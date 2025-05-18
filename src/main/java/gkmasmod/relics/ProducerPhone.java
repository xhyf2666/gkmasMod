package gkmasmod.relics;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.ForesightPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.powers.CallMeAnyTimePower;
import gkmasmod.powers.HalfDamageReceive;
import gkmasmod.powers.NoPhoneInClassPower;
import gkmasmod.screen.PocketBookViewScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.RankHelper;
import gkmasmod.utils.SoundHelper;

public class ProducerPhone extends CustomRelic {

    private static final String CLASSNAME = ProducerPhone.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static int playTimes = 1;

    private static final String AUDIO = "gkmasModResource/audio/voice/phone/phone_%s_%03d.ogg";

    private boolean RclickStart = false;

    private boolean thisBattle = false;

    public ProducerPhone() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
        this.counter = 0;
    }

    @Override
    public void update() {
        super.update();
        updateRelicRightClick();
    }

    private void updateRelicRightClick() {
        if (this.RclickStart && InputHelper.justReleasedClickRight) {
            if(AbstractDungeon.currMapNode==null){
                return;
            }
            if (AbstractDungeon.actionManager.turnHasEnded &&
                    (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT){
                return;
            }
            if(AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT)
                return;
            if (this.hb.hovered) {
                for(AbstractMonster mo:AbstractDungeon.getCurrRoom().monsters.monsters){
                    if(!mo.isDeadOrEscaped()&&mo.hasPower(NoPhoneInClassPower.POWER_ID)){
                        if(AbstractDungeon.player instanceof IdolCharacter){
                            if(SkinSelectScreen.Inst.idolName.equals(IdolData.ttmr)){
                                AbstractDungeon.effectList.add(new SpeechBubble(mo.hb.cX + mo.dialogX - 50, mo.hb.cY + mo.dialogY + 50, 3.0F, String.format(CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:MonsterAsari").DIALOG[3],CardCrawlGame.playerName), false));
                            }
                        }
                        return;
                    }
                }
                this.counter++;
                if(!this.thisBattle){
                    float amount = 1.0F * AbstractDungeon.player.currentHealth / AbstractDungeon.player.maxHealth;
                    if(amount >=0.5f){
                        addToBot(new GainTrainRoundPowerAction(AbstractDungeon.player,1));
                    }
                    else{
                        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new HalfDamageReceive(AbstractDungeon.player,1),1));
                    }
                    this.thisBattle = true;
                }
                playVoice();
                CInputActionSet.select.unpress();
            }
            this.RclickStart = false;
        }
        if (this.hb != null && this.hb.hovered && InputHelper.justClickedRight)
            this.RclickStart = true;
    }

    private void playVoice() {

        if(SkinSelectScreen.Inst.idolName.equals(IdolData.ttmr)&&counter>4){
            SoundHelper.playSound("gkmasModResource/audio/voice/phone/phone_ttmr_special.ogg");
            return;
        }

        java.util.Random random = new java.util.Random();
        int index = random.nextInt(12)+1;
        String idolName = SkinSelectScreen.Inst.idolName;
        if(AbstractDungeon.player instanceof MisuzuCharacter){
            idolName = IdolData.hmsz;
        }
        SoundHelper.playSound(String.format(AUDIO,idolName,index));
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ProducerPhone();
    }


    public void onEquip() {}

    @Override
    public void atBattleStart() {
        this.thisBattle = false;
    }

    @Override
    public void atPreBattle() {
    }

    public  void  onPlayerEndTurn(){
    }

    public void onVictory() {
        this.thisBattle = false;
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
