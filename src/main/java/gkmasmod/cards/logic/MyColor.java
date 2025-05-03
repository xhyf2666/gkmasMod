package gkmasmod.cards.logic;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.cardCustomEffect.*;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.patches.AbstractPowerPatch;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.NextTurnIdolBlockPower;
import gkmasmod.utils.*;

import java.util.ArrayList;

public class MyColor extends GkmasCard {
    private static final String CLASSNAME = MyColor.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;
    private static final int BASE_MAGIC = 150;
    private static final int BASE_MAGIC2 = 1;
    private static final int UPGRADE_PLUS_MAGIC2 = 1;
    private static final int BASE_BLOCK = 4;
    private static final int UPGRADE_PLUS_BLOCK = 2;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    public MyColor() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "color");
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseBlock = BASE_BLOCK;
        this.exhaust = true;
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
        this.tags.add(GkmasCardTag.YARUKI_TAG);
        this.backGroundColor = IdolData.hski;
        updateBackgroundImg();
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(MagicCustom.growID, new int[]{50}, new int[]{80}, CustomHelper.CustomEffectType.RATE_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(EffectChangeCustom.growID, new int[]{0}, new int[]{50}, CustomHelper.CustomEffectType.EFFECT_CHANGE));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(EffectAddCustom.growID, new int[]{0}, new int[]{70}, CustomHelper.CustomEffectType.EFFECT_CHANGE));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = PlayerHelper.getPowerAmount(p, DexterityPower.POWER_ID);
        count = (int) (1.0F*count*(this.magicNumber-100)/100);
        if(count>0){
            AbstractPower power = new DexterityPower(p, count);
            AbstractPowerPatch.IgnoreIncreaseModifyField.flag.set(power, true);
            addToBot(new ApplyPowerAction(p, p, power, count));
        }
        if(CustomHelper.hasCustom(this, EffectChangeCustom.growID)){
            addToBot(new GainBlockAction(p, p, this.block));
        }
        else {
            addToBot(new ApplyPowerAction(p,p,new NextTurnIdolBlockPower(p,this.baseBlock),this.baseBlock));
        }
        if(CustomHelper.hasCustom(this, EffectAddCustom.growID)){
            PlayerHelper.removeAllNegativePower(p);
        }
        else {
            PlayerHelper.removeNegativePower(p,this.secondMagicNumber);
        }

//        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_ssmk_3_010_produce_skillcard_01.ogg");
    }

    @Override
    public AbstractCard makeCopy() {
        return new MyColor();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeSecondMagicNumber(UPGRADE_PLUS_MAGIC2);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
