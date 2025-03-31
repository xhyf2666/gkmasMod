package gkmasmod.downfall.charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Shovel;

public class CBR_Shovel extends AbstractCharbossRelic {
    public static final String ID = "Shovel";
    private int numRelics;

    public CBR_Shovel() {
        super(new Shovel());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Shovel();
    }
}
