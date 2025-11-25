package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.relicAction.WantToBecomeIdolAction;
import gkmasmod.powers.GoodImpression;

public class WantToBecomeIdol extends CustomRelic {

    private static final String CLASSNAME = WantToBecomeIdol.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    public static final int magicNumber = 7;
    private static final int magicNumber2 = 20;
    private static final int magicNumber3 = 200;
    private static final int magicNumber4 = 2;

    private static final  int playTimes = 3;

    public WantToBecomeIdol() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }


    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,magicNumber2,magicNumber3,magicNumber4,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new WantToBecomeIdol();
    }

    public void onGoodImpressionIncrease(int amount) {
        if(amount>=magicNumber)
            addToBot(new WantToBecomeIdolAction(AbstractDungeon.player,magicNumber2,magicNumber3,magicNumber4,this));
    }

    public void onEquip() {}

    public void atBattleStart() {
        this.counter = playTimes;
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
