package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.Orrery;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.LittleGundam;
import gkmasmod.relics.PocketBook;

import java.util.ArrayList;


public class MonsterGroupPatch
{
    @SpirePatch(clz = MonsterGroup.class,method = "getRandomMonster",paramtypez = {AbstractMonster.class, boolean.class, Random.class})
    public static class MonsterGroupPatchInsertPatch_getRandomMonster{
        @SpireInsertPatch(rloc = 14,localvars = {"tmp"})
        public static void Insert(MonsterGroup __instance,AbstractMonster exception, boolean aliveOnly, Random rng, ArrayList<AbstractMonster> tmp) {
            //删除arraylist中的满足特定条件的元素
            tmp.removeIf(monster -> monster instanceof LittleGundam);
        }
    }

//    @SpirePatch(clz = MonsterGroup.class,method = "getRandomMonster",paramtypez = {AbstractMonster.class, boolean.class, Random.class})
//    public static class MonsterGroupPatchInsertPatch_getRandomMonster2{
//        @SpireInsertPatch(rloc = 38,localvars = {"arrayList"})
//        public static void Insert(MonsterGroup __instance,AbstractMonster exception, boolean aliveOnly, Random rng, ArrayList<AbstractMonster> arrayList) {
//            //删除arraylist中的满足特定条件的元素
//            arrayList.removeIf(monster -> monster instanceof LittleGundam&&((LittleGundam)monster).owner.isPlayer);
//        }
//    }
}

