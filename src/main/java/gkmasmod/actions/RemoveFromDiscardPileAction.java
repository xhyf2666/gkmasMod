package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RemoveFromDiscardPileAction extends AbstractGameAction {

    AbstractCard card;

    public RemoveFromDiscardPileAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.discardPile.contains(this.card)) {
            AbstractDungeon.player.discardPile.removeCard(this.card);
        }
        this.isDone = true;
    }
}
