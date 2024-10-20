package gkmasmod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;

public class AbstractPlayerPatch {

    @SpirePatch(clz = AbstractPlayer.class,method = SpirePatch.CLASS)
    public static class BlockField {
        public static SpireField<Integer> ThisCombatBlock = new SpireField<>(() -> 0);
    }

    @SpirePatch(clz = AbstractPlayer.class,method = SpirePatch.CLASS)
    public static class ThreeSizeField {
        public static SpireField<Integer[]> threeSize = new SpireField<>(() -> new Integer[]{0,0,0});
    }

    @SpirePatch(clz = AbstractPlayer.class,method = SpirePatch.CLASS)
    public static class PreThreeSizeField {
        public static SpireField<Integer[]> preThreeSize = new SpireField<>(() -> new Integer[]{0,0,0});
    }

    @SpirePatch(clz = AbstractPlayer.class,method = SpirePatch.CLASS)
    public static class ThreeSizeRateField {
        public static SpireField<Float[]> threeSizeRate = new SpireField<>(() -> new Float[]{0f,0f,0f});
    }

    @SpirePatch(clz = AbstractPlayer.class,method = "applyStartOfCombatLogic")
    public static class AbstractPlayerPrefixPatch_applyStartOfCombatLogic {
        @SpirePrefixPatch
        public static void Prefix() {
            BlockField.ThisCombatBlock.set(AbstractDungeon.player, 0);
        }
    }

}
