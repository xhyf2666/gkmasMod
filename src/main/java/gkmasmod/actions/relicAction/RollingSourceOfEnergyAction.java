package gkmasmod.actions.relicAction;


import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.actions.common.GainTrainRoundPowerAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.DexterityPower;
import gkmasmod.utils.PlayerHelper;

public class RollingSourceOfEnergyAction extends AbstractGameAction {
    private AbstractCreature p;
    private int require;
    private int reward;
    AbstractRelic relic = null;

    public RollingSourceOfEnergyAction(AbstractCreature p, int require, int reward,AbstractRelic relic) {
        this.p = p;
        this.require = require;
        this.reward = reward;
        this.relic = relic;
    }

    public void update() {
        int count = PlayerHelper.getPowerAmount(p, DexterityPower.POWER_ID);
        if(relic.counter <= 0){
            this.isDone = true;
            return;
        }
        if(count > require){
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, reward), reward));
            addToTop(new GainTrainRoundPowerAction(p,1));
        }
        else{
            this.isDone = true;
            return;
        }
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, relic));
        relic.flash();
        relic.counter--;
        if(relic.counter == 0){
            relic.grayscale = true;
        }
        this.isDone = true;
    }

}
