package gkmasmod.downfall.relics;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.powers.GoodImpression;
import gkmasmod.relics.HandmadeMedal;

public class CBR_HandmadeMedal extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_HandmadeMedal.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_HandmadeMedal.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 2;

    private static final int GOOD_IMPRESSION = 5;

//    private static final  int playTimes = 2;

    public CBR_HandmadeMedal() {
        super(new HandmadeMedal(),IMG);    }


//    @Override
//    public void onVictory() {
//        this.counter = playTimes;
//    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],GOOD_IMPRESSION,magicNumber);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_HandmadeMedal();
    }


    public void onEquip() {}

    public void onPlayerEndTurn() {
//        if (this.counter > 0) {
//            int count = AbstractCharBoss.boss.getPower(GoodImpression.POWER_ID)==null?0:AbstractCharBoss.boss.getPower(GoodImpression.POWER_ID).amount;
//            if(count>GOOD_IMPRESSION){
//                addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
//                this.flash();
//                addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new GoodImpression(AbstractCharBoss.boss, magicNumber), magicNumber));
//                this.counter--;
//                if (this.counter == 0) {
//                    this.grayscale = true;
//                }
//            }
//        }
        if (this.counter > 0) {
            int count = AbstractCharBoss.boss.getPower(GoodImpression.POWER_ID)==null?0:AbstractCharBoss.boss.getPower(GoodImpression.POWER_ID).amount;
            if(count>GOOD_IMPRESSION){
                addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
                this.flash();
                addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new GoodImpression(AbstractCharBoss.boss, magicNumber), magicNumber));
            }
        }
    }

//    public void atBattleStart() {
//        this.counter = playTimes;
//    }

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
