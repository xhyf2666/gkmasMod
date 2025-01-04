package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AbstractMonsterPatch {

    @SpirePatch(clz = AbstractMonster.class,method = SpirePatch.CLASS)
    public static class friendlyField {
        public static SpireField<Boolean> friendly = new SpireField<>(() -> false);
    }
}
