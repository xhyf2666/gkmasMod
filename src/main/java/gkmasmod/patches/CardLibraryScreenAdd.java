package gkmasmod.patches;

import basemod.BaseMod;
import basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.utils.PlayerHelper;
import javassist.CtBehavior;

public class CardLibraryScreenAdd {
    public static HashMap<String, String> tabBarNameMap = new HashMap<>();

    @SpirePatch2(clz = ColorTabBarFix.Render.class, method = "Insert")
    public static class RenderFix {
        @SpireInsertPatch(locator = Locator.class, localvars = {"tabName", "playerClass", "i"})
        public static void Insert(@ByRef String[] tabName, AbstractPlayer.PlayerClass playerClass, int i) {
            String tmpName = ((AbstractCard.CardColor)BaseMod.getCardColors().get(i)).toString();
            if(!tmpName.startsWith("gkmasMod"))
                return;
            if (!CardLibraryScreenAdd.tabBarNameMap.containsKey(tmpName))
                    CardLibraryScreenAdd.tabBarNameMap.put(tmpName, (CardCrawlGame.languagePack.getUIString(tmpName)).TEXT[0]);
            tmpName = CardLibraryScreenAdd.tabBarNameMap.get(tmpName);
            if (!Objects.equals(tmpName, "[MISSING_TITLE]"))
                tabName[0] = tmpName;
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderFontCentered");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList(), (Matcher)methodCallMatcher);
            }
        }
    }

    @SpirePatch(clz = CardLibraryScreen.class, method = "open")
    public static class CardLibraryOpenPatch {
        @SpirePostfixPatch
        public static void onOpen(CardLibraryScreen __instance) {
            GkmasMod.cardRate = PlayerHelper.getCardRate();
            GkmasMod.listeners.forEach(listener -> listener.onCardImgUpdate());
        }
    }
}
