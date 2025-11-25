package gkmasmod.powers.increaseModifyPower;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.patches.AbstractPowerPatch;

public abstract class AbstractIncreaseModifyPower extends AbstractPower {
    public String affectPowerID;
    public int affectAmount;
    public int affectRate;
    public int affectTurn;
    public static final int MAGIC = -13;

    public AbstractIncreaseModifyPower(String affectPowerID, int powerAmount, int powerRate, int affectTurn) {
        this.affectPowerID = affectPowerID;
        this.affectAmount = powerAmount;
        this.affectRate = powerRate;
        this.affectTurn = affectTurn;
    }

    public AbstractIncreaseModifyPower(String affectPowerID, int powerAmount, int powerRate) {
        this(affectPowerID, powerAmount, powerRate, MAGIC);
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount == 0){
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            return;
        }
        if(this.affectTurn==MAGIC){
            if(this.affectAmount>0)
                this.affectAmount = this.amount;
            else if(this.affectRate>0)
                this.affectRate = this.amount;
        }
        else if(this.affectTurn>0){
            this.affectTurn = this.amount;
        }
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer){
        flash();
        if(this.affectTurn>0){
            addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }

    //固值增加
    public float modifyPower(AbstractPower power) {
        if(AbstractPowerPatch.IgnoreIncreaseModifyField.flag.get(power))
            return 0;
        if(power.ID.equals(affectPowerID)){
            return affectAmount;
        }
        return 0;
    }

    //倍率增加
    public float modifyPowerLast(AbstractPower power) {
        if(AbstractPowerPatch.IgnoreIncreaseModifyField.flag.get(power))
            return 0;
        if(power.ID.equals(affectPowerID)){
            return 1.0f*power.amount* affectRate /100;
        }
        return 0;
    }

}
