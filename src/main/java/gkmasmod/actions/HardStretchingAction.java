package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.EscapePlanAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class HardStretchingAction extends AbstractGameAction{
    private int blockGain;
    private AbstractCreature owner;

    public HardStretchingAction(AbstractCreature owner,int blockGain) {
        this.duration = 0.0F;
        this.owner = owner;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.blockGain = blockGain;
    }

    public void update() {
        Iterator var1 = DrawCardAction.drawnCards.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            if (c.type != AbstractCard.CardType.ATTACK) {
                AbstractDungeon.actionManager.addToTop(new GainBlockAction(this.owner, this.owner, this.blockGain));
                break;
            }
            else{
                break;
            }
        }

        this.isDone = true;
    }
}
