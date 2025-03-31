package gkmasmod.downfall.charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Omamori;

public class CBR_Omamori extends AbstractCharbossRelic {
    public static final String ID = "Omamori";
    private final String addedDesc = "";

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + this.addedDesc;
    }


    public CBR_Omamori() {
        super(new Omamori());
        this.counter = 0;
        this.usedUp();
        this.description = getUpdatedDescription();
        refreshDescription();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Omamori();
    }
}
