package gkmasmod.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import gkmasmod.cards.GkmasCard;
import gkmasmod.downfall.cards.GkmasBossCard;


public class SecondBlock extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return ("B2");
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "theDefault:" (or, your id) before the name.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if(card instanceof GkmasCard)
            return ((GkmasCard) card).isSecondBlockModified;
        else if(card instanceof GkmasBossCard)
            return ((GkmasBossCard) card).isSecondBlockModified;
        return false;

    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof GkmasCard)
            return ((GkmasCard) card).secondBlock;
        else if (card instanceof GkmasBossCard)
            return ((GkmasBossCard) card).secondBlock;
        return ((GkmasCard) card).secondBlock;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof GkmasCard)
            return ((GkmasCard) card).baseSecondBlock;
        else if (card instanceof GkmasBossCard)
            return ((GkmasBossCard) card).baseSecondBlock;
        return ((GkmasCard) card).baseSecondBlock;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof GkmasCard)
            return ((GkmasCard) card).upgradedSecondBlock;
        else if (card instanceof GkmasBossCard)
            return ((GkmasBossCard) card).upgradedSecondBlock;
        return ((GkmasCard) card).upgradedSecondBlock;
    }
}