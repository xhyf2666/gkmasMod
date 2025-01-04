package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;

public class AbstractCardCustomEffect extends AbstractCardModifier {
    @Override
    public AbstractCardModifier makeCopy() {
        return null;
    }

    public String growEffectID = "";

    public String title = "";

    public int amount;

    public int price;

    public void changeAmount(int change) {
        this.amount += change;
    }

    public int getAmount() {
        return this.amount;
    }

}
