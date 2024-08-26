package gkmasmod.cards.logic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import gkmasmod.actions.BlockDamageAction;
import gkmasmod.cards.AbstractDefaultCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class LovesTheStruggle extends AbstractDefaultCard {
    private static final String CLASSNAME = LovesTheStruggle.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 2;

    private static final int BASE_MAGIC = 250;
    private static final int UPGRADE_PLUS_MAGIC = 100;

    private static final int BASE_BLOCK = 6;
    private static final int UPGRADE_PLUS_BLOCK = 1;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public LovesTheStruggle() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"yellow");
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseBlock = BASE_BLOCK;
        this.exhaust = true;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        int count = PlayerHelper.getPowerAmount(p, DexterityPower.POWER_ID);
        int damage_ = (int) (1.0F * this.magicNumber * count /100);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage_, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }


    @Override
    public AbstractCard makeCopy() {
        return (AbstractCard)new LovesTheStruggle();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
