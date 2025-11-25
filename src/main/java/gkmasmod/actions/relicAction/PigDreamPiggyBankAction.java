package gkmasmod.actions.relicAction;


import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.actions.common.GainTrainRoundPowerAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import gkmasmod.powers.GoodImpression;
import gkmasmod.utils.PlayerHelper;

public class PigDreamPiggyBankAction extends AbstractGameAction {
    private AbstractCreature p;
    private int require;
    private int reward;
    AbstractRelic relic = null;

    public PigDreamPiggyBankAction(AbstractCreature p, int require, int reward,AbstractRelic relic) {
        this.p = p;
        this.require = require;
        this.reward = reward;
        this.relic = relic;
    }

    public void update() {
        int count = PlayerHelper.getPowerAmount(p, GoodImpression.POWER_ID);
        if(relic.counter <= 0){
            this.isDone = true;
            return;
        }
        if(count > require){
            addToBot(new ApplyPowerAction(p, p, new GoodImpression(p, reward), reward));
            addToBot(new GainTrainRoundPowerAction(p,1));
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
