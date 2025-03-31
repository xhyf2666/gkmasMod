package gkmasmod.downfall.charbosses.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.relics.Abacus;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class CBR_Abacus extends AbstractCharbossRelic {
    public static final String ID = "Abacus";

    public CBR_Abacus() {
        super(new Abacus());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 6 + this.DESCRIPTIONS[1];
    }


    public void onShuffle() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(this.owner, this));
        this.addToBot(new GainBlockAction(this.owner, this.owner, 6));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Abacus();
    }
}
