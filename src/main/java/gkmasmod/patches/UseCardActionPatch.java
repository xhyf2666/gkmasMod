package gkmasmod.patches;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.BronzeAutomaton;
import gkmasmod.cards.anomaly.FinalSpurt;
import gkmasmod.cards.anomaly.StepByStep;
import gkmasmod.relics.PocketBook;
import gkmasmod.stances.PreservationStance;
import gkmasmod.stances.WakeStance;
import gkmasmod.utils.ThreeSizeHelper;

import java.util.ArrayList;


public class UseCardActionPatch
{

    @SpirePatch(clz = UseCardAction.class,method = SpirePatch.CONSTRUCTOR,paramtypez = {AbstractCard.class, AbstractCreature.class})
    public static class InsertPatchUseCardAction_Constructor {
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

    @SpirePatch(clz = UseCardAction.class,method = "update")
    public static class InsertPatchUseCardAction_update {
        @SpireInsertPatch(rloc = 144-84)
        public static SpireReturn<Void> Insert(UseCardAction __instance, AbstractCard ___targetCard, @ByRef float[] ___duration) {
            if(AbstractDungeon.player.stance.ID.equals(PreservationStance.STANCE_ID)){
                if(___targetCard.cardID.equals(FinalSpurt.ID)||___targetCard.cardID.equals(StepByStep.ID)){
                    ___targetCard.exhaustOnUseOnce = false;
                    ___targetCard.dontTriggerOnUseCard = false;
                    AbstractDungeon.actionManager.addToBottom(new HandCheckAction());
                    __instance.isDone = true;
                    return SpireReturn.Return(null);
                }
            }
            return SpireReturn.Continue();
        }

    }
}

