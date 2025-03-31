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

import static gkmasmod.characters.PlayerColorEnum.gkmasModMisuzu_character;
import static gkmasmod.characters.PlayerColorEnum.gkmasMod_character;


public class DeathScreenPatch
{

    @SpirePatch(clz = DeathScreen.class,method = "updateAscensionProgress")
    public static class DeathScreenInsertPatch_constructor {
        @SpireInsertPatch(rloc = 812-809)
        public static void Insert(DeathScreen __instance) {
            if(AbstractDungeon.player instanceof IdolCharacter){
                AbstractPlayer misuzuPlayer = CardCrawlGame.characterManager.recreateCharacter(gkmasModMisuzu_character);
                StatsScreen.incrementAscension(misuzuPlayer.getCharStat());
            }
            else if(AbstractDungeon.player instanceof MisuzuCharacter){
                AbstractPlayer idolPlayer = CardCrawlGame.characterManager.recreateCharacter(gkmasMod_character);
                StatsScreen.incrementAscension(idolPlayer.getCharStat());
            }
        }
    }
}

