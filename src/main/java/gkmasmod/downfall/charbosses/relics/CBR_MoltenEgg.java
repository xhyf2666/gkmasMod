package gkmasmod.downfall.charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.MoltenEgg2;

public class CBR_MoltenEgg extends AbstractCharbossRelic {
    public static final String ID = "MoltenEgg";
    int numCards = 0;


    public CBR_MoltenEgg() {
        super(new MoltenEgg2());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_MoltenEgg();
    }
}
