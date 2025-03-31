package gkmasmod.downfall.charbosses.relics;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.MedicalKit;

public class CBR_MedicalKit extends AbstractCharbossRelic {
    public static final String ID = "CBRMedicalKit";

    public CBR_MedicalKit() {
        super(new MedicalKit());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.STATUS) {
            flash();
            card.exhaust = true;
            action.exhaustCard = true;
        }

    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_MedicalKit();
    }
}