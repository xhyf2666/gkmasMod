package gkmasmod.patches;

import basemod.patches.com.megacrit.cardcrawl.saveAndContinue.SaveFile.ModSaves;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.GoodTune;
import gkmasmod.relics.*;
import gkmasmod.ui.SkinSelectScreen;

public class LoadPlayerSavesPatch {

    @SpirePatch(clz= SaveAndContinue.class, method="loadSaveFile",paramtypez ={String.class})
    public static class GetIdolNamePatch {
        @SpireInsertPatch(rloc = 24, localvars = {"saveFile"})
        public static void InsertPatch(String filePath, SaveFile saveFile) {
            ModSaves.HashMapOfJsonElement modSaves = ModSaves.modSaves.get(saveFile);
            if(modSaves!=null){
                Gson saveFileGson = new Gson();
                int[] data = saveFileGson.fromJson(modSaves.get("gkmasMod:skin"), int[].class);
                System.out.println("LoadPlayerSavesPatch: " + data);
                if(data != null && data.length == 3){
                    SkinSelectScreen.Inst.idolIndex = data[0];
                    SkinSelectScreen.Inst.skinIndex = data[1];
                    SkinSelectScreen.Inst.updateIndex = data[2];
                    SkinSelectScreen.Inst.refresh();
                    GkmasMod.listeners.forEach(listener -> listener.onCardImgUpdate());
                }
            }
        }
    }
}
