package gkmasmod.downfall.charbosses.actions.orb;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class EnemyAnimateOrbAction extends AbstractGameAction {
    private final int orbCount;

    public EnemyAnimateOrbAction(final int amount) {
        this.orbCount = amount;
    }

    @Override
    public void update() {
        for (int i = 0; i < this.orbCount; ++i) {
            AbstractCharBoss.boss.triggerEvokeAnimation(i);
        }
        this.isDone = true;
    }
}
