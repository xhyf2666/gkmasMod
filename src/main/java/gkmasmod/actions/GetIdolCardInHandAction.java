package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.powers.TempSavePower;

import java.util.ArrayList;
import java.util.Iterator;


public class GetIdolCardInHandAction extends AbstractGameAction {
    public GetIdolCardInHandAction() {

    }

    public void update() {

        Iterator<AbstractCard> drawPileIterator = AbstractDungeon.player.drawPile.group.iterator();
        while (drawPileIterator.hasNext()) {
            AbstractCard c = drawPileIterator.next();
            if (c.tags.contains(GkmasCardTag.IDOL_CARD_TAG)) {
                AbstractDungeon.player.hand.addToHand(c);
                drawPileIterator.remove();
            }
        }

        Iterator<AbstractCard> discardPileIterator = AbstractDungeon.player.discardPile.group.iterator();
        while (discardPileIterator.hasNext()) {
            AbstractCard c = discardPileIterator.next();
            if (c.tags.contains(GkmasCardTag.IDOL_CARD_TAG)) {
                AbstractDungeon.player.hand.addToHand(c);
                discardPileIterator.remove();
            }
        }
        this.isDone = true;
    }

}
