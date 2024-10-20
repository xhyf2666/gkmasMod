package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.BronzeOrb;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.ThreeSizeHelper;

import java.util.ArrayList;


public class CancelButtonPatch
{
    @SpirePatch(clz = CancelButton.class,method = "update")
    public static class CancelButtonInsert_update {
        @SpireInsertPatch(rloc = 52)
        public static SpireReturn<Void> Insert(CancelButton _inst) {
            return SpireReturn.Continue();
        }
    }
}

