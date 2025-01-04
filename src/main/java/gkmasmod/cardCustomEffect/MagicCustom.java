package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class MagicCustom extends AbstractCardCustomEffect {

    public static String growID = "MagicCustom";

    public MagicCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if(magic>0)
            return magic + this.amount;
        else
            return this.amount;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new MagicCustom(this.amount);
    }

}
