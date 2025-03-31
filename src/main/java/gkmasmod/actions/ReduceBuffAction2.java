package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.powers.*;
import gkmasmod.utils.PlayerHelper;

import java.util.ArrayList;

public class ReduceBuffAction2 extends AbstractGameAction {
    AbstractCreature owner;
    private int require;
    private int amount;
    private float rate;

    public ReduceBuffAction2(AbstractCreature owner, int require,int amount,float rate) {
        this.owner = owner;
        this.require = require;
        this.amount = amount;
        this.rate = rate;
    }

    public void update() {
        ArrayList<String> buffs = new ArrayList<>();
        for (AbstractPower power : this.owner.powers) {
            if (power.type == AbstractPower.PowerType.BUFF) {
                if(power.ID.equals(AllEffort.POWER_ID)||power.ID.equals(TempSavePower.POWER_ID)||power.ID.equals(TrainRoundAnomalyPower.POWER_ID)||power.ID.equals(TrainRoundSensePower.POWER_ID)||power.ID.equals(TrainRoundLogicPower.POWER_ID)){
                    continue;
                }
                buffs.add(power.ID);
            }
        }
        int i = 0;
        for(String buff : buffs) {
            if(i>=amount)
                break;
            int count = PlayerHelper.getPowerAmount(owner, buff);
            if(count>require){
                count = (int) (1.0F* count * (1-rate));
                this.owner.getPower(buff).amount = count;
                this.owner.getPower(buff).updateDescription();
                i++;
            }

        }

        this.isDone = true;
    }
}