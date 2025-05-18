package gkmasmod.cardGrowEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class EnergyGrow extends AbstractGrowEffect {

    public static String growID = "EnergyGrow";

    public int originalCost;

    public EnergyGrow(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        if(this.amount>=0)
        return rawDescription + " " + String.format(CardCrawlGame.languagePack.getUIString("growEffect:EnergyGrow").TEXT[0], this.amount);
        else
            return rawDescription + " " + String.format(CardCrawlGame.languagePack.getUIString("growEffect:EnergyReduceGrow").TEXT[0], -this.amount);
    }

    public void onInitialApplication(AbstractCard card) {
        this.originalCost = card.cost;
        if(card.cost<0){
            return;
        }
        card.cost += this.amount;
        card.costForTurn = card.cost;
        if(card.cost<0){
            card.cost = 0;
            card.costForTurn = 0;
        }
        if(this.originalCost!=card.cost){
            card.isCostModified = true;
        }
    }

    public void reApply(AbstractCard card) {
        card.cost = this.originalCost + this.amount;
        card.costForTurn = card.cost;
        if(card.cost<0){
            card.cost = 0;
            card.costForTurn = 0;
        }
        if(this.originalCost!=card.cost){
            card.isCostModified = true;
        }
    }

    public void onRemove(AbstractCard card) {
        card.cost = this.originalCost;
        card.costForTurn = card.cost;
        card.isCostModified = false;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new EnergyGrow(this.amount);
    }

}
