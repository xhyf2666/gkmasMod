package gkmasmod.utils;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import gkmasmod.cardCustomEffect.*;
import gkmasmod.cards.CustomEffect;
import gkmasmod.cards.GkmasCard;
import gkmasmod.growEffect.*;
import gkmasmod.powers.TempSavePower;

import java.util.ArrayList;

public class CustomHelper {
    public static void custom(AbstractCard card, String effect, int amount) {
        for(AbstractCardModifier modifier:CardModifierManager.modifiers(card)){
            if(modifier instanceof AbstractCardCustomEffect){
                AbstractCardCustomEffect growEffect = (AbstractCardCustomEffect) modifier;
                if(growEffect.growEffectID.equals(effect)){
                    growEffect.changeAmount(amount);
                    card.initializeDescription();
                    return;
                }
            }
        }
        CardModifierManager.addModifier(card, getCardModifierByID(effect, amount));
    }

    public static AbstractCardModifier getCardModifierByID(String id, int amount){
        if(id.equals(AttackTimeCustom.growID))
            return new AttackTimeCustom(amount);
        else if(id.equals(BlockCustom.growID))
            return new BlockCustom(amount);
        else if(id.equals(BlockTimeCustom.growID))
            return new BlockTimeCustom(amount);
        else if(id.equals(CostCustom.growID))
            return new CostCustom(amount);
        else if(id.equals(DamageCustom.growID))
            return new DamageCustom(amount);
        else if(id.equals(DexterityCustom.growID))
            return new DexterityCustom(amount);
        else if(id.equals(DrawCardCustom.growID))
            return new DrawCardCustom(amount);
        else if(id.equals(EffectAddCustom.growID))
            return new EffectAddCustom(amount);
        else if(id.equals(EffectChangeCustom.growID))
            return new EffectChangeCustom(amount);
        else if(id.equals(EffectReduceCustom.growID))
            return new EffectReduceCustom(amount);
        else if(id.equals(ExhaustRemoveCustom.growID))
            return new ExhaustRemoveCustom(amount);
        else if(id.equals(GoodImpressionCustom.growID))
            return new GoodImpressionCustom(amount);
        else if(id.equals(GoodImpressionRateAttackCustom.growID))
            return new GoodImpressionRateAttackCustom(amount);
        else if(id.equals(GoodTuneCustom.growID))
            return new GoodTuneCustom(amount);
        else if(id.equals(GreatGoodTuneCustom.growID))
            return new GreatGoodTuneCustom(amount);
        else if(id.equals(HalfDamageReceiveCustom.growID))
            return new HalfDamageReceiveCustom(amount);
        else if(id.equals(HPMagicCustom.growID))
            return new HPMagicCustom(amount);
        else if(id.equals(InnateCustom.growID))
            return new InnateCustom(amount);
        else if(id.equals(MagicCustom.growID))
            return new MagicCustom(amount);
        else if(id.equals(MoreActionCustom.growID))
            return new MoreActionCustom(amount);
        else if(id.equals(StrengthCustom.growID))
            return new StrengthCustom(amount);
        else if(id.equals(SecondDamageCustom.growID))
            return new SecondDamageCustom(amount);
        else if(id.equals(SecondMagicCustom.growID))
            return new SecondMagicCustom(amount);
        else if(id.equals(SelfRetainCustom.growID))
            return new SelfRetainCustom(amount);
        else if(id.equals(SlowCustom.growID))
            return new SlowCustom(amount);
        else if(id.equals(StrengthCustom.growID))
            return new StrengthCustom(amount);
        else if(id.equals(ThirdMagicCustom.growID))
            return new ThirdMagicCustom(amount);
        else if(id.equals(VigorCustom.growID))
            return new VigorCustom(amount);

        return new DamageCustom(amount);
    }


    public static ArrayList<CustomEffect> generateCustomEffectList(String type, int[] amount, int[] price, CustomEffectType description){
        ArrayList<CustomEffect> list = new ArrayList<>();
        for(int i=0;i<amount.length;i++){
            String descriptionString = getCustomEffectDescription(amount[i],description);
            list.add(new CustomEffect(type,amount[i],price[i],descriptionString));
        }
        return list;
    }

