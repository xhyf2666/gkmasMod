package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.Champ;
import com.megacrit.cardcrawl.monsters.exordium.TheGuardian;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.ThreeSizeHelper;


public class ChampPatch
{
    @SpirePatch(clz = Champ.class,method = SpirePatch.CONSTRUCTOR)
    public static class PostPatchChamp_Constructor {
        @SpirePostfixPatch
        public static void Post(Champ __instance, @ByRef int[] ___blockAmt, @ByRef int[] ___forgeAmt) {
            if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasRelic(PocketBook.ID)){
                int rate = ThreeSizeHelper.getHealthRate(2);
                ___blockAmt[0] = ___blockAmt[0] * rate;
                ___forgeAmt[0] = ___forgeAmt[0] * rate;
            }
        }
    }
}

