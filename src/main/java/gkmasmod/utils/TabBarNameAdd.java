package gkmasmod.utils;

import basemod.BaseMod;
import basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import javassist.CtBehavior;
import org.lwjgl.Sys;

public class TabBarNameAdd {
    public static HashMap<String, String> tabBarNameMap = new HashMap<>();

    @SpirePatch2(clz = ColorTabBarFix.Render.class, method = "Insert")
    public static class RenderFix {
        @SpireInsertPatch(locator = Locator.class, localvars = {"tabName", "playerClass", "i"})
        public static void Insert(@ByRef String[] tabName, AbstractPlayer.PlayerClass playerClass, int i) {
            String tmpName = ((AbstractCard.CardColor)BaseMod.getCardColors().get(i)).toString();
            if (!TabBarNameAdd.tabBarNameMap.containsKey(tmpName))
                TabBarNameAdd.tabBarNameMap.put(tmpName, (CardCrawlGame.languagePack.getUIString(tmpName)).TEXT[0]);
            tmpName = TabBarNameAdd.tabBarNameMap.get(tmpName);
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
}
