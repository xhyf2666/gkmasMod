package gkmasmod.cards.sense;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.cardCustomEffect.BlockCustom;
import gkmasmod.cardCustomEffect.DamageCustom;
import gkmasmod.cardCustomEffect.MagicCustom;
import gkmasmod.cardCustomEffect.MoreActionCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GreatGoodTune;
import gkmasmod.powers.HalfDamageReceive;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

import java.util.ArrayList;

public class CharmGaze extends GkmasCard {
    private static final String CLASSNAME = CharmGaze.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME);

    private static final int COST = 2;

    private static final int BASE_MAGIC = 2;
    private static final int BASE_MAGIC2 = 3;
    private static final int UPGRADE_PLUS_MAGIC2 = 1;
    private static final int BASE_MAGIC3 = 2;
    private static final int UPGRADE_PLUS_MAGIC3 = 1;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorSense;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public CharmGaze() {
        super(ID, NAME, ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME);
        this.updateShowImg = true;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseThirdMagicNumber = BASE_MAGIC3;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
        this.exhaust = true;
        this.tags.add(GkmasCardTag.COST_POWER_TAG);
        this.tags.add(GkmasCardTag.MORE_ACTION_TAG);
        CardModifierManager.addModifier(this,new MoreActionCustom(1));
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(MagicCustom.growID,new int[]{-1},new int[]{60},CustomHelper.CustomEffectType.STRENGTH_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(BlockCustom.growID,new int[]{6},new int[]{60},CustomHelper.CustomEffectType.BLOCK_ADD));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, -this.magicNumber), -this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new GreatGoodTune(p, this.secondMagicNumber), this.secondMagicNumber));
        addToBot(new ApplyPowerAction(p, p, new HalfDamageReceive(p, this.thirdMagicNumber), this.thirdMagicNumber));
//        addToBot(new GainTrainRoundPowerAction(p,1));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        int count = PlayerHelper.getPowerAmount(p,StrengthPower.POWER_ID);
        if (count >= magicNumber)
            return true;
        this.cantUseMessage = CardCrawlGame.languagePack.getUIString("gkmasMod:NotEnoughStrengthPower").TEXT[0];
        return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new CharmGaze();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeSecondMagicNumber(UPGRADE_PLUS_MAGIC2);
            upgradeThirdMagicNumber(UPGRADE_PLUS_MAGIC3);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
