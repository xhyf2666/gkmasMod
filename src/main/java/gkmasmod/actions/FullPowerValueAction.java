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

        if (amount >= 10) {
            addToTop(new ChangeStanceAction(FullPowerStance.STANCE_ID));
            AbstractDungeon.player.getPower(FullPowerValue.POWER_ID).flash();

            int a = amount/10-1;
            int b = amount%10;
            int left = b;

            int p1,p2,p3;
            p1 = p2 = p3 = a;

            if(b>=2){
                p1++;
                left -= 2;
            }
            if(b>=5){
                p2++;
                left -= 2;
            }

            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new EnthusiasticPower(AbstractDungeon.player,p1*5),p1*5));
            addToBot(new GainBlockWithPowerAction(AbstractDungeon.player,AbstractDungeon.player,p2*16));
            addToBot(new GainTrainRoundPowerAction(AbstractDungeon.player,p3));

            if(left>0){
                addToBot(new ReducePowerAction(AbstractDungeon.player,AbstractDungeon.player,FullPowerValue.POWER_ID,amount-left));
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
