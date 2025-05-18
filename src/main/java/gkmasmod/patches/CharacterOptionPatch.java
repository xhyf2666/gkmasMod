package gkmasmod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.characters.OtherIdolCharacter;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.screen.OtherSkinSelectScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.ImageHelper;

public class CharacterOptionPatch {

//    /**
//     * 切换人物时，更新角色信息
//     */
//    @SpirePatch(clz = CharacterOption.class, method = "update")
//    public static class PostPatchCharacterOption_update {
//        @SpirePostfixPatch
//        public static void Postfix(CharacterOption __instance,@ByRef String[] ___hp,@ByRef int[] ___gold,@ByRef CharSelectInfo[] ___charInfo,@ByRef float[] ___infoX, float ___DEST_INFO_X) {
//            if(__instance.c instanceof IdolCharacter){
////                ___charInfo[0] = __instance.c.getLoadout();
////                __instance.name = SkinSelectScreen.Inst.curName;
////                ___gold[0] = ((IdolCharacter) __instance.c).getGold();
////                ___hp[0] = String.valueOf(((IdolCharacter) __instance.c).getHP());
//            }
//            else if(__instance.c instanceof OtherIdolCharacter){
////                ___charInfo[0] = __instance.c.getLoadout();
////                __instance.name = OtherSkinSelectScreen.Inst.curName;
////                ___gold[0] = ((OtherIdolCharacter) __instance.c).getGold();
////                ___hp[0] = String.valueOf(((OtherIdolCharacter) __instance.c).getHP());
//            }
//        }
//    }

    /**
     * 隐藏部分角色的开始游戏按钮
     */
    @SpirePatch(clz = CharacterOption.class, method = "updateHitbox")
    public static class InsertPatchCharacterOption_updateHitbox {
        @SpireInsertPatch(rloc = 46)
        public static void Insert(CharacterOption __instance) {
            if(__instance.c instanceof OtherIdolCharacter){
                try{
                    ReflectionHacks.setPrivate(__instance,CharacterOption.class,"charInfo",__instance.c.getLoadout());
                    ReflectionHacks.setPrivate(__instance,CharacterOption.class,"name",OtherSkinSelectScreen.Inst.curName);
                    ReflectionHacks.setPrivate(__instance,CharacterOption.class,"gold",((OtherIdolCharacter) __instance.c).getGold());
                    ReflectionHacks.setPrivate(__instance,CharacterOption.class,"hp",String.valueOf(((OtherIdolCharacter) __instance.c).getHP()));
                    ReflectionHacks.setPrivate(__instance,CharacterOption.class,"flavorText",((OtherIdolCharacter) __instance.c).getStory());
                }
                catch (Exception e){
                }
                if(OtherSkinSelectScreen.Inst.idolName==IdolData.prod||OtherSkinSelectScreen.Inst.idolName==IdolData.andk||OtherSkinSelectScreen.Inst.idolName==IdolData.sson||OtherSkinSelectScreen.Inst.idolName==IdolData.sgka||OtherSkinSelectScreen.Inst.idolName==IdolData.arnm)
                    return;
                CardCrawlGame.mainMenuScreen.charSelectScreen.confirmButton.hide();
            }
            if(__instance.c instanceof MisuzuCharacter){
                __instance.name = " ";
            }
            if(__instance.c instanceof IdolCharacter){
                try{
                    ReflectionHacks.setPrivate(__instance,CharacterOption.class,"charInfo",__instance.c.getLoadout());
                    ReflectionHacks.setPrivate(__instance,CharacterOption.class,"name",SkinSelectScreen.Inst.curName);
                    ReflectionHacks.setPrivate(__instance,CharacterOption.class,"gold",((IdolCharacter) __instance.c).getGold());
                    ReflectionHacks.setPrivate(__instance,CharacterOption.class,"hp",String.valueOf(((IdolCharacter) __instance.c).getHP()));
                    ReflectionHacks.setPrivate(__instance,CharacterOption.class,"flavorText",((IdolCharacter) __instance.c).getStory());
                }
                catch (Exception e){
                }
            }
        }
    }

    @SpirePatch(clz = CharacterOption.class, method = "render")
    public static class PostPatchCharacterOption_render {
        @SpirePostfixPatch
        public static void Postfix(CharacterOption __instance,SpriteBatch sb) {
            if(isGkmasMisuzuSelected()){
                sb.draw(ImageHelper.misuzuNameImg, Settings.WIDTH * 0.15F- 180.0F * Settings.scale,Settings.HEIGHT * 0.4F + 180.0F * Settings.scale,256.0F, 128.0F, 512.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
            }
        }
    }

    public static boolean isGkmasMisuzuSelected() {
        return (CardCrawlGame.chosenCharacter == PlayerColorEnum.gkmasModMisuzu_character && (Boolean) ReflectionHacks.getPrivate(CardCrawlGame.mainMenuScreen.charSelectScreen, CharacterSelectScreen.class, "anySelected"));
    }



    /**
     * 选人界面显示随机默认背景
     */
    @SpirePatch(clz = CharacterOption.class, method = "updateHitbox")
    public static class InsertPatchCharacterOption_updateHitbox2 {
        @SpireInsertPatch(rloc = 193-139)
        public static void Insert(CharacterOption __instance) {
            if(__instance.c instanceof IdolCharacter){
                SkinSelectScreen.Inst.defaultBackgroundIndex+=1;
                SkinSelectScreen.Inst.defaultBackgroundIndex%=2;
                CardCrawlGame.mainMenuScreen.charSelectScreen.bgCharImg = new Texture(String.format("gkmasModResource/img/charSelect/background_%d.png",SkinSelectScreen.Inst.defaultBackgroundIndex));
            }
        }
    }

}

