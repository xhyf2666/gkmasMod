package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.exordium.*;
import gkmasmod.characters.OtherIdolCharacter;
import gkmasmod.monster.exordium.*;
import gkmasmod.relics.PocketBook;

import java.util.ArrayList;

public class MonsterHelperPatch {
    @SpirePatch(clz = MonsterHelper.class,method = "getEncounter")
    public static class PrePatchMonsterHelper_getEncounter {
        @SpirePrefixPatch
        public static SpireReturn<MonsterGroup> prefix(String key) {
            if(AbstractDungeon.player.hasRelic(PocketBook.ID)||AbstractDungeon.player instanceof OtherIdolCharacter){
                if (key.equals("Large Slime")){
                    if (AbstractDungeon.miscRng.randomBoolean())
                        return SpireReturn.Return(new MonsterGroup(new AcidSlimeTemari_L()));
                    return SpireReturn.Return(new MonsterGroup(new SpikeSlimeTemari_L()));
                }
                else if(key.equals("Looter")){
                    return SpireReturn.Return(new MonsterGroup(new LooterBandai(0.0F, 0.0F)));
                }
                else if(key.equals("2 Thieves")){
                    return SpireReturn.Return(new MonsterGroup(new AbstractMonster[] {new LooterBandai(-200.0F, 15.0F), new LooterNamco(80.0F, 0.0F) }));
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = MonsterHelper.class,method = "spawnSmallSlimes")
    public static class PrePatchMonsterHelper_spawnSmallSlimes {
        @SpirePrefixPatch
        public static SpireReturn<MonsterGroup> prefix() {
            if(AbstractDungeon.player.hasRelic(PocketBook.ID)||AbstractDungeon.player instanceof OtherIdolCharacter){
                AbstractMonster[] retVal = new AbstractMonster[2];
                if (AbstractDungeon.miscRng.randomBoolean()) {
                    retVal[0] = new SpikeSlimeTemari_S(-230.0F, 32.0F, 0);
                    retVal[1] = new AcidSlimeTemari_M(35.0F, 8.0F);
                } else {
                    retVal[0] = new AcidSlimeTemari_S(-230.0F, 32.0F, 0);
                    retVal[1] = new SpikeSlimeTemari_M(35.0F, 8.0F);
                }
                return SpireReturn.Return(new MonsterGroup(retVal));
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = MonsterHelper.class,method = "bottomGetStrongHumanoid")
    public static class PrePatchMonsterHelper_bottomGetStrongHumanoid {
        @SpirePrefixPatch
        public static SpireReturn<AbstractMonster> prefix(float x,float y) {
            if(AbstractDungeon.player.hasRelic(PocketBook.ID)||AbstractDungeon.player instanceof OtherIdolCharacter){
                ArrayList<AbstractMonster> monsters = new ArrayList<>();
                monsters.add(new Cultist(x, y));
                AbstractMonster slaver;
                if (AbstractDungeon.miscRng.randomBoolean())
                    slaver = new SlaverRed(x, y);
                else
                    slaver = new SlaverBlue(x, y);
                monsters.add(slaver);
                monsters.add(new LooterBandai(x, y));
                AbstractMonster output = monsters.get(AbstractDungeon.miscRng.random(0, monsters.size() - 1));
                return SpireReturn.Return(output);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = MonsterHelper.class,method = "spawnManySmallSlimes")
    public static class PrePatchMonsterHelper_spawnManySmallSlimes {
        @SpirePrefixPatch
        public static SpireReturn<MonsterGroup> prefix() {
            if(AbstractDungeon.player.hasRelic(PocketBook.ID)||AbstractDungeon.player instanceof OtherIdolCharacter){
                ArrayList<String> slimePool = new ArrayList<>();
                slimePool.add(SpikeSlimeTemari_S.ID);
                slimePool.add(SpikeSlimeTemari_S.ID);
                slimePool.add(SpikeSlimeTemari_S.ID);
                slimePool.add(AcidSlimeTemari_S.ID);
                slimePool.add(AcidSlimeTemari_S.ID);
                AbstractMonster[] retVal = new AbstractMonster[5];
                int index = AbstractDungeon.miscRng.random(slimePool.size() - 1);
                String key = slimePool.get(index);
                slimePool.remove(index);
                if (key.equals("AcidSlimeTemari_S")) {
                    retVal[0] = new SpikeSlimeTemari_S(-480.0F, 30.0F, 0);
                } else {
                    retVal[0] = new AcidSlimeTemari_S(-480.0F, 30.0F, 0);
                }
                index = AbstractDungeon.miscRng.random(slimePool.size() - 1);
                key = slimePool.get(index);
                slimePool.remove(index);
                if (key.equals("AcidSlimeTemari_S")) {
                    retVal[1] = new SpikeSlimeTemari_S(-320.0F, 2.0F, 0);
                } else {
                    retVal[1] = new AcidSlimeTemari_S(-320.0F, 2.0F, 0);
                }
                index = AbstractDungeon.miscRng.random(slimePool.size() - 1);
                key = slimePool.get(index);
                slimePool.remove(index);
                if (key.equals("AcidSlimeTemari_S")) {
                    retVal[2] = new SpikeSlimeTemari_S(-160.0F, 32.0F, 0);
                } else {
                    retVal[2] = new AcidSlimeTemari_S(-160.0F, 32.0F, 0);
                }
                index = AbstractDungeon.miscRng.random(slimePool.size() - 1);
                key = slimePool.get(index);
                slimePool.remove(index);
                if (key.equals("AcidSlimeTemari_S")) {
                    retVal[3] = new SpikeSlimeTemari_S(10.0F, -12.0F, 0);
                } else {
                    retVal[3] = new AcidSlimeTemari_S(10.0F, -12.0F, 0);
                }
                index = AbstractDungeon.miscRng.random(slimePool.size() - 1);
                key = slimePool.get(index);
                slimePool.remove(index);
                if (key.equals("AcidSlimeTemari_S")) {
                    retVal[4] = new SpikeSlimeTemari_S(200.0F, 9.0F, 0);
                } else {
                    retVal[4] = new AcidSlimeTemari_S(200.0F, 9.0F, 0);
                }
                return SpireReturn.Return(new MonsterGroup(retVal));
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = MonsterHelper.class,method = "bottomGetWeakWildlife")
    public static class PrePatchMonsterHelper_bottomGetWeakWildlife {
        @SpirePrefixPatch
        public static SpireReturn<AbstractMonster> prefix(float x, float y) {
            if(AbstractDungeon.player.hasRelic(PocketBook.ID)||AbstractDungeon.player instanceof OtherIdolCharacter){
                ArrayList<AbstractMonster> monsters = new ArrayList<>();
                AbstractMonster louse;
                if (AbstractDungeon.miscRng.randomBoolean())
                    louse = new LouseNormal(x, y);
                else
                    louse = new LouseDefensive(x, y);
                monsters.add(louse);
                monsters.add(new SpikeSlimeTemari_M(x, y));
                monsters.add(new AcidSlimeTemari_M(x, y));
                return SpireReturn.Return(monsters.get(AbstractDungeon.miscRng.random(0, monsters.size() - 1)));
            }
            return SpireReturn.Continue();
        }
    }
}
