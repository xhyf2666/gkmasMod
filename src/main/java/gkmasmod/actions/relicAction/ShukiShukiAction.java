package gkmasmod.actions.relicAction;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.actions.common.GainBlockWithPowerAction;
import gkmasmod.actions.common.GainTrainRoundPowerAction;
import gkmasmod.powers.BurstSkillPower;
import gkmasmod.utils.PlayerHelper;

public class ShukiShukiAction extends AbstractGameAction {
    private AbstractCreature p;
    private int require;
    private int block;
    AbstractRelic relic = null;

    public ShukiShukiAction(AbstractCreature p, int require, int block,
                            AbstractRelic relic) {
        this.p = p;
        this.require = require;
        this.block = block;
        this.relic = relic;
    }

    public void update() {
        int count = PlayerHelper.getPowerAmount(p, StrengthPower.POWER_ID);
        if(count < this.require){
            this.isDone = true;
            return;
        }
        if(relic.counter <= 0){
            this.isDone = true;
            return;
        }
        relic.flash();
        relic.counter--;
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, relic));
        addToBot(new DrawCardAction(1));
        addToBot(new GainTrainRoundPowerAction(p,1));
        addToBot(new GainBlockWithPowerAction(p,block));
        addToBot(new ApplyPowerAction(p,p,new BurstSkillPower(p,1),1));
        if(relic.counter == 0){
            relic.grayscale = true;
        }

        this.isDone = true;
    }

}
