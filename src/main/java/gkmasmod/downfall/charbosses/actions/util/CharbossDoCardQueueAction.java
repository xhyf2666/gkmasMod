package gkmasmod.downfall.charbosses.actions.util;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CharbossDoCardQueueAction extends AbstractGameAction {

    private final AbstractCard c;

    public CharbossDoCardQueueAction(AbstractCard c) {
        super();
        this.c = c;
    }

    @Override
    public void update() {
        if (AbstractCharBoss.boss != null) {
            AbstractCharBoss.boss.useCard(c, AbstractCharBoss.boss, c.energyOnUse);
        }

        this.isDone = true;
    }

}
