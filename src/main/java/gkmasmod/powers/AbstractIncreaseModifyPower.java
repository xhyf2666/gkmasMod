package gkmasmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.patches.AbstractPowerPatch;

public abstract class AbstractIncreaseModifyPower extends AbstractPower {
    public String affectPowerID="";
    public int affectAmount;
    public int affectRate;

    public AbstractIncreaseModifyPower(String affectPowerID, int powerAmount, int powerRate) {
        this.affectPowerID = affectPowerID;
        this.affectAmount = powerAmount;
        this.affectRate = powerRate;
    }

    //固值增加
    public float modifyPower(AbstractPower power,float powerAmount) {
        if(AbstractPowerPatch.IgnoreIncreaseModifyField.flag.get(power))
            return powerAmount;
        if(power.ID.equals(affectPowerID)){
            powerAmount += affectAmount;
        }
        return powerAmount;
    }

    //倍率增加
    public float modifyPowerLast(AbstractPower power,float powerAmount) {
        if(AbstractPowerPatch.IgnoreIncreaseModifyField.flag.get(power))
            return powerAmount;
        if(power.ID.equals(affectPowerID)){
            powerAmount+=1.0f*power.amount* affectRate /100;
        }
        return powerAmount;
    }

//    @Override
//    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
//        if(AbstractPowerPatch.IgnoreIncreaseModifyField.flag.get(power))
//            return;
//        if(power.ID.equals(affectPowerID)){
//            power.amount += affectAmount;
//            power.amount += 1.0f*power.amount* affectRate /100;
//        }
//    }
}
