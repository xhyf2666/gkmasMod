package gkmasmod.cards.logic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GoodImpression;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class Inspire extends GkmasCard {
    private static final String CLASSNAME = Inspire.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 2;
    private static final int BASE_MAGIC = 3;
    private static final int UPGRADE_PLUS_MAGIC = 1;
    private static final int BASE_MAGIC2 = 5;
    private static final int BASE_MAGIC3 = 4;
    private static final int UPGRADE_PLUS_MAGIC3 = 1;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Inspire() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseThirdMagicNumber = BASE_MAGIC3;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
        this.tags.add(GkmasCardTag.YARUKI_TAG);
        this.tags.add(GkmasCardTag.GOOD_IMPRESSION_TAG);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = PlayerHelper.getPowerAmount(p, DexterityPower.POWER_ID);
        if (count > secondMagicNumber) {
            addToBot(new ApplyPowerAction(p, p, new GoodImpression(p, thirdMagicNumber), thirdMagicNumber));
        }
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber), magicNumber));
}

    @Override
    public AbstractCard makeCopy() {
        return new Inspire();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            upgradeThirdMagicNumber(UPGRADE_PLUS_MAGIC3);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
