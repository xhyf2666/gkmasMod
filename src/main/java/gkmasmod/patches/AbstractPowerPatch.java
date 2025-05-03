package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AbstractPowerPatch {

    @SpirePatch(clz = AbstractPower.class,method = SpirePatch.CLASS)
    public static class IgnoreIncreaseModifyField {
        public static SpireField<Boolean> flag = new SpireField<>(() -> false);
    }
}
