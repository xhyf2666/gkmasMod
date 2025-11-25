package gkmasmod.actions.relicAction;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.actions.common.GainTrainRoundPowerAction;
import gkmasmod.powers.GoodTune;
import gkmasmod.powers.GreatGoodTune;
import gkmasmod.utils.PlayerHelper;

public class DropWaterAction extends AbstractGameAction {
    private AbstractCreature p;
    private int num1;
    private int num2;
    AbstractRelic relic = null;

    public DropWaterAction(AbstractCreature p, int num1, int num2, AbstractRelic relic) {
        this.p = p;
        this.relic = relic;
        this.num1 = num1;
        this.num2 = num2;
    }

    public void update() {
        if(relic.counter <= 0){
            this.isDone = true;
            return;
        }
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, relic));
        int count = PlayerHelper.getPowerAmount(p, StrengthPower.POWER_ID);
        count = (int) (1.0F*count*this.num1/100);
        if(count>0){
            addToBot(new ApplyPowerAction(p, p, new GoodTune(p, count), count));
        }
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, num2), num2));
        relic.flash();
        relic.counter--;
        if(relic.counter == 0){
            relic.grayscale = true;
        }

        this.isDone = true;
    }

}
