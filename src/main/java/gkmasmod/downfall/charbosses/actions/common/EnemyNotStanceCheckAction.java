package gkmasmod.downfall.charbosses.actions.common;


import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.stances.EnNeutralStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class EnemyNotStanceCheckAction extends AbstractGameAction {
    private final AbstractGameAction actionToBuffer;

    public EnemyNotStanceCheckAction(AbstractGameAction actionToCheck) {
        this.actionToBuffer = actionToCheck;
    }

    public void update() {
        if (!(AbstractCharBoss.boss.stance instanceof EnNeutralStance)) {
            this.addToBot(this.actionToBuffer);
        }

        this.isDone = true;
    }
}
