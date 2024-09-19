package gkmasmod.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import gkmasmod.cards.GkmasCard;


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
        return ((GkmasCard) card).isHPMagicNumberModified;

    }

    @Override
    public int value(AbstractCard card) {
        return ((GkmasCard) card).HPMagicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((GkmasCard) card).baseHPMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((GkmasCard) card).upgradedHPMagicNumber;
    }
}