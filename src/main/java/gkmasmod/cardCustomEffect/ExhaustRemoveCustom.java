package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import gkmasmod.cards.GkmasCardTag;

public class ExhaustRemoveCustom extends AbstractCardCustomEffect {

    public static String growID = "ExhaustRemoveCustom";

    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString("customEffect:ExhaustRemoveCustom").TEXT;

    public boolean isExhausted = false;

    public ExhaustRemoveCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        if(this.isExhausted){
            if(rawDescription.contains(TEXT[0])) {
                int lastIndex = rawDescription.lastIndexOf(TEXT[0]);
                if (lastIndex != -1) {
                    rawDescription = rawDescription.substring(0, lastIndex) + rawDescription.substring(lastIndex + TEXT[0].length());
                }
            }
            if(card.tags.contains(GkmasCardTag.OUTSIDE_TAG) && rawDescription.contains(TEXT[1])) {
                int lastIndex = rawDescription.lastIndexOf(TEXT[1]);
                if (lastIndex != -1) {
                    rawDescription = rawDescription.substring(0, lastIndex) + rawDescription.substring(lastIndex + TEXT[1].length());
                }
            }
        }
        return rawDescription;
    }

    public void onInitialApplication(AbstractCard card) {
        this.isExhausted = card.exhaust;
        card.exhaust = false;
    }

    public void onRemove(AbstractCard card) {
        card.exhaust = this.isExhausted;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ExhaustRemoveCustom(this.amount);
    }

}
