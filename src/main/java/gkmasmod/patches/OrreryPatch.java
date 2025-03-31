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
import com.megacrit.cardcrawl.relics.Orrery;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.relics.PledgePetal;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.ThreeSizeHelper;


public class OrreryPatch
{
    @SpirePatch(clz = Orrery.class,method = "onEquip")
    public static class OrreryInsertPatch_onEquip{
        @SpireInsertPatch(rloc = 0)
        public static void Insert(Orrery __instance) {
            if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasRelic(PocketBook.ID)){
                if(GkmasMod.screenIndex==1)
                    AbstractDungeon.getCurrRoom().addCardToRewards();
            }
        }
    }
}

