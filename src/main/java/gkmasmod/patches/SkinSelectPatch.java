package gkmasmod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;

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

    public static boolean isGkmasMod() {
        return (CardCrawlGame.chosenCharacter == PlayerColorEnum.gkmasMod_character);
    }


    @SpirePatch(clz = CharacterOption.class, method = "update")
    public static class CharacterOptionPatch_update
    {
        @SpirePostfixPatch
        public static void Postfix(CharacterOption __instance,@ByRef String[] ___hp,@ByRef int[] ___gold,@ByRef CharSelectInfo[] ___charInfo,@ByRef float[] ___infoX, float ___DEST_INFO_X)
        {
            if(__instance.c instanceof IdolCharacter){
                ___charInfo[0] = __instance.c.getLoadout();
                __instance.name = SkinSelectScreen.Inst.curName;
            }
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
        String name = IdolData.idolNames[SkinSelectScreen.Inst.idolIndex];
        String skinName = IdolData.getIdol(name).getSkin(SkinSelectScreen.Inst.skinIndex);
        int updateIndex = SkinSelectScreen.Inst.updateIndex;
        System.out.println(skinName);
        String IMG_PATH = String.format("gkmasModResource/img/charSelect/%s_%s_%d.jpg", name, skinName,updateIndex);
        // TODO 检查文件是否存在
        return new Texture(IMG_PATH);
    }
}

