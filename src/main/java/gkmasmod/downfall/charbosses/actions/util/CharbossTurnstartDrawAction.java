package gkmasmod.downfall.charbosses.actions.util;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class CharbossTurnstartDrawAction extends AbstractGameAction {

    @Override
    public void update() {
        if (AbstractCharBoss.boss != null) AbstractCharBoss.boss.endTurnStartTurn();
        this.isDone = true;
    }

}
