package gkmasmod.utils;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.cardCustomEffect.AbstractCardCustomEffect;
import gkmasmod.cards.anomaly.ComprehensiveArt;
import gkmasmod.cards.anomaly.IdealTempo;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.growEffect.*;
import gkmasmod.powers.TempSavePower;

public class GrowHelper {
    public static void grow(AbstractCard card, String effect, int amount) {
        for(AbstractCardModifier modifier:CardModifierManager.modifiers(card)){
            if(modifier instanceof AbstractGrowEffect){
                AbstractGrowEffect growEffect = (AbstractGrowEffect) modifier;
                if(growEffect.growEffectID.equals(effect)){
                    growEffect.changeAmount(amount);
                    if(growEffect instanceof EnergyGrow){
                        ((EnergyGrow) growEffect).reApply(card);
                    }
                    card.initializeDescription();
                    return;
                }
            }
        }
        if(effect.equals(DamageGrow.growID))
            CardModifierManager.addModifier(card,new DamageGrow(amount));
        else if(effect.equals(AttackTimeGrow.growID))
            CardModifierManager.addModifier(card,new AttackTimeGrow(amount));
        else if(effect.equals(EnergyGrow.growID))
            CardModifierManager.addModifier(card,new EnergyGrow(amount));
        else if(effect.equals(BlockGrow.growID))
            CardModifierManager.addModifier(card,new BlockGrow(amount));
        else if(effect.equals(BaseDamageGrow.growID))
            CardModifierManager.addModifier(card,new BaseDamageGrow(amount));
        else if(effect.equals(BaseBlockGrow.growID))
            CardModifierManager.addModifier(card,new BaseBlockGrow(amount));
        else if(effect.equals(DrawCardGrow.growID))
            CardModifierManager.addModifier(card,new DrawCardGrow(amount));
        else if(effect.equals(SleepyGrow.growID))
            CardModifierManager.addModifier(card,new SleepyGrow(amount));
        else if(effect.equals(BlockTimeGrow.growID))
            CardModifierManager.addModifier(card,new BlockTimeGrow(amount));
    }

    public static void growAll(String effect, int amount){
        growAllHand(effect, amount);
        growAllDraw(effect, amount);
        growAllDiscard(effect, amount);
        growAllExhaust(effect, amount);
        growAllTempSave(effect, amount);
    }

    public static void growAllHand(String effect, int amount){
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            grow(c, effect, amount);
        }
    }

    public static void growAllDraw(String effect, int amount){
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            grow(c, effect, amount);
        }
    }

    public static void growAllDiscard(String effect, int amount){
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            grow(c, effect, amount);
        }
    }

    public static void growAllExhaust(String effect, int amount){
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            grow(c, effect, amount);
        }
    }

    public static void growAllTempSave(String effect, int amount){
        if(AbstractDungeon.player.hasPower(TempSavePower.POWER_ID)){
            TempSavePower tempSavePower = (TempSavePower) AbstractDungeon.player.getPower(TempSavePower.POWER_ID);
            for(AbstractCard c:tempSavePower.getCards()){
                grow(c, effect, amount);
            }
        }
    }

    public static void growAllHandEN(String effect, int amount){
        for (AbstractCard c : AbstractCharBoss.boss.hand.group) {
            grow(c, effect, amount);
        }
    }

    public static void growAllTempSaveEN(String effect, int amount){
        if(AbstractCharBoss.boss.hasPower(TempSavePower.POWER_ID)){
            TempSavePower tempSavePower = (TempSavePower) AbstractCharBoss.boss.getPower(TempSavePower.POWER_ID);
            for(AbstractCard c:tempSavePower.getCards()){
                grow(c, effect, amount);
            }
        }
    }

    public static void growAllDiscardEN(String effect, int amount){
        for (AbstractCard c : AbstractCharBoss.boss.cardInBattle.values()) {
            if(c != null && !AbstractCharBoss.boss.hand.contains(c))
                grow(c, effect, amount);
        }
    }

    public static void growAllEN(String effect, int amount){
        for (AbstractCard c : AbstractCharBoss.boss.cardInBattle.values()) {
            grow(c, effect, amount);
        }
    }

    public static boolean hasGrow(AbstractCard card, String effect) {
        for(AbstractCardModifier modifier:CardModifierManager.modifiers(card)){
            if(modifier instanceof AbstractGrowEffect){
                AbstractGrowEffect growEffect = (AbstractGrowEffect) modifier;
                if(growEffect.growEffectID.equals(effect)){
                    return true;
                }
            }
        }
        return false;
    }
}
