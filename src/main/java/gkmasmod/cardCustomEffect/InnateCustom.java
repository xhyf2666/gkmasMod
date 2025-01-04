package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import gkmasmod.cards.GkmasCardTag;

public class InnateCustom extends AbstractCardCustomEffect {

    public static String growID = "InnateCustom";

    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString("customEffect:InnateCustom").TEXT;

    public boolean isInnate = false;

    public InnateCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " "+TEXT[0];
    }

    public void onInitialApplication(AbstractCard card) {
        this.isInnate = card.isInnate;
        card.isInnate = true;
    }

    public void onRemove(AbstractCard card) {
        card.exhaust = this.isInnate;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new InnateCustom(this.amount);
    }

}
