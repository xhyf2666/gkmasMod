package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class CheerfulHandkerchiefAction extends AbstractGameAction {
    private AbstractCreature p;
    private AbstractCreature m;
    private float rate;
    private int HP_COST;

    public CheerfulHandkerchiefAction(AbstractCreature p,AbstractCreature m, float rate,int HP_COST) {
        this.p = p;
        this.m = m;
        this.rate = rate;
        this.HP_COST = HP_COST;
    }

    public void update() {
        int damage = (int) (this.p.currentBlock*1.0F*this.rate);
        addToBot(new LoseHPAction(this.p, this.p, HP_COST));
        addToBot(new ModifyDamageAction(this.m, new DamageInfo(this.p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        this.isDone = true;
    }

}
