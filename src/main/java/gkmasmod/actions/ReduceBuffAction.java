package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.powers.*;
import gkmasmod.utils.PlayerHelper;

import java.util.ArrayList;

public class ReduceBuffAction extends AbstractGameAction {
    AbstractCreature owner;
    private int reduceAmount;

    public ReduceBuffAction(AbstractCreature owner, int reduceAmount) {
        this.owner = owner;
        this.reduceAmount = reduceAmount;
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
        for(String buff : buffs) {
            int count = PlayerHelper.getPowerAmount(owner, buff);
            if(count>0){
                count-=reduceAmount;
                if(count<=0)
                    count = 1;
                this.owner.getPower(buff).amount = count;
                this.owner.getPower(buff).updateDescription();
            }

        }

        this.isDone = true;
    }
}