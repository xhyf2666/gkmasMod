package gkmasmod.downfall.relics;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.relics.BalanceLogicAndSense;

public class CBR_BalanceLogicAndSense extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_BalanceLogicAndSense.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_BalanceLogicAndSense.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.SHOP;


    public CBR_BalanceLogicAndSense() {
        super(new BalanceLogicAndSense(),IMG);    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0]);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_BalanceLogicAndSense();
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
