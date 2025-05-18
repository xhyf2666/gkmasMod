package gkmasmod.cardGrowEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class TempOutAutoPlayGrow extends AbstractGrowEffect {

    public static String growID = "TempOutAutoPlayGrow";

    public TempOutAutoPlayGrow(int time) {
        this.amount = time;
        growEffectID = growID;
        priority = 20;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " NL " + CardCrawlGame.languagePack.getUIString("growEffect:TempOutAutoPlayGrow").TEXT[0];
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new TempOutAutoPlayGrow(this.amount);
    }

}
