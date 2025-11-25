package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.powers.AngerPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.common.CustomSpawnMonsterAction;
import gkmasmod.cards.othe.SendEmoji;
import gkmasmod.monster.exordium.AcidSlimeTemari_S;
import gkmasmod.utils.SoundHelper;
import gkmasmod.vfx.effect.EmojiUpEffect;

public class TemariToy extends CustomRelic {

    private static final String CLASSNAME = TemariToy.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.SPECIAL;

    private static int playTimes = 4;

    private boolean RclickStart = false;

    private int counterInBattle = 0;

    public TemariToy() {
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
                this.counter++;
                this.counterInBattle++;
                addToBot(new VFXAction(new EmojiUpEffect()));
                if(this.counterInBattle==playTimes){
                    AcidSlimeTemari_S enemy = new AcidSlimeTemari_S(-200.0F, 250.0F, 0);
                    addToBot(new CustomSpawnMonsterAction(enemy,true));
                    playVoice();
                    addToBot(new ApplyPowerAction(enemy,enemy, new AngerPower(enemy, this.counter), this.counter));
                    addToBot(new MakeTempCardInHandAction(new SendEmoji()));
                }
                CInputActionSet.select.unpress();
            }
            this.RclickStart = false;
        }
        if (this.hb != null && this.hb.hovered && InputHelper.justClickedRight)
            this.RclickStart = true;
    }

    private void playVoice() {
        java.util.Random random = new java.util.Random();
        int index = random.nextInt(4)+1;
        SoundHelper.playSound(String.format("gkmasModResource/audio/voice/special/ttmr_urusai_00%d.ogg",index));
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0]);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TemariToy();
    }


    public void onEquip() {
        this.counterInBattle = 0;
    }

    @Override
    public void atBattleStart() {
        this.counterInBattle = 0;
    }

    @Override
    public void atPreBattle() {
    }

    public  void  onPlayerEndTurn(){
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
