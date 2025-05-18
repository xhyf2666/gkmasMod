package gkmasmod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.screen.OtherSkinSelectScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;

import java.util.Random;

public class CharacterSelectScreenPatch {
    public static boolean isGkmasSelected() {
        return (CardCrawlGame.chosenCharacter == PlayerColorEnum.gkmasMod_character && (Boolean) ReflectionHacks.getPrivate(CardCrawlGame.mainMenuScreen.charSelectScreen, CharacterSelectScreen.class, "anySelected"));
    }

    public static boolean isGkmasOtherSelected() {
        return (CardCrawlGame.chosenCharacter == PlayerColorEnum.gkmasModOther_character && (Boolean) ReflectionHacks.getPrivate(CardCrawlGame.mainMenuScreen.charSelectScreen, CharacterSelectScreen.class, "anySelected"));
    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "update")
    public static class PrePatchCharacterSelectScreen_update {
        public static void Prefix(CharacterSelectScreen _inst) {
            if (CharacterSelectScreenPatch.isGkmasSelected())
                SkinSelectScreen.Inst.update();
            else{
                if (SkinSelectScreen.Inst.videoPlayer != null) {
                    SkinSelectScreen.Inst.videoPlayer.dispose();
                    SkinSelectScreen.Inst.videoPlayer = null;
                }
            }
            if(CharacterSelectScreenPatch.isGkmasOtherSelected())
                OtherSkinSelectScreen.Inst.update();
        }
    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "render")
    public static class PostPatchCharacterSelectScreen_render {
        public static void Postfix(CharacterSelectScreen _inst, SpriteBatch sb) {
            if (CharacterSelectScreenPatch.isGkmasSelected())
                SkinSelectScreen.Inst.render(sb);
            if (CharacterSelectScreenPatch.isGkmasOtherSelected())
                OtherSkinSelectScreen.Inst.render(sb);
        }
    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "update")
    public static class PostPatchCharacterSelectScreen_update {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen __instance)
        {
            if (CharacterSelectScreenPatch.isGkmasSelected() && SkinSelectScreen.isClick)
            {
                SkinSelectScreen.isClick = false;
                __instance.bgCharImg = getSkinTexture();
            }
            if (CharacterSelectScreenPatch.isGkmasOtherSelected() && OtherSkinSelectScreen.isClick)
            {
                OtherSkinSelectScreen.isClick = false;
                __instance.bgCharImg = getOtherSkinTexture();
            }
        }
    }

    public static Texture getSkinTexture() {
        String name = IdolData.idolNames[SkinSelectScreen.Inst.idolIndex];
        String skinName = IdolData.getIdol(name).getSkin(SkinSelectScreen.Inst.skinIndex);
        int updateIndex = SkinSelectScreen.Inst.updateIndex;
        String IMG_PATH = String.format("gkmasModResource/img/charSelect/%s_%s_%d.jpg", name, skinName,updateIndex);

        if(Gdx.files.internal(IMG_PATH).exists())
            return new Texture(IMG_PATH);
        // 生成一个1或2的随机数
        int index = new Random().nextInt(2);
        return new Texture(String.format("gkmasModResource/img/charSelect/background_%d.png",index));
    }

    public static Texture getOtherSkinTexture() {
        String name = IdolData.otherIdolNames[OtherSkinSelectScreen.Inst.idolIndex];
        String skinName = IdolData.getOtherIdol(name).getSkin(OtherSkinSelectScreen.Inst.skinIndex);
        int updateIndex = OtherSkinSelectScreen.Inst.updateIndex;
        String IMG_PATH = String.format("gkmasModResource/img/charSelect/%s_%s_%d.jpg", name, skinName,updateIndex);

        if(Gdx.files.internal(IMG_PATH).exists())
            return new Texture(IMG_PATH);
        return new Texture(String.format("gkmasModResource/img/charSelect/background_%d.png",2));
    }
}

