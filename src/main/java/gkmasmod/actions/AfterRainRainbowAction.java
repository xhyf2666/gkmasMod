package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.relics.CBR_FrogFan;
import gkmasmod.powers.EnthusiasticAddPower;
import gkmasmod.relics.AfterRainRainbow;
import gkmasmod.relics.FrogFan;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.PlayerHelper;

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
            if(stage!=1){
                this.isDone = true;
                return;
            }
        }
        addToBot(new ApplyPowerAction(this.p,this.p,new EnthusiasticAddPower(this.p, add), add));

        relic.flash();
        relic.counter--;
        if(relic.counter == 0){
            relic.grayscale = true;
        }
        this.isDone = true;
    }

}
