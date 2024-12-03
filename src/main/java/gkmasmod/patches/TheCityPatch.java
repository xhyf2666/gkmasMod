package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.stances.AbstractStance;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.event.Live_jsna;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.stances.FullPowerStance;
import gkmasmod.stances.PreservationStance;


public class TheCityPatch
{

    @SpirePatch(clz = TheCity.class,method = "initializeEventList")
    public static class TheCityPostPatch_initializeEventList {
        public static void Postfix(TheCity __instance) {
            if(AbstractDungeon.player instanceof IdolCharacter){
                for(int i=0;i<AbstractDungeon.eventList.size();i++){
                    if(AbstractDungeon.eventList.get(i).equals("Nest")){
                        AbstractDungeon.eventList.set(i, Live_jsna.ID);
                    }
                }
            }
        }
    }

}

