package gkmasmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.relics.PledgePetal;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.ThreeSizeHelper;


public class AwakenedOnePatch
{


    @SpirePatch(clz = AwakenedOne.class,method = "changeState")
    public static class AwakenedOneInsertPatch_changeState{
        @SpireInsertPatch(rloc = 17)
        public static void Insert(AwakenedOne __instance, String key) {
            if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasRelic(PocketBook.ID)){
                __instance.maxHealth = __instance.maxHealth * ThreeSizeHelper.getHealthRate(3);
                __instance.currentHealth = __instance.currentHealth * ThreeSizeHelper.getHealthRate(3);
            }
        }
    }
}

