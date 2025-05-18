package gkmasmod.cardGrowEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class EtherealGrow extends AbstractGrowEffect {

    public static String growID = "EtherealGrow";

    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString("growEffect:EtherealGrow").TEXT;

    public boolean isEthereal = false;

    public EtherealGrow(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " " + TEXT[0];
    }

    public void onInitialApplication(AbstractCard card) {
        this.isEthereal = card.isEthereal;
        card.isEthereal = true;
    }

    public void onRemove(AbstractCard card) {
        card.isEthereal = this.isEthereal;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new EtherealGrow(this.amount);
    }

}
