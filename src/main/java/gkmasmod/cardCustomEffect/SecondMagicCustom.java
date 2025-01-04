package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;

public class SecondMagicCustom extends AbstractCardCustomEffect {

    public static String growID = "SecondMagicCustom";

    public SecondMagicCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new SecondMagicCustom(this.amount);
    }

}
