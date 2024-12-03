package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.powers.EnthusiasticPower;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.powers.StanceLock;
import gkmasmod.powers.TrainRoundAnomalyPower;
import gkmasmod.stances.FullPowerStance;
import gkmasmod.utils.PlayerHelper;

public class FullPowerValueAction extends AbstractGameAction {

    public FullPowerValueAction() {
    }

    public void update() {
        if(AbstractDungeon.player.hasPower(StanceLock.POWER_ID)){
            this.isDone = true;
            return;
        }

        int amount = PlayerHelper.getPowerAmount(AbstractDungeon.player,FullPowerValue.POWER_ID);

        int amountBak = amount;

        if (amount >= 10) {
            addToTop(new ChangeStanceAction(FullPowerStance.STANCE_ID));
            AbstractDungeon.player.getPower(FullPowerValue.POWER_ID).flash();
            amount -= 10;

            while(amount>0){
                if(amount>=2){
                    amount-=2;
                    addToBot(new GainBlockWithPowerAction(AbstractDungeon.player,AbstractDungeon.player,5));
                }
                else{
                    break;
                }
                if(amount>=3){
                    amount-=3;
                    addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new EnthusiasticPower(AbstractDungeon.player,3),3));
                }
                else{
                    break;
                }
                if(amount>=5){
                    amount-=5;
                    addToBot(new GainTrainRoundPowerAction(AbstractDungeon.player,1));
                }
                else{
                    break;
                }
            }

            if(amount>0){
                addToBot(new ReducePowerAction(AbstractDungeon.player,AbstractDungeon.player,FullPowerValue.POWER_ID,amountBak-amount));
            }
            else{
                addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, FullPowerValue.POWER_ID));
            }
//            AbstractDungeon.player.getPower(FullPowerValue.POWER_ID).amount -= 10;
//            if (PlayerHelper.getPowerAmount(AbstractDungeon.player,FullPowerValue.POWER_ID) <= 0) {
//                this.addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, FullPowerValue.POWER_ID));
//            }
        }
        this.isDone = true;
    }

}
