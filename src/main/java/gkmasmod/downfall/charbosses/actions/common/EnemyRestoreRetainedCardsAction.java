package gkmasmod.downfall.charbosses.actions.common;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

import java.util.Iterator;

public class EnemyRestoreRetainedCardsAction extends AbstractGameAction {
    private final CardGroup group;
    private final AbstractCharBoss boss;

    public EnemyRestoreRetainedCardsAction(AbstractCharBoss boss, final CardGroup group) {
        this.setValues(boss, this.source, -1);
        this.group = group;
        this.boss = boss;
    }

    @Override
    public void update() {
        this.isDone = true;
        final Iterator<AbstractCard> c = this.group.group.iterator();
        while (c.hasNext()) {
            final AbstractCard e = c.next();
            if (e.retain || e.selfRetain) {
                e.onRetained();
                this.boss.hand.addToTop(e);
                e.retain = false;
                c.remove();
            }
        }
        if (boss != null) {
            this.boss.hand.refreshHandLayout();
        }
    }
}
