package gkmasmod.actions.relicAction;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.powers.increaseModifyPower.EnthusiasticAddPower;
import gkmasmod.stances.PreservationStance;

public class AfterRainRainbowAction extends AbstractGameAction {
    private AbstractCreature p;
    private int add;
    AbstractRelic relic = null;

    public AfterRainRainbowAction(AbstractCreature p, int add,AbstractRelic relic) {
        this.p = p;
        this.add = add;
        this.relic = relic;
    }

    public void update() {
        if(relic.counter <= 0){
            this.isDone = true;
            return;
        }
        if(p.isPlayer){
            if(!AbstractDungeon.player.stance.ID.equals(PreservationStance.STANCE_ID)){
                this.isDone = true;
                return;
            }
            int stage = ((PreservationStance)(AbstractDungeon.player.stance)).stage;
            if(stage<=0){
                this.isDone = true;
                return;
            }
        }
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, relic));
        addToBot(new ApplyPowerAction(this.p,this.p,new EnthusiasticAddPower(this.p, add), add));
        relic.flash();
        relic.counter--;
        if(relic.counter == 0){
            relic.grayscale = true;
        }
        this.isDone = true;
    }

}
