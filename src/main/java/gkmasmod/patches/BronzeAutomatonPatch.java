package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.BronzeAutomaton;
import com.megacrit.cardcrawl.monsters.city.Champ;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.ThreeSizeHelper;


public class BronzeAutomatonPatch
{

    @SpirePatch(clz = BronzeAutomaton.class,method = SpirePatch.CONSTRUCTOR)
    public static class ChampPostPatch_constructor {
        @SpirePostfixPatch
        public static void Post(BronzeAutomaton __instance, @ByRef int[] ___blockAmt) {
            if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                int rate = ThreeSizeHelper.getHealthRate(2);
                ___blockAmt[0] = ___blockAmt[0] * rate;
            }
        }
    }
}

