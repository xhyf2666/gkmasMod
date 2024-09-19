package gkmasmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import gkmasmod.cards.GkmasCard;


public class CardUpgradePatch
{
    @SpirePatch(clz = AbstractCard.class,method = "upgradeName")
    public static class CardUpgradePostPatch {
        public static void Postfix(AbstractCard __instance) {

            //System.out.println("upgradeName"+__instance.name);
        }
    }
}

