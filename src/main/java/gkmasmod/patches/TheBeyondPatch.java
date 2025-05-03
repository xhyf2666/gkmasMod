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
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.event.Live_jsna;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.exordium.MonsterGekka;
import gkmasmod.monster.exordium.MonsterShion;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class TheBeyondPatch
{

    @SpirePatch(clz = TheBeyond.class,method = "initializeBoss")
    public static class TheBeyond_Prefix_initializeBoss {
        @SpirePrefixPatch
        public static SpireReturn<Void> addBoss() {
            if(!(AbstractDungeon.player instanceof IdolCharacter||AbstractDungeon.player instanceof MisuzuCharacter)){
                return SpireReturn.Continue();
            }
            TheBeyond.bossList.clear();
            if (Settings.isDailyRun) {
                TheBeyond.bossList.add("Awakened One");
                TheBeyond.bossList.add("Time Eater");
                TheBeyond.bossList.add("Donu and Deca");
                Collections.shuffle(TheCity.bossList, new Random(AbstractDungeon.monsterRng.randomLong()));
                AbstractDungeon.bossList.remove(AbstractDungeon.bossList.size() - 1);
            } else if (GkmasMod.onlyModBoss) {
                TheBeyond.bossList.add(MonsterGekka.ID);
                if(AbstractDungeon.player instanceof MisuzuCharacter){
                    TheBeyond.bossList.add("reiris");
                }
                else{
                    if(!SkinSelectScreen.Inst.idolName.equals(IdolData.ttmr)&&!SkinSelectScreen.Inst.idolName.equals(IdolData.fktn)&&!SkinSelectScreen.Inst.idolName.equals(IdolData.hski)) {
                        TheBeyond.bossList.add("reiris");
                    }
                    if(!SkinSelectScreen.Inst.idolName.equals(IdolData.jsna)&&!SkinSelectScreen.Inst.idolName.equals(IdolData.hume)&&!SkinSelectScreen.Inst.idolName.equals(IdolData.hmsz)){
                        TheBeyond.bossList.add("begrazia");
                    }
                }
                Collections.shuffle(TheCity.bossList, new Random(AbstractDungeon.monsterRng.randomLong()));
                Random random = new Random(AbstractDungeon.monsterRng.randomLong());
                ArrayList<String> bossList = new ArrayList<>();
                bossList.add("Awakened One");
                bossList.add("Time Eater");
                bossList.add("Donu and Deca");
                Collections.shuffle(bossList, random);
                TheBeyond.bossList.add(bossList.get(0));
                TheBeyond.bossList.add(bossList.get(1));
                TheBeyond.bossList.add(bossList.get(2));
                AbstractDungeon.bossList.remove(AbstractDungeon.bossList.size() - 1);
                AbstractDungeon.bossList.add(MonsterGekka.ID);
                AbstractDungeon.bossList.add(bossList.get(0));
                AbstractDungeon.bossList.add(bossList.get(1));
                AbstractDungeon.bossList.add(bossList.get(2));

//                System.out.println("TheBeyondPatch.TheBeyond_Prefix_initializeBoss type1");
            } else {
                TheBeyond.bossList.add(MonsterGekka.ID);
                if(AbstractDungeon.player instanceof MisuzuCharacter){
                    TheBeyond.bossList.add("reiris");
                }
                else{
                    if(!SkinSelectScreen.Inst.idolName.equals(IdolData.ttmr)&&!SkinSelectScreen.Inst.idolName.equals(IdolData.fktn)&&!SkinSelectScreen.Inst.idolName.equals(IdolData.hski)) {
                        TheBeyond.bossList.add("reiris");
                    }
                    if(!SkinSelectScreen.Inst.idolName.equals(IdolData.jsna)&&!SkinSelectScreen.Inst.idolName.equals(IdolData.hume)&&!SkinSelectScreen.Inst.idolName.equals(IdolData.hmsz)){
                        TheBeyond.bossList.add("begrazia");
                    }
                }
                TheBeyond.bossList.add("Awakened One");
                TheBeyond.bossList.add("Time Eater");
                TheBeyond.bossList.add("Donu and Deca");
                Collections.shuffle(TheCity.bossList, new Random(AbstractDungeon.monsterRng.randomLong()));
//                AbstractDungeon.bossList.remove(AbstractDungeon.bossList.size() - 1);
//                System.out.println("TheBeyondPatch.TheBeyond_Prefix_initializeBoss type2");
            }
//            System.out.println(TheBeyond.bossList);
//            System.out.println(AbstractDungeon.bossList);
            return SpireReturn.Return();
        }
    }

    @SpirePatch(clz = TheBeyond.class,method = "generateElites")
    public static class TheBeyond_insert_generateElites {
        @SpireInsertPatch(rloc = 4,localvars = {"monsters"})
        public static SpireReturn<Void> addElite(TheBeyond __instance, int count, ArrayList<MonsterInfo> monsters) {
            if(!(AbstractDungeon.player instanceof IdolCharacter||AbstractDungeon.player instanceof MisuzuCharacter)){
                return SpireReturn.Continue();
            }
            if(AbstractDungeon.player instanceof IdolCharacter){
                if(SkinSelectScreen.Inst.idolName.equals(IdolData.hrnm)||SkinSelectScreen.Inst.idolName.equals(IdolData.amao)) {
                    return SpireReturn.Continue();
                }
            }
            monsters.add(new MonsterInfo("rinamiAndMao", 2.0F));
            return SpireReturn.Continue();
        }
    }

}

