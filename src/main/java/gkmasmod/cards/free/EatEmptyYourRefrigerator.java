package gkmasmod.cards.free;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import gkmasmod.cards.GkmasCard;
import gkmasmod.powers.NotGoodTune;
import gkmasmod.utils.NameHelper;

public class EatEmptyYourRefrigerator extends GkmasCard {
    private static final String CLASSNAME = EatEmptyYourRefrigerator.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);


    private static final CardType TYPE = CardType.CURSE;
    private static final CardColor COLOR = CardColor.CURSE;
    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public EatEmptyYourRefrigerator() {
        super(ID, NAME, IMG_PATH, -2, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.isEthereal = true;
    }



    public void use(AbstractPlayer p, AbstractMonster m) {}

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if(c.costForTurn > 1){
            addToTop(new GainEnergyAction(-1));
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new EatEmptyYourRefrigerator();
    }


    @Override
    public void upgrade() {
    }
}
