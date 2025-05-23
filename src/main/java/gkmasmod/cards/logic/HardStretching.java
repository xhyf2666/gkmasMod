package gkmasmod.cards.logic;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.actions.GoodImpressionDamageAction;
import gkmasmod.actions.HardStretchingAction;
import gkmasmod.cardCustomEffect.*;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GoodImpression;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

import java.util.ArrayList;

public class HardStretching extends GkmasCard {
    private static final String CLASSNAME = HardStretching.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;
    private static final int BASE_MAGIC = 1;
    private static final int BASE_MAGIC2 = 3;
    private static final int BASE_BLOCK = 1;
    private static final int UPGRADE_BLOCK_PLUS = 2;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;


    public HardStretching() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseBlock = BASE_BLOCK;
        this.tags.add(GkmasCardTag.MORE_ACTION_TAG);
        CardModifierManager.addModifier(this,new MoreActionCustom(1));
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(BlockCustom.growID,new int[]{2},new int[]{40},CustomHelper.CustomEffectType.BLOCK_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(EffectChangeCustom.growID,new int[]{0},new int[]{70},CustomHelper.CustomEffectType.EFFECT_CHANGE));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(SecondMagicCustom.growID,new int[]{2},new int[]{80},CustomHelper.CustomEffectType.USE_TIME_ADD));

    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(1, new HardStretchingAction(p,this.block)));
//        addToBot(new GainTrainRoundPowerAction(p, this.magicNumber));
        if(!CustomHelper.hasCustom(this,EffectChangeCustom.growID)){
            if(this.secondMagicNumber > 1){
                upgradeSecondMagicNumber(-1);
                this.initializeDescription();
            }
            else{
                this.exhaust = true;
            }
        }

    }



    @Override
    public AbstractCard makeCopy() {
        return  new HardStretching();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK_PLUS);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
