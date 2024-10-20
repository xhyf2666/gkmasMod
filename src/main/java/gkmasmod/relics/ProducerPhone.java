package gkmasmod.relics;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.powers.watcher.ForesightPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.powers.HalfDamageReceive;
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

    public ProducerPhone() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
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
                this.counter++;
                if(this.counter ==1){
                    float amount = 1.0F * AbstractDungeon.player.currentHealth / AbstractDungeon.player.maxHealth;
                    if(amount >=0.5f){
                        addToBot(new GainTrainRoundPowerAction(AbstractDungeon.player,1));
                    }
                    else{
                        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new HalfDamageReceive(AbstractDungeon.player,1),1));
                    }
                    this.grayscale = true;
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
        SoundHelper.playSound(String.format(AUDIO,SkinSelectScreen.Inst.idolName,index));
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
        this.counter = 0;
    }

    @Override
    public void atPreBattle() {
    }

    public  void  onPlayerEndTurn(){
    }

    public void onVictory() {
        this.grayscale = false;
        this.counter = 0;
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
