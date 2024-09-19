package gkmasmod.cards.logic;

import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.utils.NameHelper;

public class BigOnigiri extends GkmasCard {
    private static final String CLASSNAME = BigOnigiri.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;

    private static final int BASE_MAGIC = 5;
    private static final int UPGRADE_PLUS_MAGIC = 3;

    private static final int BASE_BLOCK = 2;

    private static final int BASE_HP = 3;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    private String flavor = "";

    public BigOnigiri() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "color");
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseHPMagicNumber = BASE_HP;
        this.HPMagicNumber = this.baseHPMagicNumber;
        this.baseBlock = BASE_BLOCK;
        this.block = this.baseBlock;
        this.exhaust = true;
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, CardHelper.getColor(73, 224, 254));
        flavor = FlavorText.CardStringsFlavorField.flavor.get(CARD_STRINGS);
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, this.HPMagicNumber));
        //TODO 要不要计算自己
        int count = AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1;
        // TODO 分两次获得格挡还是一次获得
        addToBot(new GainBlockAction(p, p, this.block + this.magicNumber * count));
    }

    public void applyPowers() {
        super.applyPowers();
        int count = AbstractDungeon.actionManager.cardsPlayedThisCombat.size();
        int block_ = count * this.magicNumber;
        FlavorText.AbstractCardFlavorFields.flavor.set(this, String.format(flavor, block_));
    }

    public void triggerOnCardPlayed(AbstractCard cardPlayed) {
        int count = AbstractDungeon.actionManager.cardsPlayedThisCombat.size();
        int block_ = calculateBlock(count * this.magicNumber);
        FlavorText.AbstractCardFlavorFields.flavor.set(this, String.format(flavor, block_));
    }

    @Override
    public AbstractCard makeCopy() {
        return new BigOnigiri();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
