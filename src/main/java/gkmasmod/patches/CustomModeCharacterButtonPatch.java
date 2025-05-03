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
import gkmasmod.screen.SkinSelectScreen;

public class CustomModeCharacterButtonPatch {

    @SpirePatch(clz = CustomModeCharacterButton.class, method = "updateHitbox")
    public static class CustomModeCharacterButtonPatch_updateHitbox
    {
        @SpireInsertPatch(rloc = 21)
        public static void Insert(CustomModeCharacterButton __instance)
        {
            if(__instance.c instanceof OtherIdolCharacter){
//                if(SkinSelectScreen.Inst.idolName==IdolData.ttmr&&IdolData.getIdol(SkinSelectScreen.Inst.idolName).getSkinImg(SkinSelectScreen.Inst.skinIndex).equals("skin21")&&SkinSelectScreen.Inst.hideSameIdol&&SkinSelectScreen.Inst.updateIndex==1)
//                    return;
                CardCrawlGame.mainMenuScreen.customModeScreen.confirmButton.hide();
            }
//            else {
//                CardCrawlGame.mainMenuScreen.charSelectScreen.confirmButton.show();
//            }
        }
    }

}

