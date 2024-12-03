package gkmasmod.downfall.relics;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.powers.GoodImpression;
import gkmasmod.relics.CracklingSparkler;

public class CBR_CracklingSparkler extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_CracklingSparkler.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_CracklingSparkler.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 2;

    private static final int HP_LOST = 1;

    private static final int TURN = 3;

    private static final int GOOD_IMPRESSION = 5;

    private static final  int playTimes = 4;

    public CBR_CracklingSparkler() {
        super(new CracklingSparkler(),IMG);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],TURN,GOOD_IMPRESSION,HP_LOST,magicNumber);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_CracklingSparkler();
    }

    public void onEquip() {
        this.counter = 0;
    }

    public void atTurnStart() {
        if (this.counter == -1) {
            this.counter += 2;
        } else {
            this.counter = (this.counter + 1)%TURN;
        }
        if (this.counter == 0) {
            int count = AbstractCharBoss.boss.getPower(GoodImpression.POWER_ID)==null?0:AbstractCharBoss.boss.getPower(GoodImpression.POWER_ID).amount;
            if(count>GOOD_IMPRESSION){
                addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
                this.flash();
                addToBot(new LoseHPAction(AbstractCharBoss.boss, AbstractCharBoss.boss, HP_LOST));
                addToBot(new GainBlockWithPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, count));
                addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new DexterityPower(AbstractCharBoss.boss, magicNumber), magicNumber));
            }
            else{
                this.counter = TURN -1;
            }
        }
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }

}
