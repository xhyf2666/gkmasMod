package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class DreamPlaceAction extends AbstractGameAction{
    private int blockGain;
    private AbstractCreature owner;

    public DreamPlaceAction(AbstractCreature owner, int blockGain) {
        this.duration = 0.0F;
        this.owner = owner;
        this.actionType = ActionType.WAIT;
        this.blockGain = blockGain;
    }

    public void update() {
        Iterator var1 = DrawCardAction.drawnCards.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            if(this.blockGain>0)
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, this.blockGain));
            if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS) {
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1,new DreamPlaceAction(this.owner, this.blockGain)));
            }
            break;
        }

        this.isDone = true;
    }
}
