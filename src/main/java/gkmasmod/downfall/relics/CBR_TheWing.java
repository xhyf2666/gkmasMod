package gkmasmod.downfall.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.TheWingAction;
import gkmasmod.cards.logic.Restart;
import gkmasmod.downfall.cards.logic.ENRestart;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import gkmasmod.relics.BeginnerGuideForEveryone;
import gkmasmod.relics.TheWing;

public class CBR_TheWing extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_TheWing.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_TheWing.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 6;
    private static final int magicNumber2 = 150;

    private static final  int playTimes = 2;


    public CBR_TheWing() {
        super(new TheWing(),IMG);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,magicNumber2,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_TheWing();
    }

    public void onEquip() {
        this.counter = playTimes;
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction){
        if (card.cardID.equals(ENRestart.ID)) {
            if (this.counter > 0) {
                addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
                this.flash();
                addToBot(new TheWingAction(AbstractCharBoss.boss,magicNumber,1.0F*(magicNumber2-100)/100,this));
            }
        }
    }

    public void atBattleStart() {
        this.grayscale = false;
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
