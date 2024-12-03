package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.GoodTune;
import gkmasmod.utils.PlayerHelper;

public class FirstStarTshirt extends CustomRelic {

    private static final String CLASSNAME = FirstStarTshirt.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.SPECIAL;

    private static final int magicNumber = 2;

    private static final int REQUIRE = 0;

    private static final  int playTimes = 1;

    public FirstStarTshirt() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }


    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],REQUIRE,magicNumber,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new FirstStarTshirt();
    }


    public void onEquip() {}

    public void atTurnStart() {
        if (this.counter > 0) {
            int count = PlayerHelper.getPowerAmount(AbstractDungeon.player, GoodTune.POWER_ID);
            if(count>REQUIRE){
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.flash();
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new GoodTune(AbstractDungeon.player, magicNumber), magicNumber));
                addToBot(new GainTrainRoundPowerAction(AbstractDungeon.player,1));
                this.counter--;
                if (this.counter == 0) {
                    this.grayscale = true;
                }
            }
        }

    }

    public void atBattleStart() {
        this.counter = playTimes;
    }

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
