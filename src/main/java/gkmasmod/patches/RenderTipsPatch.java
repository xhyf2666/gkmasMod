package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RenderTipsPatch{
    public static boolean notShowPlayerPowerTip = false;

    @SpirePatch2(clz = AbstractPlayer.class, method = "renderPowerTips")
    public static class PrePatchAbstractPlayer_renderPowerTips {
        public static SpireReturn<Void> Prefix() {
            if (RenderTipsPatch.notShowPlayerPowerTip)
                return SpireReturn.Return();
            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz = AbstractMonster.class, method = "renderTip")
    public static class PrePatchAbstractMonster_renderTip {
        public static SpireReturn<Void> Prefix() {
            if (RenderTipsPatch.notShowPlayerPowerTip)
                return SpireReturn.Return();
            return SpireReturn.Continue();
        }
    }
}

