package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.cards.free.Sleepy;
import gkmasmod.powers.NextXTurnAnxietyPower;

public class AfterRainGoddess extends CustomRelic {

    private static final String CLASSNAME = AfterRainGoddess.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 5;
    private static final int magicNumber2 = 1;
    private static int playTimes = 5;


    public AfterRainGoddess() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }

    @Override
    public void onVictory() {
        this.counter = playTimes;
    }



    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,magicNumber2,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new AfterRainGoddess();
    }


    public void onEquip() {
        this.counter = playTimes;
    }

    public void atTurnStartPostDraw() {
        if (this.counter > 0) {
            this.flash();
            addToBot(new GainBlockWithPowerAction(AbstractDungeon.player,AbstractDungeon.player,magicNumber));
            addToBot(new MakeTempCardInDrawPileAction(new Sleepy(),1, true, true));
            addToBot(new GainTrainRoundPowerAction(AbstractDungeon.player,magicNumber2));
            this.counter--;
            if (this.counter == 0) {
                this.grayscale = true;
            }
        }
    }

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
