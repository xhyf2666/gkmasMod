package gkmasmod.downfall.charbosses.actions.utility;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class EnemyHandCheckAction extends AbstractGameAction {
    private final AbstractCharBoss player;

    public EnemyHandCheckAction() {
        this.player = AbstractCharBoss.boss;
    }

    @Override
    public void update() {
        this.player.hand.applyPowers();
        this.player.hand.glowCheck();
        this.isDone = true;
    }
}
