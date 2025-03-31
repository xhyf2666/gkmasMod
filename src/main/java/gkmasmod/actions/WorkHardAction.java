package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.growEffect.BlockGrow;
import gkmasmod.growEffect.DamageGrow;
import gkmasmod.stances.FullPowerStance;
import gkmasmod.vfx.effect.GakuenLinkMasterEffect;

public class WorkHardAction extends AbstractGameAction {
    AbstractCreature owner;
    Boolean isSP = false;
    int amount = 0;

    public WorkHardAction(AbstractCreature owner,int amount, Boolean isSP) {
        this.owner = owner;
        this.amount = amount;
        this.isSP = isSP;
    }

    public void update() {
        if(this.isSP){
            if(!AbstractDungeon.player.stance.ID.equals(FullPowerStance.STANCE_ID)){
                this.isDone = true;
                return;
            }
            addToBot(new GrowAction(DamageGrow.growID, GrowAction.GrowType.all,this.amount));
            addToBot(new GrowAction(BlockGrow.growID, GrowAction.GrowType.all,this.amount));
        }
        else{
            if(!AbstractDungeon.player.stance.ID.equals(FullPowerStance.STANCE_ID)){
                this.isDone = true;
                return;
            }
            addToBot(new GrowAction(DamageGrow.growID, GrowAction.GrowType.all,this.amount));
        }
        this.isDone = true;
        return;
    }
}