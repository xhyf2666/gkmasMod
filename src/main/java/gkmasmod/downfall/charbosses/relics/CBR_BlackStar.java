package gkmasmod.downfall.charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BlackStar;

public class CBR_BlackStar extends AbstractCharbossRelic {
    public static final String ID = "BlackStar";
    private int numRelics;

    public CBR_BlackStar() {
        super(new BlackStar());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public AbstractRelic makeCopy() {
        return new CBR_BlackStar();
    }
}
