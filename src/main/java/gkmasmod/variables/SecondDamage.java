package gkmasmod.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import gkmasmod.cards.GkmasCard;


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
        return ((GkmasCard) card).isSecondDamageModified;

    }

    @Override
    public int value(AbstractCard card) {
        return ((GkmasCard) card).secondDamage;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((GkmasCard) card).baseSecondDamage;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((GkmasCard) card).upgradedSecondDamage;
    }
}