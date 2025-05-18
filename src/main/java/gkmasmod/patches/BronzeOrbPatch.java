package gkmasmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.beyond.TimeEater;
import com.megacrit.cardcrawl.monsters.city.BronzeAutomaton;
import com.megacrit.cardcrawl.monsters.city.BronzeOrb;
import com.megacrit.cardcrawl.monsters.city.Champ;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.ThreeSizeHelper;


public class BronzeOrbPatch
{

    @SpirePatch(clz = BronzeOrb.class,method = SpirePatch.CONSTRUCTOR)
    public static class PostPatchBronzeOrb_Constructor {
        @SpirePostfixPatch
        public static void Post(BronzeOrb __instance, @ByRef int[] ___headSlamDmg) {
            if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasRelic(PocketBook.ID)){
                __instance.maxHealth = ThreeSizeHelper.getHealthRate(2)*__instance.maxHealth;
                __instance.currentHealth = ThreeSizeHelper.getHealthRate(2)*__instance.currentHealth;
            }
        }
    }

    @SpirePatch(clz = BronzeOrb.class,method = "takeTurn")
    public static class PostPatchBronzeOrb_takeTurn{
        @SpirePrefixPatch
        public static void Prefix(BronzeOrb __instance) {
            if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasRelic(PocketBook.ID)){
                if(__instance.nextMove ==2){
                    int change = ThreeSizeHelper.getHealthRate(2) -1;
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.getMonsters().getMonster("BronzeAutomaton"), __instance,change*12));
                }
            }

        }
    }
}

