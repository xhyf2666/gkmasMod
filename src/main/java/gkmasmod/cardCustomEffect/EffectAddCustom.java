package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;

public class EffectAddCustom extends AbstractCardCustomEffect {

    public static String growID = "EffectAddCustom";

    public EffectAddCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new EffectAddCustom(this.amount);
    }

}
