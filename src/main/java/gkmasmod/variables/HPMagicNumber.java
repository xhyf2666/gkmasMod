package gkmasmod.variables;

import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.DynamicVariable;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import gkmasmod.cardCustomEffect.HPMagicCustom;
import gkmasmod.cardCustomEffect.SecondMagicCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.downfall.cards.GkmasBossCard;


public class HPMagicNumber extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return ("HP");
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "theDefault:" (or, your id) before the name.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if(card instanceof GkmasCard)
            return ((GkmasCard) card).isHPMagicNumberModified;
        else if(card instanceof GkmasBossCard)
            return ((GkmasBossCard) card).isHPMagicNumberModified;
        return false;

    }

    public int modifiedBaseValue(AbstractCard card) {
        int base = baseValue(card);
        if (card instanceof GkmasCard) {
            for(AbstractCardModifier mod : CardModifierManager.modifiers(card)) {
                if(mod instanceof AbstractCardModifier && mod instanceof HPMagicCustom) {
                    base += ((HPMagicCustom) mod).amount;
                }
            }
        }
        return base;
    }

    @Override
    public int value(AbstractCard card) {
        if(card instanceof GkmasCard)
            return ((GkmasCard) card).HPMagicNumber;
        else if(card instanceof GkmasBossCard)
            return ((GkmasBossCard) card).HPMagicNumber;
        return ((GkmasCard) card).HPMagicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof GkmasCard)
            return ((GkmasCard) card).baseHPMagicNumber;
        else if (card instanceof GkmasBossCard)
            return ((GkmasBossCard) card).baseHPMagicNumber;
        return ((GkmasCard) card).baseHPMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof GkmasCard)
            return ((GkmasCard) card).upgradedHPMagicNumber;
        else if (card instanceof GkmasBossCard)
            return ((GkmasBossCard) card).upgradedHPMagicNumber;
        return ((GkmasCard) card).upgradedHPMagicNumber;
    }
}