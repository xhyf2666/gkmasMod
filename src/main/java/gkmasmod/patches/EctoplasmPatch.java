package gkmasmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.Ectoplasm;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.relics.PledgePetal;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.ImageHelper;


public class EctoplasmPatch
{
    private static String append = CardCrawlGame.languagePack.getUIString("gkmasMod:EctoplasmSpecial").TEXT[0];

    @SpirePatch(clz = Ectoplasm.class,method = "setDescription")
    public static class EctoplasmPrePatch_setDescription {
        public static SpireReturn<String> Prefix(Ectoplasm __instance) {
            return SpireReturn.Return(__instance.DESCRIPTIONS[1]+__instance.DESCRIPTIONS[0]+append);
        }
    }

}

