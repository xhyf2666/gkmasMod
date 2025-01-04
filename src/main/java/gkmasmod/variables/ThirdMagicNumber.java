package gkmasmod.variables;

import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.DynamicVariable;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import gkmasmod.cardCustomEffect.SecondMagicCustom;
import gkmasmod.cardCustomEffect.ThirdMagicCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.downfall.cards.GkmasBossCard;


public class ThirdMagicNumber extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return ("M3");
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "theDefault:" (or, your id) before the name.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if(card instanceof GkmasCard)
            return ((GkmasCard) card).isThirdMagicNumberModified;
        else if(card instanceof GkmasBossCard)
            return ((GkmasBossCard) card).isThirdMagicNumberModified;
        return false;

    }

    public int modifiedBaseValue(AbstractCard card) {
        int base = baseValue(card);
        if (card instanceof GkmasCard) {
            for(AbstractCardModifier mod : CardModifierManager.modifiers(card)) {
                if(mod instanceof AbstractCardModifier && mod instanceof ThirdMagicCustom) {
                    base += ((ThirdMagicCustom) mod).amount;
                }
            }
        }
        return base;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof GkmasCard)
            return ((GkmasCard) card).thirdMagicNumber;
        else if (card instanceof GkmasBossCard)
            return ((GkmasBossCard) card).thirdMagicNumber;
        return ((GkmasCard) card).thirdMagicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof GkmasCard)
            return ((GkmasCard) card).baseThirdMagicNumber;
        else if (card instanceof GkmasBossCard)
            return ((GkmasBossCard) card).baseThirdMagicNumber;
        return ((GkmasCard) card).baseThirdMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof GkmasCard)
            return ((GkmasCard) card).upgradedThirdMagicNumber;
        else if (card instanceof GkmasBossCard)
            return ((GkmasBossCard) card).upgradedThirdMagicNumber;
        return ((GkmasCard) card).upgradedThirdMagicNumber;
    }
}