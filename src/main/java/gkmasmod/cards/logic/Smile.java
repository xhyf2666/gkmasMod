package gkmasmod.cards.logic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import gkmasmod.cardCustomEffect.*;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GoodImpression;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

import java.util.ArrayList;

public class Smile extends GkmasCard {
    private static final String CLASSNAME = Smile.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;
    private static final int BASE_MAGIC = 3;
    private static final int UPGRADE_PLUS_MAGIC = 1;
    private static final int BASE_MAGIC2 = 2;
    private static final int BASE_MAGIC3 = 1;
    private static final int UPGRADE_PLUS_MAGIC3 = 1;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Smile() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseThirdMagicNumber = BASE_MAGIC3;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
        this.tags.add(GkmasCardTag.YARUKI_TAG);
        this.tags.add(GkmasCardTag.GOOD_IMPRESSION_TAG);
        this.customLimit = 3;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(MagicCustom.growID,new int[]{1,1,2},new int[]{50,50,80},CustomHelper.CustomEffectType.GOOD_IMPRESSION_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(ThirdMagicCustom.growID,new int[]{1,1},new int[]{60,60},CustomHelper.CustomEffectType.DEXTERITY_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(GoodImpressionRateAttackCustom.growID,new int[]{50},new int[]{70},CustomHelper.CustomEffectType.GOOD_IMPRESSION_RATE_ATTACK));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = PlayerHelper.getPowerAmount(p, GoodImpression.POWER_ID);
        if (count > this.secondMagicNumber) {
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.thirdMagicNumber), this.thirdMagicNumber));
        }
        addToBot(new ApplyPowerAction(p, p, new GoodImpression(p, this.magicNumber), this.magicNumber));
}

    @Override
    public AbstractCard makeCopy() {
        return new Smile();
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
