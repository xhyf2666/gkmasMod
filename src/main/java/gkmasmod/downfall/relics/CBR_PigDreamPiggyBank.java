package gkmasmod.downfall.relics;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.PigDreamPiggyBankAction;
import gkmasmod.relics.PigDreamPiggyBank;

public class CBR_PigDreamPiggyBank extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_PigDreamPiggyBank.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_PigDreamPiggyBank.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 3;
    private static final int magicNumber2 = 1;

    private static final int GOOD_IMPRESSION = 5;

    private static final  int playTimes = 1;


    public CBR_PigDreamPiggyBank() {
        super(new PigDreamPiggyBank(),IMG);    }


    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],GOOD_IMPRESSION,magicNumber,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_PigDreamPiggyBank();
    }


    public void onEquip() {}

    public void onUseCard(AbstractCard card, UseCardAction useCardAction){
        if (this.counter > 0) {
            addToBot(new PigDreamPiggyBankAction(AbstractCharBoss.boss,GOOD_IMPRESSION,magicNumber,this));
//            int count = AbstractCharBoss.boss.getPower(GoodImpression.POWER_ID)==null?0:AbstractCharBoss.boss.getPower(GoodImpression.POWER_ID).amount;
//            if(count>GOOD_IMPRESSION){
//                addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
//                addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new GoodImpression(AbstractCharBoss.boss, magicNumber), magicNumber));
//                addToBot(new GainTrainRoundPowerAction(AbstractCharBoss.boss,1));
//                this.counter--;
//                if (this.counter == 0) {
//                    this.grayscale = true;
//                }
//            }
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
