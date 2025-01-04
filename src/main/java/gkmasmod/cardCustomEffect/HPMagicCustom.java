package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;

public class HPMagicCustom extends AbstractCardCustomEffect {

    public static String growID = "HPMagicCustom";

    public HPMagicCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new HPMagicCustom(this.amount);
    }

}
