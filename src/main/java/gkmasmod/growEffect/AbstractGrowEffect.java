package gkmasmod.growEffect;

import basemod.abstracts.AbstractCardModifier;

public class AbstractGrowEffect extends AbstractCardModifier {
    @Override
    public AbstractCardModifier makeCopy() {
        return null;
    }

    public String growEffectID = "";

    public int amount;

    public void changeAmount(int change) {
        this.amount += change;
    }

    public int getAmount() {
        return this.amount;
    }
}
