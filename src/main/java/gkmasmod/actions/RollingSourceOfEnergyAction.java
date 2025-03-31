package gkmasmod.actions;


import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import gkmasmod.downfall.relics.CBR_RollingSourceOfEnergy;
import gkmasmod.relics.RollingSourceOfEnergy;
import gkmasmod.utils.PlayerHelper;

public class RollingSourceOfEnergyAction extends AbstractGameAction {
    private AbstractCreature p;
    private int require;
    private int reward;

    public RollingSourceOfEnergyAction(AbstractCreature p, int require, int reward) {
        this.p = p;
        this.require = require;
        this.reward = reward;
    }

    public void update() {
        int count = PlayerHelper.getPowerAmount(p, DexterityPower.POWER_ID);
//        System.out.println("RollingSourceOfEnergyAction11: " + p.name + " has " + count + " Dexterity, require " + require + " Dexterity, reward " + reward);
        if(p.isPlayer){
            if(AbstractDungeon.player.getRelic(RollingSourceOfEnergy.ID).counter <= 0){
                this.isDone = true;
                return;
            }
        }
        else if(p instanceof AbstractCharBoss){
            if(AbstractCharBoss.boss.getRelic(CBR_RollingSourceOfEnergy.ID2).counter <= 0){
                this.isDone = true;
                return;
            }
        }
        if(count > require){
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, reward), reward));
            addToTop(new GainTrainRoundPowerAction(p,1));
        }
        else{
            this.isDone = true;
            return;
        }
        if(p.isPlayer){
            AbstractDungeon.player.getRelic(RollingSourceOfEnergy.ID).counter--;
            if(AbstractDungeon.player.getRelic(RollingSourceOfEnergy.ID).counter == 0){
                AbstractDungeon.player.getRelic(RollingSourceOfEnergy.ID).grayscale = true;
            }
        }
        else if(p instanceof AbstractCharBoss){
            AbstractCharBoss.boss.getRelic(CBR_RollingSourceOfEnergy.ID2).counter--;
            if(AbstractCharBoss.boss.getRelic(CBR_RollingSourceOfEnergy.ID2).counter == 0){
                AbstractCharBoss.boss.getRelic(CBR_RollingSourceOfEnergy.ID2).grayscale = true;
            }
        }
        this.isDone = true;
    }

}
