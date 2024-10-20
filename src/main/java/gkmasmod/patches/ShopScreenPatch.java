package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import gkmasmod.relics.MysteriousObject;
import gkmasmod.relics.OverpoweredBall;

public class ShopScreenPatch {
    @SpirePatch(clz = ShopScreen.class,method = "getBuyMsg")
    public static class PrefixShopScreen_getBuyMsg {
        @SpirePrefixPatch
        public static void Prefix() {
            if(AbstractDungeon.player.hasRelic(OverpoweredBall.ID)){
                ((OverpoweredBall)AbstractDungeon.player.getRelic(OverpoweredBall.ID)).onBuySomething();
            }
        }
    }


}
