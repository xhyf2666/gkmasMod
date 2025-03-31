package gkmasmod.downfall.charbosses.relics;

import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Pear;

public class CBR_Pear extends AbstractCharbossRelic {
    public static final String ID = "Pear";

    public CBR_Pear() {
        super(new Pear());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 10 + LocalizedStrings.PERIOD;
    }

    @Override
    public void onEquip() {
        this.owner.increaseMaxHp(10, true);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Pear();
    }
}