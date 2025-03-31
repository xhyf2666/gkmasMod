package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.cards.anomaly.BeyondTheCrossing;
import gkmasmod.powers.TempSavePower;

import java.util.ArrayList;
import java.util.Iterator;


public class PlasticUmbrellaThatDayAction extends AbstractGameAction {
    public PlasticUmbrellaThatDayAction() {

    }

    public void update() {
        Iterator<AbstractCard> iterator;
        iterator = AbstractDungeon.player.hand.group.iterator();
        ArrayList<AbstractCard> cards = new ArrayList<>();
        while (iterator.hasNext()) {
            AbstractCard c = iterator.next();
            if (c.cardID.equals(BeyondTheCrossing.ID)) {
                cards.add(c);
            }
        }
        iterator = AbstractDungeon.player.drawPile.group.iterator();
        while (iterator.hasNext()) {
            AbstractCard c = iterator.next();
            if (c.cardID.equals(BeyondTheCrossing.ID)) {
                cards.add(c);
            }
        }
        iterator = AbstractDungeon.player.discardPile.group.iterator();
        while (iterator.hasNext()) {
            AbstractCard c = iterator.next();
            if (c.cardID.equals(BeyondTheCrossing.ID)) {
                cards.add(c);
            }
        }
        for (AbstractCard c : cards) {
            TempSavePower.addCard(AbstractDungeon.player, c);
        }
        this.isDone = true;
    }

}
