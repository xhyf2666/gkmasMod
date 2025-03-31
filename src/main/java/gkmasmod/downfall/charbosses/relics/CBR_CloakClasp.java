package gkmasmod.downfall.charbosses.relics;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CloakClasp;

public class CBR_CloakClasp extends AbstractCharbossRelic {
    public static final String ID = "CloakClasp";

    public CBR_CloakClasp() {
        super(new CloakClasp());
    }


    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }

    public void onPlayerEndTurn() {
        if (!AbstractCharBoss.boss.hand.group.isEmpty()) {
            this.flash();
            this.addToBot(new GainBlockAction(AbstractCharBoss.boss, null, AbstractCharBoss.boss.hand.group.size()));
        }

    }


    public AbstractRelic makeCopy() {
        return new CloakClasp();
    }
}
