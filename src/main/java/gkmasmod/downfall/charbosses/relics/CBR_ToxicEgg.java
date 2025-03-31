package gkmasmod.downfall.charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.ToxicEgg2;

public class CBR_ToxicEgg extends AbstractCharbossRelic {
    public static final String ID = "ToxicEgg";
    int numCards = 0;


    public CBR_ToxicEgg() {
        super(new ToxicEgg2());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_ToxicEgg();
    }
}
