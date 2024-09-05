package gkmasmod.cards.logic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import gkmasmod.cards.AbstractDefaultCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GoodImpression;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class LightColorMood extends AbstractDefaultCard {
    private static final String CLASSNAME = LightColorMood.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;
    private static final int BASE_MAGIC = 2;
    private static final int BASE_MAGIC2 = 3;
    private static final int UPGRADE_PLUS_MAGIC2 = 1;

    private static final int BLOCK_AMT = 3;
    private static final int UPGRADE_PLUS_BLOCK = 2;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public LightColorMood() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK_AMT;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = PlayerHelper.getPowerAmount(p, DexterityPower.POWER_ID);
        if (count > magicNumber) {
            addToBot(new ApplyPowerAction(p, p, new GoodImpression(p, secondMagicNumber), secondMagicNumber));
        }
        addToBot(new GainBlockAction(p, p, block));
}

    @Override
    public AbstractCard makeCopy() {
        return new LightColorMood();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeSecondMagicNumber(UPGRADE_PLUS_MAGIC2);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
