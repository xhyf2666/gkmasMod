package gkmasmod.cards.logic;

import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import gkmasmod.actions.BlockDamageAction;
import gkmasmod.actions.DexterityPowerDamageAction;
import gkmasmod.cardCustomEffect.BlockCustom;
import gkmasmod.cardCustomEffect.ExhaustRemoveCustom;
import gkmasmod.cardCustomEffect.MagicCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GoodImpression;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

import java.util.ArrayList;

public class PromiseThatTime extends GkmasCard {
    private static final String CLASSNAME = PromiseThatTime.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME);

    private static final int COST = 2;
    private static final int BASE_MAGIC = 4;
    private static final int BASE_MAGIC2 = 100;
    private static final int UPGRADE_PLUS_MAGIC2 = 30;
    private static final int BASE_MAGIC3 = 150;
    private static final int UPGRADE_PLUS_MAGIC3 = 50;

    private static final int BLOCK_AMT = 3;
    private static final int UPGRADE_PLUS_BLOCK = 2;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private String flavor = "";

    public PromiseThatTime() {
        super(ID, NAME, ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME);
        this.updateShowImg = true;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseThirdMagicNumber = BASE_MAGIC3;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
        this.baseBlock = BLOCK_AMT;
        this.block = this.baseBlock;
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, CardHelper.getColor(73, 224, 254));
        flavor = FlavorText.CardStringsFlavorField.flavor.get(CARD_STRINGS);
        this.tags.add(GkmasCardTag.COST_POWER_TAG);
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(BlockCustom.growID, new int[]{3}, new int[]{60}, CustomHelper.CustomEffectType.BLOCK_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(MagicCustom.growID,new int[]{-1},new int[]{50},CustomHelper.CustomEffectType.GOOD_IMPRESSION_ADD));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        int count = PlayerHelper.getPowerAmount(p, GoodImpression.POWER_ID);
        if (count >= this.magicNumber)
            return true;
        this.cantUseMessage = CardCrawlGame.languagePack.getUIString("gkmasMod:NotEnoughGoodImpression").TEXT[0];
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new GoodImpression(p, -this.magicNumber), -this.magicNumber));
        addToBot(new BlockDamageAction(1.0F * secondMagicNumber / 100, this.block, p, m,this));
        addToBot(new DexterityPowerDamageAction(this.thirdMagicNumber*1.0F/100, 0,p, m, this));
    }

//    public void applyPowers() {
//        super.applyPowers();
//        int count1 = AbstractDungeon.player.currentBlock + block;
//        int damage1_ = (int) (1.0F * count1 * this.secondMagicNumber / 100);
//        int count2 = PlayerHelper.getPowerAmount(AbstractDungeon.player, DexterityPower.POWER_ID);
//        int damage2_ = (int) (1.0F * this.thirdMagicNumber * count2 / 100);
//        FlavorText.AbstractCardFlavorFields.flavor.set(this, String.format(flavor, calculateDamage(damage1_), calculateDamage(damage2_)));
//    }

    public void applyPowersToBlock() {
        super.applyPowersToBlock();
        int count1 = AbstractDungeon.player.currentBlock + block;
        int damage1_ = (int) (1.0F * count1 * this.secondMagicNumber / 100);
        int count2 = PlayerHelper.getPowerAmount(AbstractDungeon.player, DexterityPower.POWER_ID);
        int damage2_ = (int) (1.0F * this.thirdMagicNumber * count2 / 100);
        FlavorText.AbstractCardFlavorFields.flavor.set(this, String.format(flavor, calculateDamage(damage1_), calculateDamage(damage2_)));
    }



    @Override
    public AbstractCard makeCopy() {
        return new PromiseThatTime();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeSecondMagicNumber(UPGRADE_PLUS_MAGIC2);
            upgradeThirdMagicNumber(UPGRADE_PLUS_MAGIC3);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
