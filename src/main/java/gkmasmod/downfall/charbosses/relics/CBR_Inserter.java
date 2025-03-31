package gkmasmod.downfall.charbosses.relics;

import gkmasmod.downfall.charbosses.actions.orb.EnemyIncreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Inserter;

public class CBR_Inserter extends AbstractCharbossRelic {
    public static final String ID = "Inserter";

    public CBR_Inserter() {
        super(new Inserter());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onEquip() {
        this.counter = 0;
    }

    public void atTurnStartPostDraw() { // Nasty hack so that the player can see the empty orb when planning their turn
        if (this.counter == -1) {
            this.counter += 2;
        } else {
            ++this.counter;
        }

        if (this.counter == 2) {
            this.counter = 0;
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(this.owner, this));
            this.addToBot(new EnemyIncreaseMaxOrbAction(1));
        }

    }

    public AbstractRelic makeCopy() {
        return new CBR_Inserter();
    }
}
