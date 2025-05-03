package gkmasmod.cards.logic;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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
import gkmasmod.powers.*;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

import java.util.ArrayList;

public class DreamColorLipstick extends GkmasCard {
    private static final String CLASSNAME = DreamColorLipstick.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME);

    private static final int COST = 2;
    private static final int BASE_BLOCK = 2;
    private static final int BASE_MAGIC = 2;
    private static final int BASE_HP = 10;
    private static final int UPGRADE_PLUS_HP = 10;
    private static final int BASE_MAGIC2 = 3;
    private static final int UPGRADE_PLUS_MAGIC2 = 1;
    private static final int BASE_MAGIC3 = 120;

    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;


    public DreamColorLipstick() {
        super(ID, NAME, ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = ImageHelper.idolImgPath(SkinSelectScreen.Inst.idolName, CLASSNAME);
        this.updateShowImg = true;
        this.baseBlock = BASE_BLOCK;
        this.HPMagicNumber = this.baseHPMagicNumber = BASE_HP;
        this.magicNumber = this.baseMagicNumber = BASE_MAGIC;
        this.secondMagicNumber = this.baseSecondMagicNumber = BASE_MAGIC2;
        this.thirdMagicNumber = this.baseThirdMagicNumber = BASE_MAGIC3;
        this.tags.add(GkmasCardTag.YARUKI_TAG);
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(BlockTimeCustom.growID,new int[]{1},new int[]{60},CustomHelper.CustomEffectType.BLOCK_TIME_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(ThirdMagicCustom.growID, new int[]{10}, new int[]{80}, CustomHelper.CustomEffectType.RATE_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(EffectChangeCustom.growID, new int[]{0}, new int[]{100}, CustomHelper.CustomEffectType.EFFECT_CHANGE));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new DamageIncreasePower(p,this.HPMagicNumber),this.HPMagicNumber));
        if(PlayerHelper.getPowerAmount(p, GoodImpression.POWER_ID)>10){
            if(CustomHelper.hasCustom(this, EffectChangeCustom.growID)){
                addToBot(new ApplyPowerAction(p, p, new DreamColorLipstickSPPower(p,2), 2));
            }
            else{
                addToBot(new ApplyPowerAction(p, p, new DreamColorLipstickPower(p, this.secondMagicNumber,this.thirdMagicNumber), this.secondMagicNumber));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new DreamColorLipstick();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeHPMagicNumber(UPGRADE_PLUS_HP);
            upgradeSecondMagicNumber(UPGRADE_PLUS_MAGIC2);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
