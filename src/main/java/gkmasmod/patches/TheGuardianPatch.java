package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.monsters.exordium.TheGuardian;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.ThreeSizeHelper;


public class TheGuardianPatch
{


    @SpirePatch(clz = TheGuardian.class,method = SpirePatch.CONSTRUCTOR)
    public static class PostPatchTheGuardian_Constructor {
        @SpirePostfixPatch
        public static void Post(TheGuardian __instance,@ByRef int[] ___dmgThreshold, @ByRef int[] ___dmgThresholdIncrease, @ByRef int[] ___blockAmount,@ByRef int[] ___DEFENSIVE_BLOCK) {
            if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasRelic(PocketBook.ID)){
                int rate = ThreeSizeHelper.getHealthRate(1);
                ___dmgThreshold[0] = ___dmgThreshold[0] * rate;
                ___dmgThresholdIncrease[0] = ___dmgThresholdIncrease[0] * rate;
                ___blockAmount[0] = ___blockAmount[0] * rate;
                ___DEFENSIVE_BLOCK[0] = ___DEFENSIVE_BLOCK[0] * rate;
            }
        }
    }
}

