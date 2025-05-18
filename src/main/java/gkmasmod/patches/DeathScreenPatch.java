package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.characters.OtherIdolCharacter;

import static gkmasmod.characters.PlayerColorEnum.*;
import static gkmasmod.characters.PlayerColorEnum.gkmasModOther_character;


public class DeathScreenPatch
{

    /**
     * 4层死亡时，同步mod3种角色的进阶数
     */
    @SpirePatch(clz = DeathScreen.class,method = "updateAscensionProgress")
    public static class InsertPatchDeathScreen_updateAscensionProgress {
        @SpireInsertPatch(rloc = 812-809)
        public static void Insert(DeathScreen __instance) {
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

