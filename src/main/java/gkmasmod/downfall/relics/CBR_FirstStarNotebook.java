package gkmasmod.downfall.relics;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.relics.FirstStarNotebook;
import gkmasmod.utils.PlayerHelper;

public class CBR_FirstStarNotebook extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_FirstStarNotebook.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_FirstStarNotebook.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.SPECIAL;

    private static final int magicNumber = 2;

    private static final int REQUIRE = 4;

    private static final  int playTimes = 1;

    public CBR_FirstStarNotebook() {
        super(new FirstStarNotebook(),IMG);    }


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
        return new CBR_FirstStarNotebook();
    }


    public void onEquip() {}

    public void atTurnStart() {
        if (this.counter > 0) {
            int count = PlayerHelper.getPowerAmount(AbstractCharBoss.boss, StrengthPower.POWER_ID);
            if(count>REQUIRE){
                addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
                this.flash();
                addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new StrengthPower(AbstractCharBoss.boss, magicNumber), magicNumber));
                addToBot(new GainTrainRoundPowerAction(AbstractCharBoss.boss,1));
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
