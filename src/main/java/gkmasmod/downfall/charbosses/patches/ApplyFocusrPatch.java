package gkmasmod.downfall.charbosses.patches;


import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;


public class ApplyFocusrPatch {
    @SpirePatch(
            clz = RemoveSpecificPowerAction.class,
            method = "update"
    )
    public static class ApplyFocusrPatchA {
        @SpireInsertPatch(rloc = 24)
        public static SpireReturn<Void> Insert(RemoveSpecificPowerAction instance) {
            if (AbstractCharBoss.boss != null) {
                for (AbstractOrb o : AbstractCharBoss.boss.orbs) {
                    o.updateDescription();
                }
            }

            return SpireReturn.Continue();

        }
    }


    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "onModifyPower"
    )
    public static class ApplyFocusrPatchB {
        @SpirePrefixPatch
        public static SpireReturn Prefix() {
            if (AbstractCharBoss.boss != null) {
                for (AbstractOrb o : AbstractCharBoss.boss.orbs) {
                    o.updateDescription();
                }
            }

            return SpireReturn.Continue();

        }
    }


}



