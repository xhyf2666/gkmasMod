package gkmasmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.anomaly.ComprehensiveArt;
import gkmasmod.cards.anomaly.IdealTempo;
import gkmasmod.cards.anomaly.TakeFlight;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.powers.StanceLock;
import gkmasmod.powers.StepOnStagePower;
import gkmasmod.powers.TempSavePower;
import gkmasmod.relics.PledgePetal;
import gkmasmod.relics.PocketBook;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.stances.FullPowerStance;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.StanceHelper;


public class ChangeStanceActionPatch
{
    /**
     * 切换姿态的判断逻辑
     */
    @SpirePatch(clz = ChangeStanceAction.class,method = "update")
    public static class InsertPatchChangeStanceAction_update{
        @SpireInsertPatch(rloc = 7)
        public static SpireReturn<Void> Insert(ChangeStanceAction __instance, String ___id) {
            AbstractStance oldStance = AbstractDungeon.player.stance;
            AbstractStance newStance = new NeutralStance();

            if(oldStance.ID.equals(FullPowerStance.STANCE_ID)){
                // 全力姿态下，不允许切换姿态
                if(___id.equals(FullPowerStance.STANCE_ID2)){
                    newStance = AbstractStance.getStanceFromName(NeutralStance.STANCE_ID);
                    for (AbstractPower p : AbstractDungeon.player.powers)
                        p.onChangeStance(oldStance, newStance);
                    for (AbstractRelic r : AbstractDungeon.player.relics)
                        r.onChangeStance(oldStance, newStance);
                    oldStance.onExitStance();
                    AbstractDungeon.player.stance = newStance;
                    newStance.onEnterStance();
                    String key = NeutralStance.STANCE_ID;
                    if (AbstractDungeon.actionManager.uniqueStancesThisCombat.containsKey(key)) {
                        int currentCount = (AbstractDungeon.actionManager.uniqueStancesThisCombat.get(key)).intValue();
                        AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(currentCount + 1));
                    } else {
                        AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(1));
                    }
                    AbstractDungeon.player.switchedStance();
                    for (AbstractCard c : AbstractDungeon.player.discardPile.group)
                        c.triggerExhaustedCardsOnStanceChange(newStance);
                    AbstractDungeon.player.onStanceChange(___id);
                }
                __instance.isDone = true;
                return SpireReturn.Return();
            }

            if(AbstractDungeon.player.hasPower(StanceLock.POWER_ID)){
                __instance.isDone = true;
                return SpireReturn.Return(null);
            }

