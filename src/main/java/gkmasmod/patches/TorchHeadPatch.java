package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.BronzeAutomaton;
import com.megacrit.cardcrawl.monsters.city.BronzeOrb;
import com.megacrit.cardcrawl.monsters.city.TheCollector;
import com.megacrit.cardcrawl.monsters.city.TorchHead;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.ThreeSizeHelper;


public class TorchHeadPatch
{

    @SpirePatch(clz = TorchHead.class,method = SpirePatch.CONSTRUCTOR, paramtypez = {float.class, float.class})
    public static class TorchHeadPostPatch_constructor {
        @SpirePostfixPatch
        public static void Post(TorchHead __instance,float x, float y) {
            if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                __instance.maxHealth = ThreeSizeHelper.getHealthRate(2)*__instance.maxHealth;
                __instance.currentHealth = ThreeSizeHelper.getHealthRate(2)*__instance.currentHealth;
            }
        }
    }

}

