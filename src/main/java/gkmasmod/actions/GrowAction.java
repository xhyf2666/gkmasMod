package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.growEffect.BlockGrow;
import gkmasmod.growEffect.DamageGrow;
import gkmasmod.powers.TrainRoundAnomalyPower;
import gkmasmod.utils.GrowHelper;

public class GrowAction extends AbstractGameAction {

    private String growID;
    private GrowType type;
    private int amount;

    public GrowAction(String growID, GrowType type, int amount) {
        this.growID = growID;
        this.type = type;
        this.amount = amount;
    }

    public void update() {

        switch (this.type){
            case allHand:
                GrowHelper.growAllHand(this.growID,this.amount);
                break;
            case allDraw:
                GrowHelper.growAllDraw(this.growID,this.amount);
                break;
            case allDiscard:
                GrowHelper.growAllDiscard(this.growID,this.amount);
                break;
            case allExhaust:
                GrowHelper.growAllExhaust(this.growID,this.amount);
                break;
            case allTempSave:
                GrowHelper.growAllTempSave(this.growID,this.amount);
                break;
            case all:
                GrowHelper.growAll(this.growID,this.amount);
                break;
        }
        this.isDone = true;
    }

    public enum GrowType{
        allHand,
        allDraw,
        allDiscard,
        allExhaust,
        allTempSave,
        all
    }

}
