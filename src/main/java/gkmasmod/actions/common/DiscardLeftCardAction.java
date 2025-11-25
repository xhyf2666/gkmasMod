package gkmasmod.actions.common;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import gkmasmod.patches.AbstractMonsterPatch;

public class DiscardLeftCardAction extends AbstractGameAction {

    private int num;

    public DiscardLeftCardAction(int num) {
        this.num = num;
    }

    public void update() {
        for (int i = 0; i < num; i++) {
            if (AbstractDungeon.player.hand.size() > 0) {
                AbstractCard c = AbstractDungeon.player.hand.getBottomCard();
                AbstractDungeon.player.hand.moveToDiscardPile(c);
                c.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
            }
        }
        this.isDone = true;
    }
}
