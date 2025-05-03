package gkmasmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import gkmasmod.actions.RemoveFromDiscardPileAction;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.cards.anomaly.FinalSpurt;
import gkmasmod.cards.anomaly.StepByStep;
import gkmasmod.cards.free.Sleepy;
import gkmasmod.powers.MisuzuDreamPower;
import gkmasmod.powers.SleepPower;
import gkmasmod.powers.SleepPower2;
import gkmasmod.powers.SleepyCardPowerPower;
import gkmasmod.stances.PreservationStance;
import gkmasmod.vfx.effect.ThreeSizeChangeEffect;

import java.util.ArrayList;

public class CardGroupPatch {
    @SpirePatch(clz = CardGroup.class,method = "moveToExhaustPile")
    public static class PostPatchCardGroup_moveToExhaustPile {
        @SpirePostfixPatch
        public static void Postfix(CardGroup __instance, AbstractCard c) {
            if(c.cardID == Sleepy.ID){
                for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                    if (!monster.isDead && !monster.isDying) {
                        if(monster.hasPower(SleepPower.POWER_ID))
                            monster.getPower(SleepPower.POWER_ID).onExhaust(c);
                        if(monster.hasPower(SleepPower2.POWER_ID))
                            monster.getPower(SleepPower2.POWER_ID).onExhaust(c);
                        if(monster.hasPower(MisuzuDreamPower.POWER_ID))
                            monster.getPower(MisuzuDreamPower.POWER_ID).onExhaust(c);
                    }
                }
            }
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!monster.isDeadOrEscaped()) {
                    if(monster.hasPower(SleepyCardPowerPower.POWER_ID)){
                        monster.getPower(SleepyCardPowerPower.POWER_ID).onExhaust(c);
                    }
                }
            }
            if(c.tags.contains(GkmasCardTag.OUTSIDE_TAG)){
                AbstractDungeon.player.exhaustPile.removeTopCard();
            }
        }
    }

    @SpirePatch(clz = CardGroup.class,method = "moveToDiscardPile")
    public static class PrefixPatchCardGroup_moveToDiscardPile {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(CardGroup __instance, AbstractCard c) {
//            if(c.cardID.equals(FinalSpurt.ID)||c.cardID.equals(StepByStep.ID)){
//                if(AbstractDungeon.player.stance.ID.equals(PreservationStance.STANCE_ID)){
//                    AbstractDungeon.actionManager.addToBottom(new RemoveFromDiscardPileAction(c));
////                    SpireReturn.Return(null);
//                }
//            }
            return SpireReturn.Continue();
        }
    }

}
