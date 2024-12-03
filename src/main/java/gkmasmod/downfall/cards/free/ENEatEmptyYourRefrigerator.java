package gkmasmod.downfall.cards.free;

import gkmasmod.downfall.charbosses.actions.common.EnemyGainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.utils.NameHelper;

public class ENEatEmptyYourRefrigerator extends GkmasBossCard {
    private static final String CLASSNAME = ENEatEmptyYourRefrigerator.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENEatEmptyYourRefrigerator.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final CardType TYPE = CardType.CURSE;
    private static final CardColor COLOR = CardColor.CURSE;
    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public ENEatEmptyYourRefrigerator() {
        super(ID, NAME, IMG_PATH, -2, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.isEthereal = true;
    }



    public void use(AbstractPlayer p, AbstractMonster m) {}

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if(c.costForTurn > 1){
            addToTop(new EnemyGainEnergyAction(-1));
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new ENEatEmptyYourRefrigerator();
    }


    @Override
    public void upgrade() {
    }
}
