package gkmasmod.downfall.charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PeacePipe;

public class CBR_PeacePipe extends AbstractCharbossRelic {
    public static final String ID = "PeacePipe";
    private int numCards;

    public CBR_PeacePipe() {
        super(new PeacePipe());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_PeacePipe();
    }
}
