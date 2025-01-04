package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;

public class ThirdMagicCustom extends AbstractCardCustomEffect {

    public static String growID = "ThirdMagicCustom";

    public ThirdMagicCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ThirdMagicCustom(this.amount);
    }

}
