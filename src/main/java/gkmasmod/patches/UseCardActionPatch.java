package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.BronzeAutomaton;
import gkmasmod.relics.PocketBook;
import gkmasmod.stances.WakeStance;
import gkmasmod.utils.ThreeSizeHelper;

import java.util.ArrayList;


public class UseCardActionPatch
{

    @SpirePatch(clz = UseCardAction.class,method = SpirePatch.CONSTRUCTOR,paramtypez = {AbstractCard.class, AbstractCreature.class})
    public static class UseCardActionInsertPatch_constructor {
        @SpireInsertPatch(rloc = 46-23)
        public static void Insert(UseCardAction __instance, AbstractCard card, AbstractCreature target) {
            if(AbstractDungeon.player.stance.ID.equals(WakeStance.STANCE_ID)){
                if(card.type==AbstractCard.CardType.CURSE){
                    card.exhaust = true;
                    __instance.exhaustCard = true;
                }
            }
        }
    }
}

