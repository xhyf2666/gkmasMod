package gkmasmod.cards.logic;

import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import gkmasmod.actions.BlockDamageAction;
import gkmasmod.cardCustomEffect.*;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.ReduceDamageReceive;
import gkmasmod.powers.SummerEveningSparklersPower;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.SoundHelper;

import java.util.ArrayList;

public class RefreshingBreak extends GkmasCard {
    private static final String CLASSNAME = RefreshingBreak.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;

    private static final int BASE_BLOCK = 2;

    private static final int BASE_MAGIC = 230;
    private static final int BASE_MAGIC2 = 50;
    private static final int UPGRADE_PLUS_MAGIC2 = 40;
    private static final int BASE_MAGIC3 = 1;

    private static final int BASE_HP = 5;
    private static final int BASE_HP_UPGRADE = -1;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private String flavor = "";

    public RefreshingBreak() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "color");
        this.baseHPMagicNumber = BASE_HP;
        this.HPMagicNumber = this.baseHPMagicNumber;
        this.baseBlock = BASE_BLOCK;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseThirdMagicNumber = BASE_MAGIC3;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
        this.tags.add(GkmasCardTag.YARUKI_TAG);
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
        this.exhaust = true;
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, CardHelper.getColor(73, 224, 254));
        flavor = FlavorText.CardStringsFlavorField.flavor.get(CARD_STRINGS);
        this.backGroundColor = IdolData.hrnm;
        updateBackgroundImg();
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(BlockCustom.growID, new int[]{3}, new int[]{70}, CustomHelper.CustomEffectType.BLOCK_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(HPMagicCustom.growID, new int[]{-2}, new int[]{60}, CustomHelper.CustomEffectType.HP_REDUCE));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(ExhaustRemoveCustom.growID,new int[]{0},new int[]{80},CustomHelper.CustomEffectType.EXHAUST_REMOVE));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, this.HPMagicNumber));
        addToBot(new ApplyPowerAction(p, p, new ReduceDamageReceive(p, this.thirdMagicNumber), this.thirdMagicNumber));
        addToBot(new BlockDamageAction(1.0f*this.secondMagicNumber/100,block,p,m,this));
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_hrnm_3_005_produce_skillcard_01.ogg");

    }

    public void applyPowers() {
        AbstractPower dexterity = AbstractDungeon.player.getPower(DexterityPower.POWER_ID);
        int amount = 0;
        if (dexterity != null) {
            amount = dexterity.amount;
            dexterity.amount = (int) (dexterity.amount * 1.0f*this.magicNumber/100);
        }

        super.applyPowers();
        if (dexterity != null) {
            dexterity.amount = amount;
        }

    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPower dexterity = AbstractDungeon.player.getPower(DexterityPower.POWER_ID);
        int amount = 0;
        if (dexterity != null) {
            amount = dexterity.amount;
            dexterity.amount = (int) (dexterity.amount * 1.0f*this.magicNumber/100);
        }
        super.calculateCardDamage(mo);
        if (dexterity != null) {
            dexterity.amount = amount;
        }
    }

    public void applyPowersToBlock() {
        super.applyPowersToBlock();
        int count = AbstractDungeon.player.currentBlock + block;
        int damage_ = (int) (1.0F * count * this.secondMagicNumber / 100);
        FlavorText.AbstractCardFlavorFields.flavor.set(this, String.format(flavor, calculateDamage(damage_)));
    }


    public int calculateBlock(int block) {
        AbstractPower dexterity = AbstractDungeon.player.getPower(DexterityPower.POWER_ID);
        int amount = 0;
        if (dexterity != null) {
            amount = dexterity.amount;
            dexterity.amount = (int) (dexterity.amount * 1.0f*this.magicNumber/100);
        }

        int res = super.calculateBlock(block);
        if (dexterity != null) {
            dexterity.amount = amount;
        }
        return res;
    }

    @Override
    public AbstractCard makeCopy() {
        return new RefreshingBreak();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeHPMagicNumber(BASE_HP_UPGRADE);
            upgradeSecondMagicNumber(UPGRADE_PLUS_MAGIC2);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
