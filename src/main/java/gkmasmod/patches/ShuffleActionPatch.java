package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import gkmasmod.cards.anomaly.ComprehensiveArt;
import gkmasmod.cards.anomaly.IdealTempo;
import gkmasmod.powers.*;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.stances.FullPowerStance;
import gkmasmod.stances.PreservationStance;


public class ShuffleActionPatch
{

    @SpirePatch(clz = ShuffleAction.class,method = "update")
    public static class ShuffleActionInsertPatch_update{
        @SpirePrefixPatch()
        public static void prefix(ShuffleAction __instance, boolean ___triggerRelics) {
            if (___triggerRelics) {
                if (AbstractDungeon.player.hasPower(PracticeAgainPower.POWER_ID))
                    AbstractDungeon.player.getPower(PracticeAgainPower.POWER_ID).onSpecificTrigger();
                for(AbstractMonster mo:AbstractDungeon.getMonsters().monsters){
                    if(!mo.isDeadOrEscaped()&&mo.hasPower(BornImitatorPower.POWER_ID)){
                        mo.getPower(BornImitatorPower.POWER_ID).onSpecificTrigger();
                    }
                    if(!mo.isDeadOrEscaped()&&mo.hasPower(SakiPracticePower.POWER_ID)){
                        mo.getPower(SakiPracticePower.POWER_ID).onSpecificTrigger();
                    }
                }
            }
        }
    }

}

