package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import gkmasmod.cardGrowEffect.ExhaustRemoveGrow;
import gkmasmod.utils.GrowHelper;

import java.util.Iterator;

public class HeavyLoveAction extends AbstractGameAction{
    private AbstractCreature owner;

    public HeavyLoveAction(AbstractCreature owner) {
        this.duration = 0.0F;
        this.owner = owner;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        Iterator var1 = DrawCardAction.drawnCards.iterator();
        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            if(c.exhaust){
                GrowHelper.grow(c, ExhaustRemoveGrow.growID,1);
            }
        }
        this.isDone = true;
    }
}
