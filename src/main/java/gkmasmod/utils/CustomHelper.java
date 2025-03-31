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
import java.util.Iterator;

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

    public static void custom(AbstractCard card,AbstractCardModifier effect){
        if(effect instanceof AbstractCardCustomEffect){
            AbstractCardCustomEffect customEffect = (AbstractCardCustomEffect) effect;
            for (AbstractCardModifier modifier : CardModifierManager.modifiers(card)) {
                if (modifier instanceof AbstractCardCustomEffect) {
                    AbstractCardCustomEffect growEffect = (AbstractCardCustomEffect) modifier;
                    if (growEffect.growEffectID.equals(customEffect.growEffectID)) {
                        growEffect.changeAmount(customEffect.amount);
                        card.initializeDescription();
                        return;
                    }
                }
            }
        }
        CardModifierManager.addModifier(card, effect);

    }

    public static void removeCustom(AbstractCard card, String effect) {
        Iterator<AbstractCardModifier> iteratorC1 = CardModifierManager.modifiers(card).iterator();
        while (iteratorC1.hasNext()) {
            AbstractCardModifier mod = iteratorC1.next();
            if (mod instanceof AbstractCardCustomEffect && ((AbstractCardCustomEffect) mod).growEffectID.equals(effect)) {
                iteratorC1.remove();  // 使用 Iterator 的 remove 方法安全地移除元素
            }
        }
    }

    public static boolean hasCustom(AbstractCard card, String effect) {
        for(AbstractCardModifier modifier:CardModifierManager.modifiers(card)){
            if(modifier instanceof AbstractCardCustomEffect){
                AbstractCardCustomEffect growEffect = (AbstractCardCustomEffect) modifier;
                if(growEffect.growEffectID.equals(effect)){
                    return true;
                }
            }
        }
        return false;
    }

    public static AbstractCardModifier getCardModifierByID(String id, int amount){
        if(id.equals(AttackTimeCustom.growID))
            return new AttackTimeCustom(amount);
        else if(id.equals(BlockCustom.growID))
            return new BlockCustom(amount);
        else if(id.equals(BlockRateAttackCustom.growID))
            return new BlockRateAttackCustom(amount);
        else if(id.equals(BlockTimeCustom.growID))
            return new BlockTimeCustom(amount);
        else if(id.equals(CostCustom.growID))
            return new CostCustom(amount);
        else if(id.equals(DamageCustom.growID))
            return new DamageCustom(amount);
        else if(id.equals(DexterityCustom.growID))
            return new DexterityCustom(amount);
        else if(id.equals(DexterityRateAttackCustom.growID))
            return new DexterityRateAttackCustom(amount);
        else if(id.equals(DrawCardCustom.growID))
            return new DrawCardCustom(amount);
        else if(id.equals(EffectAddCustom.growID))
            return new EffectAddCustom(amount);
        else if(id.equals(EffectChangeCustom.growID))
            return new EffectChangeCustom(amount);
        else if(id.equals(EffectReduceCustom.growID))
            return new EffectReduceCustom(amount);
        else if(id.equals(EnthusiasticCustom.growID))
            return new EnthusiasticCustom(amount);
        else if(id.equals(ExhaustRemoveCustom.growID))
            return new ExhaustRemoveCustom(amount);
        else if(id.equals(FullPowerValueCustom.growID))
            return new FullPowerValueCustom(amount);
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
        else if(id.equals(NotGoodTuneCustom.growID))
            return new NotGoodTuneCustom(amount);
        else if(id.equals(NotGreatGoodTuneCustom.growID))
            return new NotGreatGoodTuneCustom(amount);
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
        else if(id.equals(TempMoreActionCustom.growID))
            return new TempMoreActionCustom(amount);
        else if(id.equals(ThirdMagicCustom.growID))
            return new ThirdMagicCustom(amount);
        else if(id.equals(TopSkyCustom.growID))
            return new TopSkyCustom(amount);
        else if(id.equals(UpgradeAllHandCustom.growID))
            return new UpgradeAllHandCustom(amount);
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
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:HalfDamageReceiveAdd").TEXT[0], amount);
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
            case ENTHUSIASTIC_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:EnthusiasticAdd").TEXT[0], amount);
            case TOP_SKY_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:TopSkyAdd").TEXT[0], amount);
            case UPGRADE_ALL_HAND:
                return CardCrawlGame.languagePack.getUIString("customEffect:UpgradeAllHand").TEXT[0];
            case USE_TIME_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:UseTimeAdd").TEXT[0], amount);
            case NOT_GOOD_TUNE_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:NotGoodTuneAdd").TEXT[0], amount);
            case NOT_GREAT_GOOD_TUNE_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:NotGreatGoodTuneAdd").TEXT[0], amount);
            case GROW_EFFECT_ADD:
                return CardCrawlGame.languagePack.getUIString("customEffect:GrowEffectAdd").TEXT[0];
            case GROW_EFFECT_CHANGE:
                return CardCrawlGame.languagePack.getUIString("customEffect:GrowEffectChange").TEXT[0];
            case GROW_EFFECT_REDUCE:
                return CardCrawlGame.languagePack.getUIString("customEffect:GrowEffectReduce").TEXT[0];
            case FULL_POWER_VALUE_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:FullPowerValueAdd").TEXT[0], amount);
            case CONCENTRATION_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:ConcentrationAdd").TEXT[0], amount);
            case PRESERVATION_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:PreservationAdd").TEXT[0], amount);
            case NEXT_TURN_PRESERVATION:
                return CardCrawlGame.languagePack.getUIString("customEffect:NextTurnPreservation").TEXT[0];
            case END_TURN_PRESERVATION:
                return CardCrawlGame.languagePack.getUIString("customEffect:EndTurnPreservation").TEXT[0];
            case TEMP_SAVE_ADD:
                return String.format(CardCrawlGame.languagePack.getUIString("customEffect:TempSaveAdd").TEXT[0], amount);
            default:
                return "";
        }
    }

    public static enum CustomEffectType{
        BLOCK_ADD,//格挡增加
        DAMAGE_ADD,//伤害增加
        ATTACK_TIME_ADD,//攻击次数增加
        DRAW_CARD_ADD,//抽牌增加
        ENERGY_COST_REDUCE,//能量消耗减少
        GOOD_TUNE_ADD,//好调增加
        GREAT_GOOD_TUNE_ADD,//绝好调增加
        STRENGTH_ADD,//力量增加
        DEXTERITY_ADD,//敏捷增加
        GOOD_IMPRESSION_ADD,//好印象增加
        RATE_ADD,//倍率增加
        SLOW_ADD,//缓慢增加
        VIGOR_ADD,//活力增加
        HP_REDUCE,//HP消耗减少
        EXHAUST_REMOVE,//消耗移除
        EFFECT_ADD,//效果追加
        EFFECT_CHANGE,//效果变更
        EFFECT_REDUCE,//效果削除
        DEXTERITY_RATE_ATTACK,//敏捷倍率伤害
        BLOCK_RATE_ATTACK,//格挡倍率伤害
        GOOD_TUNE_RATE_ATTACK,//好调倍率伤害
        GOOD_IMPRESSION_RATE_ATTACK,//好印象倍率伤害
        GREAT_GOOD_TUNE_RATE_ATTACK,//绝好调倍率伤害
        HALF_DAMAGE_RECEIVE_ADD,//受伤锐减效果延长
        DOUBLE_DAMAGE_RECEIVE_REDUCE,//受伤剧增效果减少
        INNATE_ADD,//固有
        SELF_RETAIN_ADD,//保留
        TEMP_MORE_ACTION_ADD,//临时再动
        MORE_ACTION_ADD,//再动
        BLOCK_TIME_ADD,//格挡次数增加
        ENTHUSIASTIC_ADD,//热意增加
        TOP_SKY_ADD,//上浮增加
        UPGRADE_ALL_HAND,//强化所有手牌
        USE_TIME_ADD,//使用次数增加
        NOT_GOOD_TUNE_ADD,//不调增加
        NOT_GREAT_GOOD_TUNE_ADD,//绝不调增加
        GROW_EFFECT_ADD,//成长效果增加
        GROW_EFFECT_CHANGE,//成长效果变更
        GROW_EFFECT_REDUCE,//成长效果削除
        FULL_POWER_VALUE_ADD,//全力值增加
        CONCENTRATION_ADD,//强气增加
        PRESERVATION_ADD,//温存增加
        NEXT_TURN_PRESERVATION,//下回合温存
        END_TURN_PRESERVATION,//回合结束温存
        TEMP_SAVE_ADD,//暂存增加

    }


}
