package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CostCustom extends AbstractCardCustomEffect {

    public static String growID = "CostCustom";

    public int originalCost;

    public CostCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public void onInitialApplication(AbstractCard card) {
        this.originalCost = card.cost;
        card.cost += this.amount;
        card.costForTurn = card.cost;
        if(card.cost<0){
            card.cost = 0;
            card.costForTurn = 0;
        }
    }

    public void onRemove(AbstractCard card) {
        card.cost = this.originalCost;
        card.costForTurn = card.cost;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CostCustom(this.amount);
    }

}
