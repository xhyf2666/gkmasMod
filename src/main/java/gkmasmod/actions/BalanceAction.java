package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.patches.AbstractPowerPatch;
import gkmasmod.powers.GoodTune;
import gkmasmod.relics.AbsoluteNewSelf;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.utils.PlayerHelper;

public class BalanceAction extends AbstractGameAction {
    private AbstractCreature p;

    /**
     * 平衡感Action：平均持有者的力量和好调层数。
     * @param p 触发者
     */
    public BalanceAction(AbstractCreature p) {
        this.p = p;
    }

    public void update() {
        int count_goodTune = PlayerHelper.getPowerAmount(this.p, GoodTune.POWER_ID);
        int count_strength = PlayerHelper.getPowerAmount(this.p, StrengthPower.POWER_ID);
        int count = (count_goodTune + count_strength+1)/2;
        if(count<=0){
            this.isDone = true;
            return;
        }
        AbstractPower power;
        if(this.p.hasPower(GoodTune.POWER_ID)) {
            power = new GoodTune(this.p, count - count_goodTune);
            if(count-count_goodTune>0)
                AbstractPowerPatch.IgnoreIncreaseModifyField.flag.set(power, true);
            addToBot(new ApplyPowerAction(this.p, this.p, power, count - count_goodTune));
        }
        else{
            power = new GoodTune(this.p, count);
            AbstractPowerPatch.IgnoreIncreaseModifyField.flag.set(power, true);
            addToBot(new ApplyPowerAction(this.p, this.p, power, count));
        }
        if(this.p.hasPower(StrengthPower.POWER_ID)){
            power = new StrengthPower(this.p, count - count_strength);
            if(count-count_strength>0)
                AbstractPowerPatch.IgnoreIncreaseModifyField.flag.set(power, true);
            addToBot(new ApplyPowerAction(this.p, this.p, power, count - count_strength));
        }
        else{
            power = new StrengthPower(this.p, count);
            AbstractPowerPatch.IgnoreIncreaseModifyField.flag.set(power, true);
            addToBot(new ApplyPowerAction(this.p, this.p, power, count));
        }
        this.isDone = true;
    }

}
