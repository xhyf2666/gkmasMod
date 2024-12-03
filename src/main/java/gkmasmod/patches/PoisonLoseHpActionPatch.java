package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.DarkOrbEvokeAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.powers.AllEffort;
import gkmasmod.relics.PocketBook;


public class PoisonLoseHpActionPatch
{

    @SpirePatch(clz = PoisonLoseHpAction.class,method = SpirePatch.CONSTRUCTOR)
    public static class PoisonLoseHpActionPostPatch_constructor {
        @SpirePostfixPatch
        public static void Post(PoisonLoseHpAction __instance) {
            if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                float rate=1.0f;
                if(AbstractDungeon.player.hasPower(AllEffort.POWER_ID)){
                    if(AbstractPlayerPatch.FinalCircleRoundField.finalCircleRound.get(AbstractDungeon.player).size()>0){
                        rate = (float) (AbstractPlayerPatch.FinalDamageRateField.finalDamageRate.get(AbstractDungeon.player)*1.0f);
                    }
                    __instance.amount = (int) (rate*__instance.amount);
                }
            }
        }
    }

}

