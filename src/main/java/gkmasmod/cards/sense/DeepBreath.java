package gkmasmod.cards.sense;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.cardCustomEffect.DamageCustom;
import gkmasmod.cardCustomEffect.MagicCustom;
import gkmasmod.cardCustomEffect.SecondMagicCustom;
import gkmasmod.cardCustomEffect.ThirdMagicCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GoodTune;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

import java.util.ArrayList;

public class DeepBreath extends GkmasCard {
    private static final String CLASSNAME = DeepBreath.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;
    private static final int BASE_MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;
    private static final int BASE_MAGIC2 = 2;
    private static final int BASE_MAGIC3 = 1;
    private static final int UPGRADE_PLUS_MAGIC3 = 1;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorSense;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public DeepBreath() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseThirdMagicNumber = BASE_MAGIC3;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
        this.tags.add(GkmasCardTag.GOOD_TUNE_TAG);
        this.tags.add(GkmasCardTag.FOCUS_TAG);
        this.customLimit = 3;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(DamageCustom.growID,new int[]{4},new int[]{60},CustomHelper.CustomEffectType.DAMAGE_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(MagicCustom.growID,new int[]{1},new int[]{60},CustomHelper.CustomEffectType.STRENGTH_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(ThirdMagicCustom.growID,new int[]{1,1},new int[]{50,50},CustomHelper.CustomEffectType.GOOD_TUNE_ADD));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = PlayerHelper.getPowerAmount(p, StrengthPower.POWER_ID);
        if (count > secondMagicNumber) {
            addToBot(new ApplyPowerAction(p, p, new GoodTune(p, thirdMagicNumber), thirdMagicNumber));
        }
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
        addToBot(new DrawCardAction(1));
}

    @Override
    public AbstractCard makeCopy() {
        return new DeepBreath();
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
