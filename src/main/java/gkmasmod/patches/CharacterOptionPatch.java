package gkmasmod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;

public class CharacterOptionPatch {

    @SpirePatch(clz = CharacterOption.class, method = "update")
    public static class CharacterOptionPatch_update
    {
        @SpirePostfixPatch
        public static void Postfix(CharacterOption __instance,@ByRef String[] ___hp,@ByRef int[] ___gold,@ByRef CharSelectInfo[] ___charInfo,@ByRef float[] ___infoX, float ___DEST_INFO_X)
        {
            if(__instance.c instanceof IdolCharacter){
                ___charInfo[0] = __instance.c.getLoadout();
                __instance.name = SkinSelectScreen.Inst.curName;
                ___gold[0] = ((IdolCharacter) __instance.c).getGold();
                ___hp[0] = String.valueOf(((IdolCharacter) __instance.c).getHP());
            }
        }
    }

    @SpirePatch(clz = CharacterOption.class, method = "updateHitbox")
    public static class CharacterOptionPatch_updateHitbox
    {
        @SpireInsertPatch(rloc = 46)
        public static void Insert(CharacterOption __instance)
        {
            if(__instance.c instanceof MisuzuCharacter){
                CardCrawlGame.mainMenuScreen.charSelectScreen.confirmButton.hide();
            }
        }
    }

}

