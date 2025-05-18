package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.unlock.relics.defect.CablesUnlock;
import gkmasmod.characters.OtherIdolCharacter;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.relics.BalanceLogicAndSense;
import gkmasmod.relics.PocketBook;
import gkmasmod.relics.ProducerGlass;
import gkmasmod.screen.OtherSkinSelectScreen;
import gkmasmod.utils.IdolData;

import java.util.ArrayList;


public class RelicLibraryPatch
{
    @SpirePatch(clz= RelicLibrary.class,method="populateRelicPool")
    public static class PostPatchRelicLibrary_populateRelicPool{
        @SpirePostfixPatch
        public static void Postfix(ArrayList<String> pool, AbstractRelic.RelicTier tier, AbstractPlayer.PlayerClass chosenClass) {
            if(chosenClass== PlayerColorEnum.gkmasModOther_character){
                if (tier == AbstractRelic.RelicTier.COMMON) {
                    pool.add(ProducerGlass.ID);
                }
                else if (tier == AbstractRelic.RelicTier.SHOP) {
                    pool.add(BalanceLogicAndSense.ID);
                }
                if(OtherSkinSelectScreen.Inst.idolName.equals(IdolData.arnm)){
                    if (tier == AbstractRelic.RelicTier.COMMON) {
                        pool.add(DataDisk.ID);
                    }
                    else if (tier == AbstractRelic.RelicTier.UNCOMMON) {
                        pool.add(SymbioticVirus.ID);
                        pool.add(GoldPlatedCables.ID);
                    }
                    else if (tier == AbstractRelic.RelicTier.RARE) {
                        pool.add(EmotionChip.ID);
                    }
                    else if (tier == AbstractRelic.RelicTier.BOSS) {
                        pool.add(NuclearBattery.ID);
                        pool.add(Inserter.ID);
                    }
                    else if (tier == AbstractRelic.RelicTier.SHOP) {
                        pool.add(RunicCapacitor.ID);
                    }
                }
            }
            if(chosenClass== PlayerColorEnum.gkmasModMisuzu_character){
                if (tier == AbstractRelic.RelicTier.COMMON) {
                    pool.add(ProducerGlass.ID);
                }
                else if (tier == AbstractRelic.RelicTier.SHOP) {
                    pool.add(BalanceLogicAndSense.ID);
                }
            }
        }
    }

    @SpirePatch(clz= RelicLibrary.class,method="addClassSpecificRelics")
    public static class PostPatchRelicLibrary_addClassSpecificRelics{
        @SpirePostfixPatch
        public static void Postfix(ArrayList<AbstractRelic> relicPool) {
            AbstractPlayer.PlayerClass chosenClass = AbstractDungeon.player.chosenClass;
            if(chosenClass== PlayerColorEnum.gkmasModOther_character){
                relicPool.add(new ProducerGlass());
                relicPool.add(new BalanceLogicAndSense());
                if(OtherSkinSelectScreen.Inst.idolName.equals(IdolData.arnm)){
                    relicPool.add(new DataDisk());
                    relicPool.add(new SymbioticVirus());
                    relicPool.add(new GoldPlatedCables());
                    relicPool.add(new EmotionChip());
                    relicPool.add(new NuclearBattery());
                    relicPool.add(new Inserter());
                    relicPool.add(new RunicCapacitor());
                }
            }
            if(chosenClass== PlayerColorEnum.gkmasModMisuzu_character){
                relicPool.add(new ProducerGlass());
                relicPool.add(new BalanceLogicAndSense());
            }
        }
    }
}

