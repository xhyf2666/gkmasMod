package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.powers.TrainRoundAnomalyPower;

public class TrainRoundAnomalyFirstAction extends AbstractGameAction {

    public TrainRoundAnomalyFirstAction() {
    }

    public void update() {
        if(AbstractDungeon.player.hasPower(TrainRoundAnomalyPower.POWER_ID)){
            AbstractDungeon.player.getPower(TrainRoundAnomalyPower.POWER_ID).atStartOfTurnPostDraw();
        }
        this.isDone = true;
    }

}
