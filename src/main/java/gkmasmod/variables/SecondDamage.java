package gkmasmod.variables;

import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.DynamicVariable;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import gkmasmod.cardCustomEffect.SecondDamageCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.downfall.cards.GkmasBossCard;


public class SecondDamage extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return ("D2");
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "theDefault:" (or, your id) before the name.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if(card instanceof GkmasCard)
            return ((GkmasCard) card).isSecondDamageModified;
        else if(card instanceof GkmasBossCard)
            return ((GkmasBossCard) card).isSecondDamageModified;
        return false;

    }

    @Override
    public int modifiedBaseValue(AbstractCard card) {
        int base = baseValue(card);
        if (card instanceof GkmasCard) {
            for(AbstractCardModifier mod : CardModifierManager.modifiers(card)) {
                if(mod instanceof AbstractCardModifier && mod instanceof SecondDamageCustom) {
                    base += ((SecondDamageCustom) mod).amount;
                }
            }
        }
        return base;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof GkmasCard)
            return ((GkmasCard) card).secondDamage;
        else if (card instanceof GkmasBossCard)
            return ((GkmasBossCard) card).secondDamage;
        return ((GkmasCard) card).secondDamage;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof GkmasCard)
            return ((GkmasCard) card).baseSecondDamage;
        else if (card instanceof GkmasBossCard)
            return ((GkmasBossCard) card).baseSecondDamage;
        return ((GkmasCard) card).baseSecondDamage;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof GkmasCard)
            return ((GkmasCard) card).upgradedSecondDamage;
        else if (card instanceof GkmasBossCard)
            return ((GkmasBossCard) card).upgradedSecondDamage;
        return ((GkmasCard) card).upgradedSecondDamage;
    }
}