//package gkmasmod.patches;
//
//import com.evacipated.cardcrawl.modthespire.lib.*;
//import com.megacrit.cardcrawl.ui.panels.TopPanel;
//import javassist.CtBehavior;
//
//public class TopPanelPatch {
//    @SpirePatch(
//            clz = TopPanel.class,
//            method = "setPlayerName"
//    )
//    public static class PatchSetPlayerName {
//        @SpireInsertPatch(
//                locator = Locator.class
//        )
//        public static void Insert(TopPanel __instance) {
//            // 在 this.name = CardCrawlGame.playerName; 之后插入代码
//            System.out.println("Player name has been set: " + __instance.name);
//            // 你可以在这里插入任何其他逻辑
//        }
//
//        private static class Locator extends SpireInsertLocator {
//            @Override
//            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
//                // 这里定位到第一行后面的插入点，即 this.name = CardCrawlGame.playerName;
//                Matcher matcher = new Matcher.MethodCallMatcher(TopPanel.class, "setPlayerName");
//                return LineFinder.findInOrder(ctMethodToPatch, matcher);
//            }
//        }
//}
