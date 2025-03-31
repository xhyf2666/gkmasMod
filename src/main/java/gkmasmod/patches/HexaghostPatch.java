package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.Champ;
import com.megacrit.cardcrawl.monsters.exordium.Hexaghost;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.ThreeSizeHelper;


public class HexaghostPatch
{


    @SpirePatch(clz = Hexaghost.class,method = SpirePatch.CONSTRUCTOR)
    public static class HexaghostPostPatch_constructor {
        @SpirePostfixPatch
        public static void Post(Hexaghost __instance, @ByRef int[] ___strengthenBlockAmt) {
            if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasRelic(PocketBook.ID)){
                int rate = ThreeSizeHelper.getHealthRate(1);
                ___strengthenBlockAmt[0] = ___strengthenBlockAmt[0] * rate;
            }
        }
    }
}

