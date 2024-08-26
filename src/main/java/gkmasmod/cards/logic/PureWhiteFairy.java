package gkmasmod.cards.logic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.GoodImpressionDamageAction;
import gkmasmod.cards.AbstractDefaultCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GoodImpression;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class PureWhiteFairy extends AbstractDefaultCard {
    private static final String CLASSNAME = PureWhiteFairy.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;
    private static final int BASE_MAGIC = 2;

    private static final int BASE_MAGIC2 = 120;
    private static final int UPGRADE_PLUS_MAGIC2 = 40;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public PureWhiteFairy() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"yellow");
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = PlayerHelper.getPowerAmount(p, GoodImpression.POWER_ID);
        addToBot(new GoodImpressionDamageAction(1.0F*secondMagicNumber/100,this.magicNumber,count,p,m));
    }

    @Override
    public AbstractCard makeCopy() {
        return (AbstractCard)new PureWhiteFairy();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeSecondMagicNumber(UPGRADE_PLUS_MAGIC2);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