            //从悠闲进入其他姿态时
            if(oldStance.ID.equals(PreservationStance.STANCE_ID)){
                if(StanceHelper.isInStance(oldStance,PreservationStance.STANCE_ID,2)){
                    //无法从悠闲进入温存
                    if(___id.equals(PreservationStance.STANCE_ID)||___id.equals(PreservationStance.STANCE_ID2)||___id.equals(PreservationStance.STANCE_ID3)){
                        __instance.isDone = true;
                        return SpireReturn.Return();
                    }
                    if(___id.equals(ConcentrationStance.STANCE_ID2)){
                        if(AbstractDungeon.player.hasPower(StepOnStagePower.POWER_ID)){
                            AbstractDungeon.player.getPower(StepOnStagePower.POWER_ID).onSpecificTrigger();
                        }
                    }
                    newStance = AbstractStance.getStanceFromName(___id);
                    for (AbstractPower p : AbstractDungeon.player.powers)
                        p.onChangeStance(oldStance, newStance);
                    for (AbstractRelic r : AbstractDungeon.player.relics)
                        r.onChangeStance(oldStance, newStance);
                    PreservationStance preservationStance = (PreservationStance) oldStance;
                    preservationStance.onExitSpecialStance(newStance);
                    AbstractDungeon.player.stance = newStance;
                    newStance.onEnterStance();
                    String key = ___id;
                    if (AbstractDungeon.actionManager.uniqueStancesThisCombat.containsKey(key)) {
                        int currentCount = (AbstractDungeon.actionManager.uniqueStancesThisCombat.get(key)).intValue();
                        AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(currentCount + 1));
                    } else {
                        AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(1));
                    }
                    AbstractDungeon.player.switchedStance();
                    for (AbstractCard c : AbstractDungeon.player.discardPile.group)
                        c.triggerExhaustedCardsOnStanceChange(newStance);
                    AbstractDungeon.player.onStanceChange(___id);
                }
            }

            if(___id.equals(PreservationStance.STANCE_ID)||___id.equals(PreservationStance.STANCE_ID2)){
                if(oldStance.ID.equals(PreservationStance.STANCE_ID)){
                    //如果之前处于温存
                    PreservationStance current = (PreservationStance)oldStance;
                    if(StanceHelper.isInStance(oldStance,PreservationStance.STANCE_ID)){
                        //从温存1进入温存2
                        if(AbstractDungeon.player.hasPower(StepOnStagePower.POWER_ID)){
                            AbstractDungeon.player.getPower(StepOnStagePower.POWER_ID).onSpecificTrigger();
                        }
                        String key = PreservationStance.STANCE_ID2;
                        if (AbstractDungeon.actionManager.uniqueStancesThisCombat.containsKey(key)) {
                            int currentCount = (AbstractDungeon.actionManager.uniqueStancesThisCombat.get(key)).intValue();
                            AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(currentCount + 1));
                        } else {
                            AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(1));
                        }
                    }
                    current.onEnterSameStance();
                }
                else{
                    //之前不处于温存
                    if(___id.equals(PreservationStance.STANCE_ID2)){
                        if(AbstractDungeon.player.hasPower(StepOnStagePower.POWER_ID)){
                            AbstractDungeon.player.getPower(StepOnStagePower.POWER_ID).onSpecificTrigger();
                        }
                    }
                    newStance = AbstractStance.getStanceFromName(___id);
                    for (AbstractPower p : AbstractDungeon.player.powers)
                        p.onChangeStance(oldStance, newStance);
                    for (AbstractRelic r : AbstractDungeon.player.relics)
                        r.onChangeStance(oldStance, newStance);
                    oldStance.onExitStance();
                    AbstractDungeon.player.stance = newStance;
                    newStance.onEnterStance();
                    String key = ___id;
                    if (AbstractDungeon.actionManager.uniqueStancesThisCombat.containsKey(key)) {
                        int currentCount = (AbstractDungeon.actionManager.uniqueStancesThisCombat.get(key)).intValue();
                        AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(currentCount + 1));
                    } else {
                        AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(1));
                    }
                    //进入温存2时，额外记录进入温存的次数
                    if(key.equals(PreservationStance.STANCE_ID2)){
                        key = PreservationStance.STANCE_ID;
                        if (AbstractDungeon.actionManager.uniqueStancesThisCombat.containsKey(key)) {
                            int currentCount = (AbstractDungeon.actionManager.uniqueStancesThisCombat.get(key)).intValue();
                            AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(currentCount + 1));
                        } else {
                            AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(1));
                        }
                    }
                    AbstractDungeon.player.switchedStance();
                    for (AbstractCard c : AbstractDungeon.player.discardPile.group)
                        c.triggerExhaustedCardsOnStanceChange(newStance);
                    AbstractDungeon.player.onStanceChange(___id);
                }
            }
            else if(___id.equals(PreservationStance.STANCE_ID3)){
                if(oldStance.ID.equals(PreservationStance.STANCE_ID)){
                    //从温存进入悠闲
                    PreservationStance current = (PreservationStance)oldStance;
                    if(!StanceHelper.isInStance(oldStance,PreservationStance.STANCE_ID,2)){
                        //之前不处于悠闲
                        if(AbstractDungeon.player.hasPower(StepOnStagePower.POWER_ID)){
                            AbstractDungeon.player.getPower(StepOnStagePower.POWER_ID).onSpecificTrigger();
                        }
                        String key = PreservationStance.STANCE_ID3;
                        if (AbstractDungeon.actionManager.uniqueStancesThisCombat.containsKey(key)) {
                            int currentCount = (AbstractDungeon.actionManager.uniqueStancesThisCombat.get(key)).intValue();
                            AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(currentCount + 1));
                        } else {
                            AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(1));
                        }
                    }
                    current.onEnterSpecialStance();
                }
                else{
                    //从其他姿态进入悠闲
                    newStance = AbstractStance.getStanceFromName(___id);
                    for (AbstractPower p : AbstractDungeon.player.powers)
                        p.onChangeStance(oldStance, newStance);
                    for (AbstractRelic r : AbstractDungeon.player.relics)
                        r.onChangeStance(oldStance, newStance);
                    oldStance.onExitStance();
                    AbstractDungeon.player.stance = newStance;
                    newStance.onEnterStance();
                    String key = ___id;
                    if (AbstractDungeon.actionManager.uniqueStancesThisCombat.containsKey(key)) {
                        int currentCount = (AbstractDungeon.actionManager.uniqueStancesThisCombat.get(key)).intValue();
                        AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(currentCount + 1));
                    } else {
                        AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(1));
                    }
                    AbstractDungeon.player.switchedStance();
                    for (AbstractCard c : AbstractDungeon.player.discardPile.group)
                        c.triggerExhaustedCardsOnStanceChange(newStance);
                    AbstractDungeon.player.onStanceChange(___id);
                }
            }
            else if(___id.equals(ConcentrationStance.STANCE_ID)||___id.equals(ConcentrationStance.STANCE_ID2)){
                if(oldStance.ID.equals(ConcentrationStance.STANCE_ID)){
                    ConcentrationStance current = (ConcentrationStance)oldStance;
                    if(StanceHelper.isInStance(oldStance,ConcentrationStance.STANCE_ID)){
                        //从强气1进入强气2
                        if(AbstractDungeon.player.hasPower(StepOnStagePower.POWER_ID)){
                            AbstractDungeon.player.getPower(StepOnStagePower.POWER_ID).onSpecificTrigger();
                        }
                        String key = ConcentrationStance.STANCE_ID2;
                        if (AbstractDungeon.actionManager.uniqueStancesThisCombat.containsKey(key)) {
                            int currentCount = (AbstractDungeon.actionManager.uniqueStancesThisCombat.get(key)).intValue();
                            AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(currentCount + 1));
                        } else {
                            AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(1));
                        }
                    }
                    current.onEnterSameStance();
                }
                else{
                    if(___id.equals(ConcentrationStance.STANCE_ID2)){
                        if(AbstractDungeon.player.hasPower(StepOnStagePower.POWER_ID)){
                            AbstractDungeon.player.getPower(StepOnStagePower.POWER_ID).onSpecificTrigger();
                        }
                    }
                    newStance = AbstractStance.getStanceFromName(___id);
                    for (AbstractPower p : AbstractDungeon.player.powers)
                        p.onChangeStance(oldStance, newStance);
                    for (AbstractRelic r : AbstractDungeon.player.relics)
                        r.onChangeStance(oldStance, newStance);
                    oldStance.onExitStance();
                    AbstractDungeon.player.stance = newStance;
                    newStance.onEnterStance();
                    String key = ___id;
                    if (AbstractDungeon.actionManager.uniqueStancesThisCombat.containsKey(key)) {
                        int currentCount = (AbstractDungeon.actionManager.uniqueStancesThisCombat.get(key)).intValue();
                        AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(currentCount + 1));
                    } else {
                        AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(1));
                    }
                    //进入强气2时，额外记录进入强气的次数
                    if(key.equals(ConcentrationStance.STANCE_ID2)){
                        key = ConcentrationStance.STANCE_ID;
                        if (AbstractDungeon.actionManager.uniqueStancesThisCombat.containsKey(key)) {
                            int currentCount = (AbstractDungeon.actionManager.uniqueStancesThisCombat.get(key)).intValue();
                            AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(currentCount + 1));
                        } else {
                            AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(1));
                        }
                    }
                    AbstractDungeon.player.switchedStance();
                    for (AbstractCard c : AbstractDungeon.player.discardPile.group)
                        c.triggerExhaustedCardsOnStanceChange(newStance);
                    AbstractDungeon.player.onStanceChange(___id);
                }
            }
            else if(___id.equals(FullPowerStance.STANCE_ID)){
                newStance = AbstractStance.getStanceFromName(___id);
                for (AbstractPower p : AbstractDungeon.player.powers)
                    p.onChangeStance(oldStance, newStance);
                for (AbstractRelic r : AbstractDungeon.player.relics)
                    r.onChangeStance(oldStance, newStance);
                oldStance.onExitStance();
                AbstractDungeon.player.stance = newStance;
                newStance.onEnterStance();
                String key = FullPowerStance.STANCE_ID;
                if (AbstractDungeon.actionManager.uniqueStancesThisCombat.containsKey(key)) {
                    int currentCount = (AbstractDungeon.actionManager.uniqueStancesThisCombat.get(key)).intValue();
                    AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(currentCount + 1));
                } else {
                    AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(1));
                }
                AbstractDungeon.player.switchedStance();
                for (AbstractCard c : AbstractDungeon.player.discardPile.group)
                    c.triggerExhaustedCardsOnStanceChange(newStance);
                AbstractDungeon.player.onStanceChange(___id);
            }
            else if(___id.equals(FullPowerStance.STANCE_ID2)){
                __instance.isDone = true;
                return SpireReturn.Return();
            }
            else{
                return SpireReturn.Continue();
            }
            AbstractDungeon.onModifyPower();
            __instance.isDone = true;
            return SpireReturn.Return();
        }
    }

}

