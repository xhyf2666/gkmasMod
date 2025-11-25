package gkmasmod.cards.logic;

import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;
import gkmasmod.cardCustomEffect.*;
import gkmasmod.cardGrowEffect.BlockGrow;
import gkmasmod.cardGrowEffect.DamageGrow;
import gkmasmod.cardGrowEffect.EnergyGrow;
import gkmasmod.cardGrowEffect.ExhaustRemoveGrow;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GoodImpression;
import gkmasmod.utils.*;

import java.util.ArrayList;

public class SeizeTheFuture extends GkmasCard {
    private static final String CLASSNAME = SeizeTheFuture.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;

    private static final int BASE_BLOCK = 5;

    private static final int BASE_MAGIC = 3;
    private static final int BASE_MAGIC2 = 2;
    private static final int UPGRADE_PLUS_MAGIC2 = 1;
    private static final int BASE_MAGIC3 = 2;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;


    public SeizeTheFuture() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "color");
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseBlock = BASE_BLOCK;
        this.block = this.baseBlock;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseThirdMagicNumber = BASE_MAGIC3;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
        this.exhaust = true;
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
        this.tags.add(GkmasCardTag.GOOD_IMPRESSION_TAG);
        this.backGroundColor = IdolData.hume;
        updateBackgroundImg();
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(EffectChangeCustom.growID, new int[]{0}, new int[]{60}, CustomHelper.CustomEffectType.EFFECT_CHANGE));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(SecondMagicCustom.growID, new int[]{1}, new int[]{50}, CustomHelper.CustomEffectType.GROW_EFFECT_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(MoreActionCustom.growID, new int[]{1}, new int[]{60}, CustomHelper.CustomEffectType.MORE_ACTION_ADD));
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new ApplyPowerAction(p,p, new GoodImpression(p, this.magicNumber)));
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_hume_3_016_produce_skillcard_01.ogg");
        if(CustomHelper.hasCustom(this, EffectChangeCustom.growID)){
            if(this.thirdMagicNumber > 1){
                upgradeThirdMagicNumber(-1);
                this.initializeDescription();
            }
            else if(!GrowHelper.hasGrow(this, ExhaustRemoveGrow.growID)){
                this.exhaust = true;
            }
        }
    }

    @Override
    public void triggerOnCardPlayed(AbstractCard cardPlayed) {
        GrowHelper.grow(this, BlockGrow.growID,this.secondMagicNumber);
    }

    @Override
    public AbstractCard makeCopy() {
        return new SeizeTheFuture();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeSecondMagicNumber(UPGRADE_PLUS_MAGIC2);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
