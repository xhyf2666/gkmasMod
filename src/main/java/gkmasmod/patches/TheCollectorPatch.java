package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.Champ;
import com.megacrit.cardcrawl.monsters.city.TheCollector;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.ThreeSizeHelper;


public class TheCollectorPatch
{


    @SpirePatch(clz = TheCollector.class,method = SpirePatch.CONSTRUCTOR)
    public static class ChampPostPatch_constructor {
        @SpirePostfixPatch
        public static void Post(TheCollector __instance, @ByRef int[] ___blockAmt) {
            if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasRelic(PocketBook.ID)){
                int rate = ThreeSizeHelper.getHealthRate(2);
                if (AbstractDungeon.ascensionLevel >= 19) {
                    ___blockAmt[0] = (___blockAmt[0]+5) * rate-5;
                } else {
                    ___blockAmt[0] = ___blockAmt[0] * rate;
                }
            }
        }
    }
}

