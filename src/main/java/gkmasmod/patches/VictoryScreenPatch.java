package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.characters.OtherIdolCharacter;
import gkmasmod.stances.WakeStance;

import static gkmasmod.characters.PlayerColorEnum.*;


public class VictoryScreenPatch
{

    @SpirePatch(clz = VictoryScreen.class,method = SpirePatch.CONSTRUCTOR,paramtypez = {MonsterGroup.class})
    public static class InsertPatchVictoryScreen_Constructor {
        @SpireInsertPatch(rloc = 147-64)
        public static void Insert(VictoryScreen __instance, MonsterGroup m) {
            //进阶数同步
            try{
                if(AbstractDungeon.player instanceof IdolCharacter){
                    AbstractPlayer misuzuPlayer = CardCrawlGame.characterManager.recreateCharacter(gkmasModMisuzu_character);
                    StatsScreen.incrementAscension(misuzuPlayer.getCharStat());
                    AbstractPlayer otherIdolPlayer = CardCrawlGame.characterManager.recreateCharacter(gkmasModOther_character);
                    StatsScreen.incrementAscension(otherIdolPlayer.getCharStat());
                }
                else if(AbstractDungeon.player instanceof MisuzuCharacter){
                    AbstractPlayer idolPlayer = CardCrawlGame.characterManager.recreateCharacter(gkmasMod_character);
                    StatsScreen.incrementAscension(idolPlayer.getCharStat());
                    AbstractPlayer otherIdolPlayer = CardCrawlGame.characterManager.recreateCharacter(gkmasModOther_character);
                    StatsScreen.incrementAscension(otherIdolPlayer.getCharStat());
                }
                else if(AbstractDungeon.player instanceof OtherIdolCharacter){
                    AbstractPlayer idolPlayer = CardCrawlGame.characterManager.recreateCharacter(gkmasMod_character);
                    StatsScreen.incrementAscension(idolPlayer.getCharStat());
                    AbstractPlayer misuzuPlayer = CardCrawlGame.characterManager.recreateCharacter(gkmasModMisuzu_character);
                    StatsScreen.incrementAscension(misuzuPlayer.getCharStat());
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

