package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;

public class EffectReduceCustom extends AbstractCardCustomEffect {

    public static String growID = "EffectReduceCustom";

    public EffectReduceCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new EffectReduceCustom(this.amount);
    }

}
