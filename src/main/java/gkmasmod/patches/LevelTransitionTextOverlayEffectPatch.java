package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.beyond.TimeEater;
import com.megacrit.cardcrawl.vfx.scene.LevelTransitionTextOverlayEffect;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.ThreeSizeHelper;


public class LevelTransitionTextOverlayEffectPatch
{

    @SpirePatch(clz = LevelTransitionTextOverlayEffect.class,method = "update")
    public static class PrePatchLevelTransitionTextOverlayEffect_update {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(LevelTransitionTextOverlayEffect __instance) {
            if(GkmasMod.playVideo)
                return SpireReturn.Return(null);
            return SpireReturn.Continue();
        }
    }
}

