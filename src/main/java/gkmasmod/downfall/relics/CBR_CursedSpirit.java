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
import gkmasmod.relics.CursedSpirit;

public class CBR_CursedSpirit extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_CursedSpirit.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_CursedSpirit.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int BLOCK = 4;

    private float magicNumber;

    public CBR_CursedSpirit() {
        super(new CursedSpirit(),IMG);    }



    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],BLOCK);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_CursedSpirit();
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
            this.flash();
            addToBot(new GainBlockAction(AbstractCharBoss.boss, AbstractCharBoss.boss, BLOCK));
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
