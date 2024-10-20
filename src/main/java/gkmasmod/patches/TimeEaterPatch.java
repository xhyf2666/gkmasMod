package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Deca;
import com.megacrit.cardcrawl.monsters.beyond.TimeEater;
import com.megacrit.cardcrawl.monsters.city.TheCollector;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.ThreeSizeHelper;


public class TimeEaterPatch
{


    @SpirePatch(clz = TimeEater.class,method = SpirePatch.CONSTRUCTOR)
    public static class TimeEaterPostPatch_constructor {
        @SpirePostfixPatch
        public static void Post(TimeEater __instance, @ByRef int[] ___headSlamDmg) {
            if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                int rate = ThreeSizeHelper.getHealthRate(3);
                ___headSlamDmg[0] = ___headSlamDmg[0] * rate;
            }
        }
    }

    @SpirePatch(clz = TimeEater.class,method = "takeTurn")
    public static class TimeEaterPrePatch_RenderCard {
        @SpirePrefixPatch
        public static void Prefix(TimeEater __instance) {
            if (__instance.nextMove == 3) {
                int change = ThreeSizeHelper.getHealthRate(3) - 1;
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(__instance, __instance, 20 * change));
            }
        }
    }
}

