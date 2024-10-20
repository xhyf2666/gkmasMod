package gkmasmod.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.utils.PlayerHelper;
import javassist.CtBehavior;

import java.util.ArrayList;

public class AbstractCreaturePatch {

    @SpirePatch(clz = AbstractCreature.class,method = SpirePatch.CLASS)
    public static class blockBakField {
        public static SpireField<Integer> blockBak = new SpireField<>(() -> 0);
    }

    @SpirePatch(clz = AbstractCreature.class,method = "addBlock")
    public static class PostPatchAbstractCreature_addBlock {
        @SpireInsertPatch(locator = Locator.class, localvars = {"tmp"})
        public static void Insert(AbstractCreature __instance, int blockAmount,float tmp) {
            if(__instance.isPlayer){
                int increase = MathUtils.floor(tmp);
                if(increase>9999){
                    increase = 9999;
                }
                int block = __instance.currentBlock + increase;
                if(block>9999){
                    block = 9999;
                }
                System.out.println("block:"+block);
                blockBakField.blockBak.set(__instance, block);

                int count = AbstractPlayerPatch.BlockField.ThisCombatBlock.get(AbstractDungeon.player);
                AbstractPlayerPatch.BlockField.ThisCombatBlock.set(AbstractDungeon.player, count+increase);
            }
        }
    }

    @SpirePatch(clz = AbstractCreature.class,method = "addBlock")
    public static class PostPatchAbstractCreature_addBlock2 {
        @SpireInsertPatch(rloc =37 ,localvars = {"tmp"})
        public static void Insert(AbstractCreature __instance, int blockAmount,float tmp) {
            if(__instance.isPlayer){
                System.out.println("block2:"+blockBakField.blockBak.get(__instance));
                __instance.currentBlock = blockBakField.blockBak.get(__instance);
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList(), (Matcher)methodCallMatcher);
        }
    }
}
