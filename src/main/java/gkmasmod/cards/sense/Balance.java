package gkmasmod.cards.sense;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.actions.BalanceAction;
import gkmasmod.cardCustomEffect.*;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.BalancePower;
import gkmasmod.powers.GoodTune;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

import java.util.ArrayList;

public class Balance extends GkmasCard {
    private static final String CLASSNAME = Balance.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;

    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorSense;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Balance() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(GkmasCardTag.GOOD_TUNE_TAG);
        this.tags.add(GkmasCardTag.FOCUS_TAG);
        this.isEthereal = true;
        this.customLimit = 1;
        this.customEffectList = new ArrayList<>();
        this.customEffectList.add(CustomHelper.generateCustomEffectList(BlockCustom.growID,new int[]{2},new int[]{40},CustomHelper.CustomEffectType.BLOCK_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(SelfRetainCustom.growID,new int[]{0},new int[]{70},CustomHelper.CustomEffectType.SELF_RETAIN_ADD));
        this.customEffectList.add(CustomHelper.generateCustomEffectList(EffectChangeCustom.growID,new int[]{0},new int[]{100},CustomHelper.CustomEffectType.EFFECT_CHANGE));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(CustomHelper.hasCustom(this,EffectChangeCustom.growID)){
            addToBot(new ApplyPowerAction(p,p,new BalancePower(p)));
        }
        else{
            addToBot(new BalanceAction(p));
//            int count_goodTune = PlayerHelper.getPowerAmount(p, GoodTune.POWER_ID);
//            int count_strength = PlayerHelper.getPowerAmount(p, StrengthPower.POWER_ID);
//            int count = (count_goodTune + count_strength+1)/2;
//            if(count<=0)
//                return;
//            if(p.hasPower(GoodTune.POWER_ID))
//                addToBot(new ApplyPowerAction(p, p, new GoodTune(p, count-count_goodTune), count-count_goodTune));
//            else{
//                addToBot(new ApplyPowerAction(p, p, new GoodTune(p, count), count));
//            }
//            if(p.hasPower(StrengthPower.POWER_ID))
//                addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, count-count_strength), count-count_strength));
//            else{
//                addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, count), count));
//            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return  new Balance();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.isEthereal = false;
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
