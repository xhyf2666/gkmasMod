package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.defect.LightningOrbEvokeAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbPassiveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.powers.AllEffort;
import gkmasmod.relics.PocketBook;


public class LightningOrbEvokeActionPatch
{

    /**
     * 电球伤害受到三维倍率的加成
     */
    @SpirePatch(clz = LightningOrbEvokeAction.class,method = SpirePatch.CONSTRUCTOR)
    public static class PostPatchLightningOrbEvokeAction_Constructor {
        @SpirePostfixPatch
        public static void Post(LightningOrbEvokeAction __instance, @ByRef DamageInfo[] ___info) {
            if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                float rate=1.0f;
                if(AbstractDungeon.player.hasPower(AllEffort.POWER_ID)){
                    if(AbstractPlayerPatch.FinalCircleRoundField.finalCircleRound.get(AbstractDungeon.player).size()>0){
                        rate = (float) (AbstractPlayerPatch.FinalDamageRateField.finalDamageRate.get(AbstractDungeon.player)*1.0f);
                    }
                    ___info[0].base = (int) (rate*___info[0].base);
                }
            }
        }
    }

}

