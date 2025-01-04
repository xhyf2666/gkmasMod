package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class SelfRetainCustom extends AbstractCardCustomEffect {

    public static String growID = "SelfRetainCustom";

    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString("customEffect:SelfRetainCustom").TEXT;

    public boolean selfRetain = false;

    public SelfRetainCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " "+TEXT[0];
    }

    public void onInitialApplication(AbstractCard card) {
        this.selfRetain = card.selfRetain;
        card.selfRetain = true;
    }

    public void onRemove(AbstractCard card) {
        card.selfRetain = this.selfRetain;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new SelfRetainCustom(this.amount);
    }

}
