package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.powers.TrainRoundAnomalyPower;
import gkmasmod.powers.TrainRoundProducePower;

public class TrainRoundProduceFirstAction extends AbstractGameAction {

    public TrainRoundProduceFirstAction() {
    }

    public void update() {
        if(AbstractDungeon.player.hasPower(TrainRoundProducePower.POWER_ID)){
            AbstractDungeon.player.getPower(TrainRoundProducePower.POWER_ID).atStartOfTurnPostDraw();
        }
        this.isDone = true;
    }

}
