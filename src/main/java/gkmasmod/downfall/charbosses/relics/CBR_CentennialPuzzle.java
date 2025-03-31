package gkmasmod.downfall.charbosses.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CentennialPuzzle;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class CBR_CentennialPuzzle extends AbstractCharbossRelic {
    public static final String ID = "CentennialPuzzle";

    public CBR_CentennialPuzzle() {
        super(new CentennialPuzzle());
    }

    private static boolean usedThisCombat = false;


    public void atPreBattle() {
        usedThisCombat = false;
        this.pulse = true;
        this.beginPulse();
    }

    public void wasHPLost(int damageAmount) {
        if (!AbstractDungeon.actionManager.turnHasEnded) {
            if (damageAmount > 0 && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !usedThisCombat) {
                this.flash();
                this.pulse = false;
                //this.addToTop(new EnemyDrawCardAction(this.owner, 3));
                this.addToTop(new RelicAboveCreatureAction(this.owner, this));
                //this.addToBot(new CharbossSortHandAction());
                usedThisCombat = true;
                this.grayscale = true;
            }
        }

    }

    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    public void onVictory() {
        this.pulse = false;
    }


    @Override
    public AbstractRelic makeCopy() {
        return new CBR_CentennialPuzzle();
    }

}
