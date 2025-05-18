package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.stances.AbstractStance;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.characters.OtherIdolCharacter;
import gkmasmod.event.Live_jsna;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.exordium.MonsterAsari;
import gkmasmod.monster.exordium.MonsterGekka;
import gkmasmod.monster.exordium.MonsterNadeshiko;
import gkmasmod.monster.exordium.MonsterShion;
import gkmasmod.screen.OtherSkinSelectScreen;
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

    /**
     * 添加2层事件
     */
    @SpirePatch(clz = TheCity.class,method = "initializeEventList")
    public static class PostPatchTheCity_initializeEventList {
        public static void Postfix(TheCity __instance) {
            if(AbstractDungeon.player instanceof IdolCharacter||AbstractDungeon.player instanceof MisuzuCharacter||AbstractDungeon.player instanceof OtherIdolCharacter){
                for(int i=0;i<AbstractDungeon.eventList.size();i++){
                    if(AbstractDungeon.eventList.get(i).equals("Nest")){
                        AbstractDungeon.eventList.set(i, Live_jsna.ID);
                    }
                }
            }
        }
    }

    /**
     * 设置2层Boss
     */
    @SpirePatch(clz = TheCity.class,method = "initializeBoss")
    public static class PrePatchTheCity_initializeBoss {
        @SpirePrefixPatch
        public static SpireReturn<Void> prefix() {
            if(!(AbstractDungeon.player instanceof IdolCharacter||AbstractDungeon.player instanceof MisuzuCharacter||AbstractDungeon.player instanceof OtherIdolCharacter)){
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
                if(AbstractDungeon.player instanceof OtherIdolCharacter && OtherSkinSelectScreen.Inst.idolName.equals(IdolData.sson)){

                }
                else{
                    TheCity.bossList.add(MonsterShion.ID);
                    TheCity.bossList.add(MonsterShion.ID);
                }
                TheCity.bossList.add(MonsterAsari.ID);
                Collections.shuffle(TheCity.bossList, new Random(AbstractDungeon.monsterRng.randomLong()));
            } else {
                if(AbstractDungeon.player instanceof OtherIdolCharacter && OtherSkinSelectScreen.Inst.idolName.equals(IdolData.sson)){

                }
                else{
                    TheCity.bossList.add(MonsterShion.ID);
                    TheCity.bossList.add(MonsterShion.ID);
                }
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

    /**
     * 设置2层精英
     */
    @SpirePatch(clz = TheCity.class,method = "generateElites")
    public static class InsertPatchTheCity_generateElites {
        @SpireInsertPatch(rloc = 4,localvars = {"monsters"})
        public static SpireReturn<Void> insert(TheCity __instance, int count, ArrayList<MonsterInfo> monsters) {
            if(!(AbstractDungeon.player instanceof IdolCharacter||AbstractDungeon.player instanceof MisuzuCharacter||AbstractDungeon.player instanceof OtherIdolCharacter)){
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

