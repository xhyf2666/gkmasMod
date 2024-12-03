package gkmasmod.downfall.relics;
import gkmasmod.downfall.charbosses.actions.common.EnemyMakeTempCardInHandAction;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.cards.free.IdolSoul;
import gkmasmod.relics.IdolNoSoul;

public class CBR_IdolNoSoul extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_IdolNoSoul.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_IdolNoSoul.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.SPECIAL;


    public CBR_IdolNoSoul() {
        super(new IdolNoSoul(),IMG);    }


    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0]);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_IdolNoSoul();
    }


    public void onEquip() {}

    public void atTurnStart() {
    }

    public void onStrengthPowerIncrease(){

    }


    public void atPreBattle() {
        addToBot(new EnemyMakeTempCardInHandAction(new IdolSoul()));
    }

    public  void  onPlayerEndTurn(){
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
