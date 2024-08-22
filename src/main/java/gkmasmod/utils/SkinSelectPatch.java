package gkmasmod.utils;

import basemod.ReflectionHacks;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.ui.SkinSelectScreen;

import java.io.InputStream;
import java.net.URL;
import java.util.logging.FileHandler;

public class SkinSelectPatch {
    public static boolean isGkmasSelected() {
        return (CardCrawlGame.chosenCharacter == PlayerColorEnum.gkmasMod_character && (
           (Boolean)ReflectionHacks.getPrivate(CardCrawlGame.mainMenuScreen.charSelectScreen, CharacterSelectScreen.class, "anySelected")).booleanValue());
    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "update")
    public static class UpdateButtonPatch {
        public static void Prefix(CharacterSelectScreen _inst) {
            if (SkinSelectPatch.isGkmasSelected())
                SkinSelectScreen.Inst.update();
        }
    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "render")
    public static class RenderButtonPatch {
        public static void Postfix(CharacterSelectScreen _inst, SpriteBatch sb) {
            if (SkinSelectPatch.isGkmasSelected())
                SkinSelectScreen.Inst.render(sb);
        }
    }


    @SpirePatch(clz = CharacterSelectScreen.class, method = "update")
    public static class CharacterSelectScreenPatch_Update
    {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen __instance)
        {
            if (SkinSelectPatch.isGkmasSelected() && SkinSelectScreen.isClick)
            {
                SkinSelectScreen.isClick = false;
                __instance.bgCharImg = getSkinTexture();
            }
        }
    }

    public static Texture getSkinTexture() {
        String name = IdolNameString.idolNames[SkinSelectScreen.Inst.idolIndex];
        String skinName = IdolSkin.SKINS.get(name).get(SkinSelectScreen.Inst.skinIndex);
        int updateIndex = SkinSelectScreen.Inst.updateIndex;
        System.out.println(skinName);
        String IMG_PATH = String.format("img/charSelect/%s_%s_%d.jpg", name, skinName,updateIndex);
        // TODO 检查文件是否存在
        return new Texture(IMG_PATH);
    }
}

