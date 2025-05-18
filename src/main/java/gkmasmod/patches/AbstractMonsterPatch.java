package gkmasmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.downfall.bosses.AbstractIdolBoss;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.powers.RebirthPower;
import gkmasmod.relics.PocketBook;

import java.util.ArrayList;

public class AbstractMonsterPatch {

    @SpirePatch(clz = AbstractMonster.class,method = SpirePatch.CLASS)
    public static class friendlyField {
        public static SpireField<Boolean> friendly = new SpireField<>(() -> false);
    }

    /**
     * 敌人死亡时，触发 复苏
     */
    @SpirePatch(clz = AbstractMonster.class,method = "damage")
    public static class InsertPatchAbstractMonster_damage {
        @SpireInsertPatch(rloc =832-739)
        public static SpireReturn<Void> Insert(AbstractMonster __instance, DamageInfo info) {
            if(__instance instanceof AbstractCharBoss)
                return SpireReturn.Continue();
            if(__instance.currentHealth<=0){
                for (final AbstractPower p : __instance.powers) {
                    if (p instanceof RebirthPower) {
                        __instance.currentHealth = 0;
                        p.onSpecificTrigger();
                        return SpireReturn.Return(null);
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }

    /**
     * 在 偶像之路 中显示敌人的姿态提示
     */
    @SpirePatch(clz = AbstractMonster.class,method = "renderTip")
    public static class InsertPatchAbstractMonster_renderPowerTips {
        @SpireInsertPatch(rloc =2)
        public static SpireReturn Insert(AbstractMonster __instance, SpriteBatch sb, ArrayList<PowerTip> ___tips) {
            if(__instance instanceof AbstractCharBoss&&AbstractCharBoss.boss!=null){
                if (!AbstractCharBoss.boss.stance.ID.equals("Neutral")){
                    ___tips.add(new PowerTip(AbstractCharBoss.boss.stance.name, AbstractCharBoss.boss.stance.description));
                }
            }
            return SpireReturn.Continue();
        }
    }
}
