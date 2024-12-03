package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.powers.RebirthPower;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.IdolData;

import java.util.ArrayList;

public class AbstractPlayerPatch {

    @SpirePatch(clz = AbstractPlayer.class,method = SpirePatch.CLASS)
    public static class ThreeSizeField {
        public static SpireField<int[]> threeSize = new SpireField<>(() -> new int[]{0,0,0});
    }

    @SpirePatch(clz = AbstractPlayer.class,method = SpirePatch.CLASS)
    public static class PreThreeSizeField {
        public static SpireField<int[]> preThreeSize = new SpireField<>(() -> new int[]{0,0,0});
    }

    @SpirePatch(clz = AbstractPlayer.class,method = SpirePatch.CLASS)
    public static class ThreeSizeRateField {
        public static SpireField<float[]> threeSizeRate = new SpireField<>(() -> new float[]{0f,0f,0f});
    }

    @SpirePatch(clz = AbstractPlayer.class,method = SpirePatch.CLASS)
    public static class FinalCircleRoundField {
        public static SpireField<ArrayList<Integer>> finalCircleRound = new SpireField<>(() -> new ArrayList<>());
    }

    @SpirePatch(clz = AbstractPlayer.class,method = SpirePatch.CLASS)
    public static class IsRenderFinalCircleField {
        public static SpireField<Boolean> IsRenderFinalCircle = new SpireField<>(() -> false);
    }

    @SpirePatch(clz = AbstractPlayer.class,method = SpirePatch.CLASS)
    public static class FinalDamageRateField {
        public static SpireField<Double> finalDamageRate = new SpireField<>(() -> 1.0);
    }

    @SpirePatch(clz = AbstractPlayer.class,method = "applyStartOfCombatLogic")
    public static class AbstractPlayerPrefixPatch_applyStartOfCombatLogic {
        @SpirePrefixPatch
        public static void Prefix() {
            AbstractCreaturePatch.BlockField.ThisCombatBlock.set(AbstractDungeon.player, 0);
        }
    }

    @SpirePatch(clz = AbstractPlayer.class,method = "gainGold")
    public static class AbstractPlayerPrefixPatch_gainGold {
        @SpirePrefixPatch
        public static void Prefix(AbstractPlayer __instance, int amount) {
            if(__instance instanceof IdolCharacter){
                IdolCharacter idolCharacter = (IdolCharacter) __instance;
                if(idolCharacter.idolData.idolName.equals(IdolData.fktn)){
                    if (amount <= 0) {
                    } else {
                        CardCrawlGame.goldGained += amount;
                        __instance.gold += amount;
                        for (AbstractRelic r : __instance.relics)
                            r.onGainGold();
                    }
                }
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class,method = "damage")
    public static class InsertPatchAbstractPlayer_damage {
        @SpireInsertPatch(rloc =1851-1725)
        public static SpireReturn<Void> Insert(AbstractPlayer __instance) {
            for(AbstractPower p:__instance.powers){
                if(p instanceof RebirthPower){
                    __instance.currentHealth = 0;
                    p.onSpecificTrigger();
                    return SpireReturn.Return(null);
                }
            }
            return SpireReturn.Continue();
        }
    }

//    @SpirePatch(clz = AbstractPlayer.class,method = "renderPowerTips")
//    public static class AbstractPlayerPrefixPatch_renderPowerTips {
//        @SpirePrefixPatch
//        public static void Prefix(AbstractPlayer __instance) {
//            System.out.println(__instance.stance);
//            System.out.println(__instance.stance.ID);
//        }
//    }

}
