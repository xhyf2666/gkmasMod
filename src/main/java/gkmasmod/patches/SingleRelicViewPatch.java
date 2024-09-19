//package gkmasmod.patches;
//
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
//import com.megacrit.cardcrawl.helpers.ImageMaster;
//import com.megacrit.cardcrawl.relics.AbstractRelic;
//import com.megacrit.cardcrawl.screens.SingleRelicViewPopup;
//import gkmasmod.screen.SingleRelicViewScreen;
//
//public class SingleRelicViewPatch {
//
//
//    @SpirePatch(clz = SingleRelicViewPopup.class, method = "update")
//    public static class UpdateButtonPatch {
//        public static void Prefix(SingleRelicViewPopup _inst, AbstractRelic ___relic) {
//            SingleRelicViewScreen.Inst.usedImg = ImageMaster.loadImage(String.format("gkmasModResource/img/meme/%s.png", ___relic.relicId));
//            SingleRelicViewScreen.Inst.update();
//        }
//    }
//
//    @SpirePatch(clz = SingleRelicViewPopup.class, method = "render")
//    public static class RenderButtonPatch {
//        public static void Postfix(SingleRelicViewPopup _inst, SpriteBatch sb) {
//            SingleRelicViewScreen.Inst.render(sb);
//        }
//    }
//}
//
