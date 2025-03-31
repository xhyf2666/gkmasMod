package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import gkmasmod.downfall.charbosses.actions.common.EnemyMakeTempCardInHandAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;

public class EnemyDrawAction extends AbstractGameAction {
    int numCards;
    boolean top;

    public EnemyDrawAction(int numCards,boolean top) {
        this.numCards = numCards;
        this.top = top;
    }

    public void update() {
        for (int i = 0; i < numCards; i++) {
            if(AbstractCharBoss.boss.drawPile.isEmpty())
            {
                this.isDone = true;
                return;
            }
            AbstractCard card = AbstractCharBoss.boss.drawPile.getBottomCard();
            AbstractCharBoss.boss.drawPile.removeCard(card);
            if(top){
                addToTop(new EnemyMakeTempCardInHandAction(card));
            }
            else{
                addToBot(new EnemyMakeTempCardInHandAction(card));
            }
        }
        this.isDone = true;
    }

}
