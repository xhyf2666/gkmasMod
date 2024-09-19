package gkmasmod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;

public class MapRoomNodePatch {

    public static Texture spImg = ImageMaster.loadImage("gkmasModResource/img/UI/sp.png");

    @SpirePatch(clz = MapRoomNode.class,method = SpirePatch.CLASS)
    public static class SPField {
        public static SpireField<Boolean> isSP = new SpireField<>(() -> false);
    }

    @SpirePatch(clz = MapRoomNode.class, method = "render")
    public static class PostPatchMapRoomNode_render {
        @SpirePostfixPatch
        public static void postfix(MapRoomNode __instance, SpriteBatch sb,float ___scale) {
            if(SPField.isSP.get(__instance)){
                sb.setColor(Color.LIGHT_GRAY);
                sb.draw(spImg, (float)__instance.x*Settings.xScale*128.0F + 580.0F * Settings.xScale - 40.0F + __instance.offsetX, (float)__instance.y * Settings.MAP_DST_Y + 200.0F * Settings.scale + DungeonMapScreen.offsetY - 30.0F + __instance.offsetY, 32.0F, 12.0F, 96.0F, 96.0F, ___scale * Settings.scale, ___scale * Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
            }
        }
    }
}
