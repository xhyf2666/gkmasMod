package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class StageDeviceFlowAction extends AbstractGameAction {
    private float startingDuration;

    public StageDeviceFlowAction() {
        this.target = AbstractDungeon.player;
        this.actionType = ActionType.WAIT;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            int count = AbstractDungeon.player.hand.size();
            if (count >= 0) {
                this.addToTop(new DrawCardAction(this.target, count));
                this.addToTop(new DiscardAction(this.target, this.target, count+1, true));
            }

            this.isDone = true;
        }

    }
}