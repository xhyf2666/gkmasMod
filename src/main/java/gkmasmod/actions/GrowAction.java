package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import gkmasmod.utils.GrowHelper;

public class GrowAction extends AbstractGameAction {

    private String growID;
    private GrowType type;
    private int amount;

    private boolean forBoss = false;

    public GrowAction(String growID, GrowType type, int amount) {
        this(growID,type,amount,false);
    }

    public GrowAction(String growID, GrowType type, int amount,boolean forBoss) {
        this.growID = growID;
        this.type = type;
        this.amount = amount;
        this.forBoss = forBoss;
    }

    public void update() {

        if(forBoss){
            switch (this.type){
                case allHand:
                    GrowHelper.growAllHandEN(this.growID,this.amount);
                    break;
                case allDraw:
                case allDiscard:
                    GrowHelper.growAllDiscardEN(this.growID,this.amount);
                    break;
                case allTempSave:
                    GrowHelper.growAllTempSaveEN(this.growID,this.amount);
                    break;
                case all:
                    GrowHelper.growAllEN(this.growID,this.amount);
                    break;
            }
        }
        else{
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
