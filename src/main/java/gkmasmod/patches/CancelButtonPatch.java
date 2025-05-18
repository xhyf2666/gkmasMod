package gkmasmod.patches;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.BronzeOrb;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.relics.PocketBook;
import gkmasmod.room.specialTeach.SpecialTeachScreen;
import gkmasmod.utils.ThreeSizeHelper;

import java.util.ArrayList;


public class CancelButtonPatch
{
    /**
     * 特殊指导界面，点击 取消 按钮时，如果还没指导过则保留界面
     */
    @SpirePatch(clz = CancelButton.class,method = "update")
    public static class InsertPatchCancelButton_update {
        @SpireInsertPatch(rloc = 63)
        public static SpireReturn<Void> Insert(CancelButton _inst) {
            if(GkmasMod.screenIndex==3){
                SpecialTeachScreen screen = ((SpecialTeachScreen)BaseMod.getCustomScreen(SpecialTeachScreen.Enum.SpecialTeach_Screen));
                screen.cancelUpgrade();
                if(!screen.usedCustomEffect)
                    return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}

