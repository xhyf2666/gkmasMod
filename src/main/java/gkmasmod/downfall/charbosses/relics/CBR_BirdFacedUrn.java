package gkmasmod.downfall.charbosses.relics;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BirdFacedUrn;

public class CBR_BirdFacedUrn extends AbstractCharbossRelic {
    public static final String ID = "BirdFacedUrn";

    public CBR_BirdFacedUrn() {
        super(new BirdFacedUrn());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 2 + this.DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_BirdFacedUrn();
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER) {
            this.flash();
            this.addToTop(new HealAction(this.owner, this.owner, 2));
            this.addToTop(new RelicAboveCreatureAction(this.owner, this));
        }
    }
}
