package gkmasmod.growEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class CanNotPlayGrow extends AbstractCardModifier {

    public CanNotPlayGrow() {
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return CardCrawlGame.languagePack.getUIString("growEffect:CanNotPlayGrow").TEXT[0] + " NL "+rawDescription;
    }

    public void onInitialApplication(AbstractCard card) {
        card.cost = -2;
        card.costForTurn = -2;
    }

    public boolean canPlayCard(AbstractCard card) {
        return false;
    }

    public void onRemove(AbstractCard card) {
        card.cost = 0;
        card.costForTurn = 0;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CanNotPlayGrow();
    }

}
