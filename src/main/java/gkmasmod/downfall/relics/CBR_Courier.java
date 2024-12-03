package gkmasmod.downfall.relics;

import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Courier;

public class CBR_Courier extends AbstractCharbossRelic {
    public static final String ID = "The Courier";

    public CBR_Courier() {
        super(new Courier());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Courier();
    }
}
