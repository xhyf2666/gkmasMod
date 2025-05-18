package gkmasmod.patches;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.characters.OtherIdolCharacter;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.exordium.MonsterNadeshiko;
import gkmasmod.monster.exordium.MonsterShion;
import gkmasmod.room.specialTeach.SpecialTeachScreen;
import gkmasmod.screen.OtherSkinSelectScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class ExordiumPatch
{
    /**
     * 设置1层Boss
     */
    @SpirePatch(clz = Exordium.class,method = "initializeBoss")
    public static class PrePatchExordium__initializeBoss {
        @SpirePrefixPatch
        public static SpireReturn<Void> prefix() {
            if(!(AbstractDungeon.player instanceof IdolCharacter||AbstractDungeon.player instanceof MisuzuCharacter||AbstractDungeon.player instanceof OtherIdolCharacter)){
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
                if(AbstractDungeon.player instanceof OtherIdolCharacter && OtherSkinSelectScreen.Inst.idolName.equals(IdolData.andk)){
                    Exordium.bossList.add("The Guardian");
                    Exordium.bossList.add("Hexaghost");
                    Exordium.bossList.add("Slime Boss");
                    Collections.shuffle(Exordium.bossList, new Random(AbstractDungeon.monsterRng.randomLong()));
                }
                else{
                    TheCity.bossList.add(MonsterNadeshiko.ID);
                    TheCity.bossList.add(MonsterNadeshiko.ID);
                }
                Exordium.bossList.add(MonsterNadeshiko.ID);
                Exordium.bossList.add(MonsterNadeshiko.ID);
            }
            else {
                if(AbstractDungeon.player instanceof OtherIdolCharacter && OtherSkinSelectScreen.Inst.idolName.equals(IdolData.andk)){

                }
                else{
                    TheCity.bossList.add(MonsterNadeshiko.ID);
                    TheCity.bossList.add(MonsterNadeshiko.ID);
                }
                Exordium.bossList.add("The Guardian");
                Exordium.bossList.add("Hexaghost");
                Exordium.bossList.add("Slime Boss");
                Collections.shuffle(Exordium.bossList, new Random(AbstractDungeon.monsterRng.randomLong()));
//                AbstractDungeon.bossList.remove(AbstractDungeon.bossList.size() - 1);
            }
            return SpireReturn.Return();
        }
    }

    /**
     * 设置1层精英
     */
    @SpirePatch(clz = Exordium.class,method = "generateElites")
    public static class InsertPatchExordium_generateElites {
        @SpireInsertPatch(rloc = 4,localvars = {"monsters"})
        public static SpireReturn<Void> insert(Exordium __instance, int count, ArrayList<MonsterInfo> monsters) {
            if(!(AbstractDungeon.player instanceof IdolCharacter||AbstractDungeon.player instanceof MisuzuCharacter||AbstractDungeon.player instanceof OtherIdolCharacter)){
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

