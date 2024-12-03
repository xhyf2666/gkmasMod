package gkmasmod.downfall.relics;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.powers.GoodImpression;
import gkmasmod.relics.GreenUniformBracelet;

public class CBR_GreenUniformBracelet extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_GreenUniformBracelet.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_GreenUniformBracelet.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 3;

    private static final  int playTimes = 2;

    private static boolean isRefresh = true;

    public CBR_GreenUniformBracelet() {
        super(new GreenUniformBracelet(),IMG);    }


    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_GreenUniformBracelet();
    }


    public void onEquip() {}

    public void atTurnStart() {
    }

    public void onGoodImpressionIncrease(){
        if (this.counter > 0 && isRefresh) {
            addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
            this.flash();
            addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new GoodImpression(AbstractCharBoss.boss, magicNumber), magicNumber));
            this.counter--;
            // 防止自己触发自己
            isRefresh = false;
            if (this.counter == 0) {
                this.grayscale = true;
            }
        }
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        this.isRefresh = true;
    }

    public void atBattleStart() {
        this.counter = playTimes;
        this.isRefresh = true;
    }

    public  void  onPlayerEndTurn(){
        this.isRefresh = true;
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
