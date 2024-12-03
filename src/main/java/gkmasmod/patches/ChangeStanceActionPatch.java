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


public class ChangeStanceActionPatch
{

    @SpirePatch(clz = ChangeStanceAction.class,method = "update")
    public static class ChangeStanceActionInsertPatch_update{
        @SpireInsertPatch(rloc = 7)
        public static SpireReturn<Void> Insert(ChangeStanceAction __instance, String ___id) {
            AbstractStance oldStance = AbstractDungeon.player.stance;
            AbstractStance newStance = new NeutralStance();

            if(AbstractDungeon.player.hasPower(StanceLock.POWER_ID)){
                __instance.isDone = true;
                return SpireReturn.Return(null);
            }

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
                    AbstractDungeon.player.onStanceChange(___id);
                }
                __instance.isDone = true;
                return SpireReturn.Return();
            }

            if(___id.equals(PreservationStance.STANCE_ID)||___id.equals(PreservationStance.STANCE_ID2)){
                if(oldStance.ID.equals(PreservationStance.STANCE_ID)){
                    PreservationStance current = (PreservationStance)oldStance;
                    int stage = current.stage;
                    if(stage ==0){
                        //从温存1进入温存2
                        if(AbstractDungeon.player.hasPower(StepOnStagePower.POWER_ID)){
                            AbstractDungeon.player.getPower(StepOnStagePower.POWER_ID).onSpecificTrigger();
                        }
                    }
                    current.onEnterSameStance();
                    String key = PreservationStance.STANCE_ID2;
                    if (AbstractDungeon.actionManager.uniqueStancesThisCombat.containsKey(key)) {
                        int currentCount = (AbstractDungeon.actionManager.uniqueStancesThisCombat.get(key)).intValue();
                        AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(currentCount + 1));
                    } else {
                        AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(1));
                    }
                }
                else{
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
                    AbstractDungeon.player.switchedStance();
                    AbstractDungeon.player.onStanceChange(___id);
                }
            }
            else if(___id.equals(ConcentrationStance.STANCE_ID)||___id.equals(ConcentrationStance.STANCE_ID2)){
                if(oldStance.ID.equals(ConcentrationStance.STANCE_ID)){
                    ConcentrationStance current = (ConcentrationStance)oldStance;
                    int stage = current.stage;
                    if(stage ==0){
                        //从强气1进入强气2
                        if(AbstractDungeon.player.hasPower(StepOnStagePower.POWER_ID)){
                            AbstractDungeon.player.getPower(StepOnStagePower.POWER_ID).onSpecificTrigger();
                        }
                    }
                    current.onEnterSameStance();
                    String key = ConcentrationStance.STANCE_ID2;
                    if (AbstractDungeon.actionManager.uniqueStancesThisCombat.containsKey(key)) {
                        int currentCount = (AbstractDungeon.actionManager.uniqueStancesThisCombat.get(key)).intValue();
                        AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(currentCount + 1));
                    } else {
                        AbstractDungeon.actionManager.uniqueStancesThisCombat.put(key, Integer.valueOf(1));
                    }
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
                    AbstractDungeon.player.switchedStance();
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
                AbstractDungeon.player.onStanceChange(___id);
            }
            else{
                return SpireReturn.Continue();
            }
            AbstractDungeon.onModifyPower();
            if (Settings.FAST_MODE) {
                __instance.isDone = true;
            }
            return SpireReturn.Return();
        }
    }

    private static void changeStanceNotify(){
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group){
            if(c instanceof IdealTempo || c instanceof ComprehensiveArt){
                c.switchedStance();
            }
        }
        if(AbstractDungeon.player.hasPower(TempSavePower.POWER_ID)){
            TempSavePower tempSavePower = (TempSavePower) AbstractDungeon.player.getPower(TempSavePower.POWER_ID);
            for(AbstractCard c:tempSavePower.getCards()){
                if(c instanceof IdealTempo || c instanceof ComprehensiveArt){
                    c.switchedStance();
                }
            }
        }
    }

}

