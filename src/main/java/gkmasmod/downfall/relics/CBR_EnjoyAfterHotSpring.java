package gkmasmod.downfall.relics;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.EnjoyAfterHotSpringAction;
import gkmasmod.relics.EnjoyAfterHotSpring;

public class CBR_EnjoyAfterHotSpring extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_EnjoyAfterHotSpring.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_EnjoyAfterHotSpring.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 130;
    private static final int magicNumber2 = 4;


    private static final  int playTimes = 2;

    private int currentTimes =0;

    public CBR_EnjoyAfterHotSpring() {
        super(new EnjoyAfterHotSpring(),IMG);    }


    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],4,magicNumber,magicNumber2,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_EnjoyAfterHotSpring();
    }


    public void onEquip() {}

    public void onUseCard(AbstractCard card, UseCardAction useCardAction){
        if(currentTimes < playTimes){
            counter = (counter + 1)%4;
            if(counter == 0){
                addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
                this.flash();
                addToBot(new GainBlockAction(AbstractCharBoss.boss, AbstractCharBoss.boss, magicNumber2));
                addToBot(new EnjoyAfterHotSpringAction(AbstractCharBoss.boss,1.0F*(magicNumber-100)/100));
                currentTimes++;
                if(currentTimes == playTimes){
                    grayscale = true;
                }
            }
        }
    }

    public void atBattleStart() {
        counter = 0;
    }

    public  void  onPlayerEndTurn(){
    }

    public void justEnteredRoom(AbstractRoom room) {
        currentTimes = 0;
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
