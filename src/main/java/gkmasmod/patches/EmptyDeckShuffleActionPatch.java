package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.powers.BornImitatorPower;
import gkmasmod.powers.PracticeAgainPower;
import gkmasmod.powers.SakiPracticePower;


public class EmptyDeckShuffleActionPatch
{

    @SpirePatch(clz = EmptyDeckShuffleAction.class,method = SpirePatch.CONSTRUCTOR)
    public static class EmptyDeckShuffleActionInsertPatch_update{
        @SpirePostfixPatch()
        public static void postfix(EmptyDeckShuffleAction __instance) {
            if(AbstractDungeon.player.hasPower(PracticeAgainPower.POWER_ID))
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

