package gkmasmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.cards.free.Sleepy;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.powers.SleepPower;
import gkmasmod.powers.SleepPower2;
import gkmasmod.vfx.effect.ThreeSizeChangeEffect;

import java.util.ArrayList;

public class CardGroupPatch {
    @SpirePatch(clz = CardGroup.class,method = "moveToExhaustPile")
    public static class PostPatchAbstractDungeonConstructor {
        @SpirePostfixPatch
        public static void Postfix(CardGroup __instance, AbstractCard c) {
            if(c.cardID == Sleepy.ID){
                for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                    if (!monster.isDead && !monster.isDying) {
                        if(monster.hasPower(SleepPower.POWER_ID))
                            monster.getPower(SleepPower.POWER_ID).onExhaust(c);
                        if(monster.hasPower(SleepPower2.POWER_ID))
                            monster.getPower(SleepPower2.POWER_ID).onExhaust(c);
                    }
                }
            }
            if(c.tags.contains(GkmasCardTag.OUTSIDE_TAG)){
                AbstractDungeon.player.exhaustPile.removeTopCard();
            }
        }
    }

}
