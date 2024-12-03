package gkmasmod.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import gkmasmod.cards.GkmasCard;
import gkmasmod.downfall.cards.GkmasBossCard;


public class GrowMagicNumber extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return ("GROW");
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "theDefault:" (or, your id) before the name.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if(card instanceof GkmasCard)
            return ((GkmasCard) card).isGrowMagicNumberModified;
        else if(card instanceof GkmasBossCard)
            return ((GkmasBossCard) card).isGrowMagicNumberModified;
        return false;

    }

    @Override
    public int value(AbstractCard card) {
        if(card instanceof GkmasCard)
            return ((GkmasCard) card).growMagicNumber;
        else if(card instanceof GkmasBossCard)
            return ((GkmasBossCard) card).growMagicNumber;
        return ((GkmasCard) card).growMagicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof GkmasCard)
            return ((GkmasCard) card).baseGrowMagicNumber;
        else if (card instanceof GkmasBossCard)
            return ((GkmasBossCard) card).baseGrowMagicNumber;
        return ((GkmasCard) card).baseGrowMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof GkmasCard)
            return ((GkmasCard) card).upgradedGrowMagicNumber;
        else if (card instanceof GkmasBossCard)
            return ((GkmasBossCard) card).upgradedGrowMagicNumber;
        return ((GkmasCard) card).upgradedGrowMagicNumber;
    }
}