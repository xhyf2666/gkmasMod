package gkmasmod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.screen.ThreeSizeChangeScreen;
import gkmasmod.utils.IdolData;

public class CombatRewardScreenPatch {

    @SpirePatch(clz = CombatRewardScreen.class, method = "update")
    public static class PrefixCombatRewardScreenPatch_update {
        public static void Prefix(CombatRewardScreen _inst) {
            if (AbstractDungeon.player instanceof IdolCharacter){
                if(ThreeSizeChangeScreen.VoInst != null)
                    ThreeSizeChangeScreen.VoInst.update();
                if(ThreeSizeChangeScreen.DaInst != null)
                    ThreeSizeChangeScreen.DaInst.update();
                if(ThreeSizeChangeScreen.ViInst != null)
                    ThreeSizeChangeScreen.ViInst.update();
            }
        }
    }

    @SpirePatch(clz = CombatRewardScreen.class, method = "render")
    public static class PostfixCombatRewardScreenPatch_render {
        public static void Postfix(CombatRewardScreen _inst, SpriteBatch sb) {
            if (AbstractDungeon.player instanceof IdolCharacter){
                if(ThreeSizeChangeScreen.VoInst != null)
                    ThreeSizeChangeScreen.VoInst.render(sb);
                if(ThreeSizeChangeScreen.DaInst != null)
                    ThreeSizeChangeScreen.DaInst.render(sb);
                if(ThreeSizeChangeScreen.ViInst != null)
                    ThreeSizeChangeScreen.ViInst.render(sb);
            }
        }
    }

}

