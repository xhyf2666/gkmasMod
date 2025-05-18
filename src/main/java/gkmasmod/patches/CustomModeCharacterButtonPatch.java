package gkmasmod.patches;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.custom.CustomModeCharacterButton;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.OtherIdolCharacter;
import gkmasmod.screen.OtherSkinSelectScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;

public class CustomModeCharacterButtonPatch {

    /**
     * 自定义模式界面，隐藏部分角色的开始游戏按钮
     */
    @SpirePatch(clz = CustomModeCharacterButton.class, method = "updateHitbox")
    public static class InsertPatchCustomModeCharacterButton_updateHitbox {
        @SpireInsertPatch(rloc = 21)
        public static void Insert(CustomModeCharacterButton __instance) {
            if(__instance.c instanceof OtherIdolCharacter){
                if(OtherSkinSelectScreen.Inst.idolName== IdolData.prod||OtherSkinSelectScreen.Inst.idolName==IdolData.andk||OtherSkinSelectScreen.Inst.idolName==IdolData.sson||OtherSkinSelectScreen.Inst.idolName==IdolData.sgka||OtherSkinSelectScreen.Inst.idolName==IdolData.arnm)
                    return;
                CardCrawlGame.mainMenuScreen.customModeScreen.confirmButton.hide();
            }
//            else {
//                CardCrawlGame.mainMenuScreen.charSelectScreen.confirmButton.show();
//            }
        }
    }

}

