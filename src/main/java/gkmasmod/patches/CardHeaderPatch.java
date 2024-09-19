package gkmasmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import gkmasmod.cards.GkmasCard;


public class CardHeaderPatch
{
    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderTitle", paramtypez = {SpriteBatch.class})
    public static class SingleCardViewPatch {
        private static float drawScale = 2.0F;
        private static float yOffsetBase = 690.0F;

        @SpirePostfixPatch
        public static void Postfix(SingleCardViewPopup _inst, SpriteBatch sb, AbstractCard ___card, float ___current_y) {
            if (___card instanceof GkmasCard) {
                GkmasCard card = (GkmasCard)___card;
                card.renderCardHeader(sb, Settings.WIDTH / 2.0F, ___current_y, yOffsetBase, drawScale);
            }
        }
    }
}

