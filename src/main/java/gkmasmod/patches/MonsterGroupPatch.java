package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.random.Random;
import gkmasmod.monster.friend.LittleGundam;
import gkmasmod.powers.FriendUmePower1;
import gkmasmod.powers.FriendUmePower2;

import java.util.ArrayList;


public class MonsterGroupPatch
{
    @SpirePatch(clz = MonsterGroup.class,method = "getRandomMonster",paramtypez = {AbstractMonster.class, boolean.class, Random.class})
    public static class MonsterGroupPatchInsertPatch_getRandomMonster{
        @SpireInsertPatch(rloc = 14,localvars = {"tmp"})
        public static void Insert(MonsterGroup __instance,AbstractMonster exception, boolean aliveOnly, Random rng, ArrayList<AbstractMonster> tmp) {
            //删除arraylist中的满足特定条件的元素
            tmp.removeIf(monster -> AbstractMonsterPatch.friendlyField.friendly.get(monster));
        }
    }

    @SpirePatch(clz = MonsterGroup.class,method = "areMonstersBasicallyDead")
    public static class PrefixPatchMonsterGroup_areMonstersBasicallyDead {
        public static SpireReturn<Boolean> Prefix(MonsterGroup __instance) {
            for (AbstractMonster m : __instance.monsters) {
                if (!m.isDying && !m.isEscaping&&!AbstractMonsterPatch.friendlyField.friendly.get(m)&&!m.hasPower(MinionPower.POWER_ID))
                    return SpireReturn.Return(false);
            }
            for (AbstractMonster m : __instance.monsters) {
                if (AbstractMonsterPatch.friendlyField.friendly.get(m)&&m.hasPower(FriendUmePower1.POWER_ID)){
                    m.getPower(FriendUmePower1.POWER_ID).onSpecificTrigger();
                    break;
                }
            }
            return SpireReturn.Return(true);
        }
    }

    @SpirePatch(clz = MonsterGroup.class,method = "areMonstersDead")
    public static class PrefixPatchMonsterGroup_areMonstersDead {
        public static SpireReturn<Boolean> Prefix(MonsterGroup __instance) {
            for (AbstractMonster m : __instance.monsters) {
                if (!m.isDying && !m.escaped&&!AbstractMonsterPatch.friendlyField.friendly.get(m)&&!m.hasPower(MinionPower.POWER_ID))
                    return SpireReturn.Return(false);
            }
            return SpireReturn.Return(true);
        }
    }
}

