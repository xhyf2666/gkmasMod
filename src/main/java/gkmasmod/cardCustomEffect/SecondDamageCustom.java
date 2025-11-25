package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;

public class SecondDamageCustom extends AbstractCardCustomEffect {

    public static String growID = "SecondDamageCustom";

    public SecondDamageCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new SecondDamageCustom(this.amount);
    }

}
