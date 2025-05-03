package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.stances.AbstractStance;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.event.Live_jsna;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.exordium.MonsterAsari;
import gkmasmod.monster.exordium.MonsterNadeshiko;
import gkmasmod.monster.exordium.MonsterShion;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.stances.FullPowerStance;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.IdolData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class TheCityPatch
{

    @SpirePatch(clz = TheCity.class,method = "initializeEventList")
    public static class TheCityPostPatch_initializeEventList {
        public static void Postfix(TheCity __instance) {
            if(AbstractDungeon.player instanceof IdolCharacter||AbstractDungeon.player instanceof MisuzuCharacter){
                for(int i=0;i<AbstractDungeon.eventList.size();i++){
                    if(AbstractDungeon.eventList.get(i).equals("Nest")){
                        AbstractDungeon.eventList.set(i, Live_jsna.ID);
                    }
                }
            }
        }
    }

    @SpirePatch(clz = TheCity.class,method = "initializeBoss")
    public static class TheCity_Prefix_initializeBoss {
        @SpirePrefixPatch
        public static SpireReturn<Void> addBoss() {
            if(!(AbstractDungeon.player instanceof IdolCharacter||AbstractDungeon.player instanceof MisuzuCharacter)){
                return SpireReturn.Continue();
            }
            TheCity.bossList.clear();
            if (Settings.isDailyRun) {
                TheCity.bossList.add("Automaton");
                TheCity.bossList.add("Collector");
                TheCity.bossList.add("Champ");
                Collections.shuffle(TheCity.bossList, new Random(AbstractDungeon.monsterRng.randomLong()));
                AbstractDungeon.bossList.remove(AbstractDungeon.bossList.size() - 1);
            } else if (GkmasMod.onlyModBoss) {
                TheCity.bossList.add(MonsterShion.ID);
                TheCity.bossList.add(MonsterShion.ID);
                TheCity.bossList.add(MonsterAsari.ID);
                Collections.shuffle(TheCity.bossList, new Random(AbstractDungeon.monsterRng.randomLong()));
            } else {
                TheCity.bossList.add(MonsterShion.ID);
                TheCity.bossList.add(MonsterShion.ID);
                TheCity.bossList.add(MonsterAsari.ID);
                TheCity.bossList.add("Automaton");
                TheCity.bossList.add("Collector");
                TheCity.bossList.add("Champ");
                Collections.shuffle(TheCity.bossList, new Random(AbstractDungeon.monsterRng.randomLong()));
//                AbstractDungeon.bossList.remove(AbstractDungeon.bossList.size() - 1);
            }
            return SpireReturn.Return();
        }
    }

    @SpirePatch(clz = TheCity.class,method = "generateElites")
    public static class TheCity_insert_generateElites {
        @SpireInsertPatch(rloc = 4,localvars = {"monsters"})
        public static SpireReturn<Void> addElite(TheCity __instance, int count, ArrayList<MonsterInfo> monsters) {
            if(!(AbstractDungeon.player instanceof IdolCharacter||AbstractDungeon.player instanceof MisuzuCharacter)){
                return SpireReturn.Continue();
            }
            if(AbstractDungeon.player instanceof IdolCharacter){
                if(SkinSelectScreen.Inst.idolName.equals(IdolData.shro)||SkinSelectScreen.Inst.idolName.equals(IdolData.kcna)) {
                    return SpireReturn.Continue();
                }
            }
            monsters.add(new MonsterInfo("hiroAndChina", 1.0F));
            return SpireReturn.Continue();
        }
    }

}

