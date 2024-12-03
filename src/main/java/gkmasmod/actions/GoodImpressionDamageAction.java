package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import gkmasmod.powers.GoodImpression;
import gkmasmod.utils.PlayerHelper;

public class GoodImpressionDamageAction extends AbstractGameAction {
    private AbstractCreature m;

    private AbstractCreature p;


    private int goodImpressionAdd;

    private float rate;

    private AbstractCard card;

    private float reduceRate=0.0f;

    public GoodImpressionDamageAction(float rate,int goodImpressionAdd,AbstractCreature p, AbstractCreature m) {
        this.m = m;
        this.p = p;
        this.goodImpressionAdd = goodImpressionAdd;
        this.rate = rate;
        this.card = null;
    }

    public GoodImpressionDamageAction(float rate,int goodImpressionAdd,AbstractCreature p, AbstractCreature m, AbstractCard card) {
        this.m = m;
        this.p = p;
        this.goodImpressionAdd = goodImpressionAdd;
        this.rate = rate;
        this.card = card;
    }

    public GoodImpressionDamageAction(float rate,int goodImpressionAdd,AbstractCreature p, AbstractCreature m, AbstractCard card,float reduceRate) {
        this.m = m;
        this.p = p;
        this.goodImpressionAdd = goodImpressionAdd;
        this.rate = rate;
        this.card = card;
        this.reduceRate = reduceRate;
    }



    public void update() {
        int count = PlayerHelper.getPowerAmount(p, GoodImpression.POWER_ID);
        count+=this.goodImpressionAdd;
        int damage = (int)(this.rate*(count));
        if(goodImpressionAdd>0){
            addToBot(new ApplyPowerAction(p, p, new GoodImpression(p,this.goodImpressionAdd), this.goodImpressionAdd));
        }
        if(this.reduceRate>0){
            int reduce = (int)(this.reduceRate*(count));
            addToBot(new ReducePowerAction(p,p,GoodImpression.POWER_ID,reduce));
        }
        addToTop(new ModifyDamageAction(m, new DamageInfo(p,damage , DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL,this.card));
        this.isDone = true;
    }

}
