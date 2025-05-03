package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.growEffect.BlockGrow;
import gkmasmod.growEffect.DamageGrow;
import gkmasmod.powers.EndOfTurnPreservationStancePower;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.relics.DestinyEncounter;
import gkmasmod.relics.FrogFan;
import gkmasmod.stances.FullPowerStance;

public class DestinyEncounterAction extends AbstractGameAction {
    AbstractCreature owner;
    int magic1 = 0;
    int magic2 = 0;
    int magic3 = 0;
    AbstractRelic relic = null;

    public DestinyEncounterAction(AbstractCreature owner, int magic1, int magic2,int magic3, AbstractRelic relic) {
        this.owner = owner;
        this.magic1 = magic1;
        this.magic2 = magic2;
        this.magic3 = magic3;
        this.relic = relic;
    }

    public void update() {
        if(this.owner.isPlayer){
            if(!AbstractDungeon.player.stance.ID.equals(FullPowerStance.STANCE_ID)){
                this.isDone = true;
                return;
            }
        }

        if(relic.counter <= 0){
            this.isDone = true;
            return;
        }
        addToBot(new GrowAction(DamageGrow.growID, GrowAction.GrowType.all,this.amount));
        addToBot(new GainBlockWithPowerAction(this.owner,magic1));
        addToBot(new ApplyPowerAction(this.owner, this.owner, new FullPowerValue(this.owner, magic2), magic2));
        addToBot(new GrowAction(DamageGrow.growID, GrowAction.GrowType.allHand,magic3));
        addToBot(new ApplyPowerAction(this.owner, this.owner, new EndOfTurnPreservationStancePower(this.owner, 1), 1));
        relic.flash();
        relic.counter--;
        if(relic.counter == 0){
            relic.grayscale = true;
        }
        this.isDone = true;
        return;
    }
}