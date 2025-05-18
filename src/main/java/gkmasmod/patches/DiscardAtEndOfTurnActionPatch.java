package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.DiscardAtEndOfTurnAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.powers.BornImitatorPower;
import gkmasmod.powers.FriendTemariPower2;
import gkmasmod.powers.PracticeAgainPower;


public class DiscardAtEndOfTurnActionPatch
{

    /**
     * 回合结束时，触发伙伴tmr的进食效果
     */
    @SpirePatch(clz = DiscardAtEndOfTurnAction.class,method = "update")
    public static class InsertPatchDiscardAtEndOfTurnAction_update{
        @SpireInsertPatch(rloc = 34-24)
        public static void insert(DiscardAtEndOfTurnAction __instance) {
            for(AbstractMonster mo:AbstractDungeon.getMonsters().monsters){
                if(!mo.isDeadOrEscaped()&&mo.hasPower(FriendTemariPower2.POWER_ID)){
                    ((FriendTemariPower2)mo.getPower(FriendTemariPower2.POWER_ID)).eatSomething(AbstractDungeon.player.hand.size());
                }
            }
        }
    }

}

