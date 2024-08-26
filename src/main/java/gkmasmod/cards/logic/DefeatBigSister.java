package gkmasmod.cards.logic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import gkmasmod.actions.BlockDamageAction;
import gkmasmod.cards.AbstractDefaultCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.utils.NameHelper;

public class DefeatBigSister extends AbstractDefaultCard {
    private static final String CLASSNAME = DefeatBigSister.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;

    private static final int BASE_MAGIC = 100;
    private static final int UPGRADE_PLUS_MAGIC = 40;

    private static final int BASE_BLOCK = 3;

    private static final int BASE_HP = 4;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public DefeatBigSister() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"yellow");
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseHPMagicNumber = BASE_HP;
        this.HPMagicNumber = this.baseHPMagicNumber;
        this.baseBlock = BASE_BLOCK;
        this.block = this.baseBlock;
        this.exhaust = true;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, this.HPMagicNumber));
        addToBot(new BlockDamageAction(1.0F*this.magicNumber / 100, this.block, p, m));
        addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    public AbstractCard makeCopy() {
        return new DefeatBigSister();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
