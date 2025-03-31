package gkmasmod.downfall.charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.SpiritPoop;

public class CBR_SpiritPoop extends AbstractCharbossRelic {
    public static final String ID = "SpiritPoop";

    public CBR_SpiritPoop() {
        super(new SpiritPoop());
        this.tier = RelicTier.SPECIAL;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_SpiritPoop();
    }
}
