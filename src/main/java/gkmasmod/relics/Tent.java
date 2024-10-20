package gkmasmod.relics;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepScreenCoverEffect;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.powers.GoodTune;
import gkmasmod.screen.PocketBookViewScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.PlayerHelper;
import gkmasmod.utils.RankHelper;

public class Tent extends CustomRelic {

    private static final String CLASSNAME = Tent.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.SPECIAL;

    private static final  int playTimes = 3;

    private boolean RclickStart = false;

    public Tent() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }


    @Override
    public void update() {
        super.update();
        updateRelicRightClick();
    }

    private void updateRelicRightClick() {
        if (this.RclickStart && InputHelper.justReleasedClickRight) {
            if (this.hb.hovered && this.counter < playTimes) {
                if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss){
                    return;
                }
                if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
                    (AbstractDungeon.getCurrRoom()).smoked = true;
                    addToBot(new VFXAction(new SmokeBombEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
                    AbstractDungeon.player.hideHealthBar();
                    AbstractDungeon.player.isEscaping = true;
                    AbstractDungeon.player.flipHorizontal = !AbstractDungeon.player.flipHorizontal;
                    AbstractDungeon.overlayMenu.endTurnButton.disable();
                    AbstractDungeon.player.escapeTimer = 2.0F;
                }
                CardCrawlGame.sound.play("SLEEP_BLANKET");
                AbstractDungeon.effectList.add(new CampfireSleepEffect());
                for (int i = 0; i < 30; i++)
                    AbstractDungeon.topLevelEffects.add(new CampfireSleepScreenCoverEffect());
                CInputActionSet.select.unpress();
                this.counter++;
                if(this.counter == playTimes){
                    this.grayscale = true;
                }
                //TODO 跳过事件
            }
            this.RclickStart = false;
        }
        if (this.hb != null && this.hb.hovered && InputHelper.justClickedRight)
            this.RclickStart = true;
    }


    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Tent();
    }


    public void onEquip() {}

    public  void  onPlayerEndTurn(){
    }

    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
