package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.cards.free.JustAngel;
import gkmasmod.cards.free.JustDemon;

public class AngelAndDemonRelic extends CustomRelic {

    private static final String CLASSNAME = AngelAndDemonRelic.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.COMMON;

    private static final int HP = 4;
    private static final int BASE_DAMAGE = 9;

    public AngelAndDemonRelic() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new AngelAndDemonRelic();
    }


    @Override
    public void atTurnStartPostDraw() {
        AbstractCard card1 = new JustAngel();
        card1.upgrade();
        AbstractCard card2 = new JustDemon();
        card2.upgrade();
        addToBot(new MakeTempCardInHandAction(card1));
        addToBot(new MakeTempCardInHandAction(card2));
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
