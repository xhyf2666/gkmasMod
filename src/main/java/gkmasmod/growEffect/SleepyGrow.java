package gkmasmod.growEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import gkmasmod.cards.GkmasCardTag;

public class SleepyGrow extends AbstractGrowEffect {

    public static String growID = "SleepyGrow";

    public SleepyGrow(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(GkmasCardTag.SLEEP_TAG);
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " " + CardCrawlGame.languagePack.getUIString("growEffect:SleepyGrow").TEXT[0];
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.tags.remove(GkmasCardTag.SLEEP_TAG);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new SleepyGrow(this.amount);
    }

}
