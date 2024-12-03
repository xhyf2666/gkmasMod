package gkmasmod.growEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class AttackTimeGrow extends AbstractGrowEffect {

    public static String growID = "AttackTimeGrow";

    public AttackTimeGrow(int time) {
        this.amount = time;
        growEffectID = growID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " " + String.format(CardCrawlGame.languagePack.getUIString("growEffect:AttackTimeGrow").TEXT[0], this.amount);
    }

    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if(card.damage<0)
            return;
        for (int i = 0; i < this.amount; i++) {
            addToBot(new DamageAction(target, new DamageInfo(target, card.damage)));
        }

    }


    @Override
    public AbstractCardModifier makeCopy() {
        return new AttackTimeGrow(this.amount);
    }

}
