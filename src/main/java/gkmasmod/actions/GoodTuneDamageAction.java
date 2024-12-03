package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import gkmasmod.powers.GoodTune;
import gkmasmod.utils.PlayerHelper;

public class GoodTuneDamageAction extends AbstractGameAction {
    private AbstractCreature m;

    private AbstractCreature p;

    private int goodTuneAdd;

    private float rate;


    private boolean damageAll;

    private AbstractCard card;

    public GoodTuneDamageAction(float rate, int goodTuneAdd, AbstractCreature p, AbstractCreature m, AbstractCard card) {
        this(rate, goodTuneAdd, p, m,card, false);
    }

    public GoodTuneDamageAction(float rate, int goodTuneAdd, AbstractCreature p, AbstractCreature m, AbstractCard card, boolean damageAll) {
        this.m = m;
        this.p = p;
        this.goodTuneAdd = goodTuneAdd;
        this.rate = rate;
        this.damageAll = damageAll;
        this.card = card;
    }

    public void update() {
        int count = PlayerHelper.getPowerAmount(p, GoodTune.POWER_ID);
        count += goodTuneAdd;
        int damage = (int)(this.rate*count);
        if(this.goodTuneAdd > 0){
            addToBot(new ApplyPowerAction(p, p, new GoodTune(p, goodTuneAdd), goodTuneAdd));
        }
        if(damageAll){
            addToTop(new ModifyDamageAllEnemyAction(damage, AttackEffect.SLASH_HORIZONTAL,this.card));
        }
        else{
            addToTop(new ModifyDamageAction(m, new DamageInfo(p, damage , DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_HORIZONTAL,this.card));
        }
        this.isDone = true;
    }
}
