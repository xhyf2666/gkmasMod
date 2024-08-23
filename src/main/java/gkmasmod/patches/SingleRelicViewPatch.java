package gkmasmod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.SingleRelicViewPopup;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.ui.SingleRelicViewScreen;
import gkmasmod.ui.SkinSelectScreen;
import gkmasmod.utils.IdolData;

public class SingleRelicViewPatch {


    @SpirePatch(clz = SingleRelicViewPopup.class, method = "update")
    public static class UpdateButtonPatch {
        public static void Prefix(SingleRelicViewPopup _inst, AbstractRelic ___relic) {
            SingleRelicViewScreen.Inst.usedImg = ImageMaster.loadImage(String.format("img/meme/%s.png", ___relic.relicId));
            SingleRelicViewScreen.Inst.update();
        }
    }

    @SpirePatch(clz = SingleRelicViewPopup.class, method = "render")
    public static class RenderButtonPatch {
        public static void Postfix(SingleRelicViewPopup _inst, SpriteBatch sb) {
            SingleRelicViewScreen.Inst.render(sb);
        }
    }
}

