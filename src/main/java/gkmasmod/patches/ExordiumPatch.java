package gkmasmod.patches;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.exordium.MonsterNadeshiko;
import gkmasmod.room.specialTeach.SpecialTeachScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class ExordiumPatch
{
    @SpirePatch(clz = Exordium.class,method = "initializeBoss")
    public static class Exordium_Prefix_initializeBoss {
        @SpirePrefixPatch
        public static SpireReturn<Void> addBoss() {
            if(!(AbstractDungeon.player instanceof IdolCharacter||AbstractDungeon.player instanceof MisuzuCharacter)){
                return SpireReturn.Continue();
            }
            Exordium.bossList.clear();
            if (Settings.isDailyRun) {
                Exordium.bossList.add("The Guardian");
                Exordium.bossList.add("Hexaghost");
                Exordium.bossList.add("Slime Boss");
                Collections.shuffle(Exordium.bossList, new Random(AbstractDungeon.monsterRng.randomLong()));
                AbstractDungeon.bossList.remove(AbstractDungeon.bossList.size() - 1);
            } else if (GkmasMod.onlyModBoss) {
                Exordium.bossList.add(MonsterNadeshiko.ID);
                Exordium.bossList.add(MonsterNadeshiko.ID);
            }
            else {
                Exordium.bossList.add(MonsterNadeshiko.ID);
                Exordium.bossList.add(MonsterNadeshiko.ID);
                Exordium.bossList.add("The Guardian");
                Exordium.bossList.add("Hexaghost");
                Exordium.bossList.add("Slime Boss");
                Collections.shuffle(Exordium.bossList, new Random(AbstractDungeon.monsterRng.randomLong()));
//                AbstractDungeon.bossList.remove(AbstractDungeon.bossList.size() - 1);
            }
            return SpireReturn.Return();
        }
    }

    @SpirePatch(clz = Exordium.class,method = "generateElites")
    public static class Exordium_insert_generateElites {
        @SpireInsertPatch(rloc = 4,localvars = {"monsters"})
        public static SpireReturn<Void> addElite(Exordium __instance, int count, ArrayList<MonsterInfo> monsters) {
            if(!(AbstractDungeon.player instanceof IdolCharacter||AbstractDungeon.player instanceof MisuzuCharacter)){
                return SpireReturn.Continue();
            }
            if(AbstractDungeon.player instanceof IdolCharacter){
                if(SkinSelectScreen.Inst.idolName.equals(IdolData.kllj)||SkinSelectScreen.Inst.idolName.equals(IdolData.ssmk)) {
                    return SpireReturn.Continue();
                }
            }
            monsters.add(new MonsterInfo("sumikaAndLilja", 1.0F));
            return SpireReturn.Continue();
        }
    }
}

