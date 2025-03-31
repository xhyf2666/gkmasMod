package gkmasmod.downfall.charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.DreamCatcher;

public class CBR_DreamCatcher extends AbstractCharbossRelic {
    public static final String ID = "DreamCatcher";
    private int numCards;

    public CBR_DreamCatcher() {
        super(new DreamCatcher());
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_DreamCatcher();
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

}
