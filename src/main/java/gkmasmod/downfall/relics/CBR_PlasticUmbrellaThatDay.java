package gkmasmod.downfall.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.PlasticUmbrellaThatDayAction;
import gkmasmod.downfall.cards.anomaly.ENBeyondTheCrossing;
import gkmasmod.downfall.charbosses.actions.common.EnemyMakeTempCardInHandAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import gkmasmod.patches.GameActionManagerPatch;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.relics.BeyondTheSea;
import gkmasmod.relics.PlasticUmbrellaThatDay;
import gkmasmod.utils.PlayerHelper;

public class CBR_PlasticUmbrellaThatDay extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_PlasticUmbrellaThatDay.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_PlasticUmbrellaThatDay.class.getSimpleName().substring(4);

    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 8;

    private static final int FULL_POWER_VALUE = 4;

    private static final  int playTimes = 1;

    public CBR_PlasticUmbrellaThatDay() {
        super(new PlasticUmbrellaThatDay(),IMG);
    }

    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],FULL_POWER_VALUE,magicNumber,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_PlasticUmbrellaThatDay();
    }


    public void onEquip() {
        this.counter = playTimes;
    }

    public void atTurnStartPostDraw() {
    }

    @Override
    public void onTrigger() {
        if (this.counter > 0) {
            //todo
            int count = PlayerHelper.getPowerAmount(AbstractCharBoss.boss, FullPowerValue.POWER_ID);
            if(count>FULL_POWER_VALUE){
                addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
                this.flash();
                addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new FullPowerValue(AbstractCharBoss.boss, magicNumber), magicNumber));
                AbstractCard tmp = new ENBeyondTheCrossing();
                tmp.upgrade();
                addToBot(new EnemyMakeTempCardInHandAction(tmp));
                this.counter--;
                if (this.counter == 0) {
                    this.grayscale = true;
                }
            }
        }
    }

    @Override
    public void atTurnStart() {
        onTrigger();
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
