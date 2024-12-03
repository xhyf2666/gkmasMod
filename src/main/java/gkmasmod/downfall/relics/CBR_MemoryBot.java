package gkmasmod.downfall.relics;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.powers.GoodImpression;
import gkmasmod.relics.MemoryBot;

public class CBR_MemoryBot extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_MemoryBot.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_MemoryBot.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int HP = 50;
    private static final int magicNumber = 3;
    private static int playTimes = 1;

    public CBR_MemoryBot() {
        super(new MemoryBot(),IMG);    }


    @Override
    public void onVictory() {

    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],HP,magicNumber);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_MemoryBot();
    }

    public void onPlayerEndTurn() {
        float amount = 1.0F* AbstractCharBoss.boss.currentHealth / AbstractCharBoss.boss.maxHealth;
        float HP_ = HP*1.0F/100;
        if(amount>=HP_){
            addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
            this.flash();
            addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new GoodImpression(AbstractCharBoss.boss, magicNumber), magicNumber));
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
