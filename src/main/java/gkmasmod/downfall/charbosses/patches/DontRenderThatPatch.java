package gkmasmod.downfall.charbosses.patches;


import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.vfx.combat.PowerExpireTextEffect;


public class DontRenderThatPatch {
    @SpirePatch(
            clz = PowerExpireTextEffect.class,
            method = "render"
    )
    public static class Noooooo {
        @SpirePrefixPatch
        public static SpireReturn Prefix() {
            if (AbstractCharBoss.boss != null) {

            }
            return SpireReturn.Continue();
        }
    }
}



