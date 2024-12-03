package gkmasmod.downfall.relics;

import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.RegalPillow;

public class CBR_RegalPillow extends AbstractCharbossRelic {
    public static final String ID = "RegalPillow";

    public CBR_RegalPillow() {
        super(new RegalPillow());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + '\017' + this.DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_RegalPillow();
    }
}