    public static String getCustomEffectDescription(int amount,CustomEffectType effect){
        switch (effect){
            case BLOCK_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:BlockAdd").TEXT[0], amount);
            case DAMAGE_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:DamageAdd").TEXT[0], amount);
            case ATTACK_TIME_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:AttackTimeAdd").TEXT[0], amount);
            case DRAW_CARD_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:DrawCardAdd").TEXT[0], amount);
            case ENERGY_COST_REDUCE:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:EnergyCostReduce").TEXT[0], amount);
            case GOOD_TUNE_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:GoodTuneAdd").TEXT[0], amount);
            case GREAT_GOOD_TUNE_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:GreatGoodTuneAdd").TEXT[0], amount);
            case STRENGTH_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:StrengthAdd").TEXT[0], amount);
            case DEXTERITY_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:DexterityAdd").TEXT[0], amount);
            case GOOD_IMPRESSION_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:GoodImpressionAdd").TEXT[0], amount);
            case RATE_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:RateAdd").TEXT[0], amount);
            case SLOW_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:SlowAdd").TEXT[0], amount);
            case VIGOR_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:VigorAdd").TEXT[0], amount);
            case HP_REDUCE:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:HpReduce").TEXT[0], amount);
            case EXHAUST_REMOVE:
                return CardCrawlGame.languagePack.getUIString("customEffect:ExhaustRemove").TEXT[0];
            case EFFECT_ADD:
                return CardCrawlGame.languagePack.getUIString("customEffect:EffectAdd").TEXT[0];
            case EFFECT_CHANGE:
                return CardCrawlGame.languagePack.getUIString("customEffect:EffectChange").TEXT[0];
            case EFFECT_REDUCE:
                return CardCrawlGame.languagePack.getUIString("customEffect:EffectReduce").TEXT[0];
            case DEXTERITY_RATE_ATTACK:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:DexterityRateAttack").TEXT[0], amount);
            case BLOCK_RATE_ATTACK:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:BlockRateAttack").TEXT[0], amount);
            case GOOD_TUNE_RATE_ATTACK:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:GoodTuneRateAttack").TEXT[0], amount);
            case GOOD_IMPRESSION_RATE_ATTACK:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:GoodImpressionRateAttack").TEXT[0], amount);
            case GREAT_GOOD_TUNE_RATE_ATTACK:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:GreatGoodTuneRateAttack").TEXT[0], amount);
            case HALF_DAMAGE_RECEIVE_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:HalfDamageReduceAdd").TEXT[0], amount);
            case DOUBLE_DAMAGE_RECEIVE_REDUCE:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:DoubleDamageAddReduce").TEXT[0], amount);
            case INNATE_ADD:
                return CardCrawlGame.languagePack.getUIString("customEffect:InnateAdd").TEXT[0];
            case SELF_RETAIN_ADD:
                return CardCrawlGame.languagePack.getUIString("customEffect:SelfRetainAdd").TEXT[0];
            case TEMP_MORE_ACTION_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:TempMoreActionAdd").TEXT[0], amount);
            case MORE_ACTION_ADD:
                return CardCrawlGame.languagePack.getUIString("customEffect:MoreActionAdd").TEXT[0];
            case BLOCK_TIME_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:BlockTimeAdd").TEXT[0], amount);
            default:
                return "";
        }
    }

    public static enum CustomEffectType{
        BLOCK_ADD,
        DAMAGE_ADD,
        ATTACK_TIME_ADD,
        DRAW_CARD_ADD,
        ENERGY_COST_REDUCE,
        GOOD_TUNE_ADD,
        GREAT_GOOD_TUNE_ADD,
        STRENGTH_ADD,
        DEXTERITY_ADD,
        GOOD_IMPRESSION_ADD,
        RATE_ADD,
        SLOW_ADD,
        VIGOR_ADD,
        HP_REDUCE,
        EXHAUST_REMOVE,
        EFFECT_ADD,
        EFFECT_CHANGE,
        EFFECT_REDUCE,
        DEXTERITY_RATE_ATTACK,
        BLOCK_RATE_ATTACK,
        GOOD_TUNE_RATE_ATTACK,
        GOOD_IMPRESSION_RATE_ATTACK,
        GREAT_GOOD_TUNE_RATE_ATTACK,
        HALF_DAMAGE_RECEIVE_ADD,
        DOUBLE_DAMAGE_RECEIVE_REDUCE,
        INNATE_ADD,
        SELF_RETAIN_ADD,
        TEMP_MORE_ACTION_ADD,
        MORE_ACTION_ADD,
        BLOCK_TIME_ADD,

    }


}
