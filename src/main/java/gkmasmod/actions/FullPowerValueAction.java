package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import gkmasmod.downfall.charbosses.actions.common.EnemyGainEnergyAction;
import gkmasmod.downfall.charbosses.actions.unique.EnemyChangeStanceAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.stances.ENFullPowerStance;
import gkmasmod.powers.EnthusiasticPower;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.powers.StanceLock;
import gkmasmod.powers.TrainRoundAnomalyPower;
import gkmasmod.stances.FullPowerStance;
import gkmasmod.utils.PlayerHelper;

public class FullPowerValueAction extends AbstractGameAction {
    private AbstractCreature owner;
    private boolean flag = false;

    public FullPowerValueAction(AbstractCreature owner) {
        this(owner,false);
    }

    public FullPowerValueAction(AbstractCreature owner,boolean flag) {
        this.owner = owner;
        this.flag = flag;
    }

    public void update() {
        if(this.owner.hasPower(StanceLock.POWER_ID)){
            this.isDone = true;
            return;
        }

        int amount = PlayerHelper.getPowerAmount(this.owner,FullPowerValue.POWER_ID);
        if(flag)
            amount+=10;

        if (amount >= 10) {
            if(this.owner instanceof AbstractPlayer)
                addToTop(new ChangeStanceAction(FullPowerStance.STANCE_ID));
            else if(this.owner instanceof AbstractCharBoss)
                addToTop(new EnemyChangeStanceAction(ENFullPowerStance.STANCE_ID));
            if(this.owner.hasPower(FullPowerValue.POWER_ID))
                this.owner.getPower(FullPowerValue.POWER_ID).flash();

            int a = amount/10-1;
            int b = amount%10;
            int left = b;

            int p1,p2,p3,p4;
            p1 = p2 = p3 = p4 = a;

            if(b>=2){
                p1++;
                left -= 2;
            }
            if(b>=5){
                p2++;
                left -= 2;
            }
            if(b>=7){
                p4++;
                left -= 2;
            }

            if(p1>0){
                addToBot(new ApplyPowerAction(this.owner,this.owner,new EnthusiasticPower(this.owner,p1*2),p1*2));
            }
            if(p2>0)
                addToBot(new GainBlockAction(this.owner,this.owner,p2*7));
            if(p4>0){
                addToBot(new ApplyPowerAction(this.owner,this.owner,new DexterityPower(this.owner,p4*2),p4*2));
                for (int i = 0; i < p4; i++) {
                    addToBot(new ApplyPowerAction(this.owner,this.owner,new LoseDexterityPower(this.owner,2),2,true));
                }
            }
            if(p3>0){
                if(this.owner instanceof AbstractPlayer)
                    addToBot(new GainEnergyAction(p3));
                else if(this.owner instanceof AbstractCharBoss)
                    addToBot(new EnemyGainEnergyAction(p3));
            }
//            addToBot(new GainTrainRoundPowerAction(AbstractDungeon.player,p3));

            if(left>0){
                addToBot(new ReducePowerAction(this.owner,this.owner,FullPowerValue.POWER_ID,amount-left));
            }
            else{
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, FullPowerValue.POWER_ID));
            }
//            AbstractDungeon.player.getPower(FullPowerValue.POWER_ID).amount -= 10;
//            if (PlayerHelper.getPowerAmount(AbstractDungeon.player,FullPowerValue.POWER_ID) <= 0) {
//                this.addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, FullPowerValue.POWER_ID));
//            }
        }
        this.isDone = true;
    }

}
