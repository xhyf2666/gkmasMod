package gkmasmod.downfall.charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Matryoshka;

public class CBR_Matroyshka extends AbstractCharbossRelic {
    public static final String ID = "Matroyshka";
    private int numRelics;


    public CBR_Matroyshka() {
        super(new Matryoshka());
        this.counter = 2;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Matroyshka();
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
