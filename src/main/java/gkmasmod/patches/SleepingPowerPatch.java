package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.powers.SleepingPower;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class SleepingPowerPatch {
    @SpirePatch(clz = GameActionManager.class, method = "getNextAction")
    public static class InstrumentPatch_getNextAction {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals("com.megacrit.cardcrawl.monsters.AbstractMonster") && m
                            .getMethodName().equals("takeTurn"))
                        m.replace("if (!m.hasPower(gkmasmod.powers.SleepingPower.POWER_ID)) {$_ = $proceed($$);}");
                }
            };
        }
    }

    @SpirePatch(clz = AbstractMonster.class, method = "rollMove")
    public static class PrePatchAbstractMonster_rollMove {
        public static SpireReturn<Void> Prefix(AbstractMonster __instance) {
            if (__instance.hasPower(SleepingPower.POWER_ID))
                return SpireReturn.Return(null);
            return SpireReturn.Continue();
        }
    }
}
