package gkmasmod.growEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

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
            return rawDescription + " " + String.format(CardCrawlGame.languagePack.getUIString("growEffect:EnergyReduceGrow").TEXT[0], this.amount);
    }

    public void onInitialApplication(AbstractCard card) {
        this.originalCost = card.cost;
        card.cost += this.amount;
        if(card.cost<0){
            card.cost = 0;
        }
    }

    public void onRemove(AbstractCard card) {
        card.cost = this.originalCost;
        card.costForTurn = card.cost;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new EnergyGrow(this.amount);
    }

}
