package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.DexterityPower;
import gkmasmod.utils.PlayerHelper;

public class DexterityPowerDamageAction extends AbstractGameAction {
    private AbstractCreature m;

    private AbstractCreature p;


    private int dexterityPowerAdd;

    private float rate;

    private AbstractCard card;

    public DexterityPowerDamageAction(float rate, int dexterityPowerAdd, AbstractCreature p, AbstractCreature m, AbstractCard card) {
        this.m = m;
        this.p = p;
        this.dexterityPowerAdd = dexterityPowerAdd;
        this.rate = rate;
        this.card = card;
    }

    public void update() {
        int count = PlayerHelper.getPowerAmount(p, DexterityPower.POWER_ID);
        int damage = (int)(this.rate*(count+dexterityPowerAdd));
        if(this.dexterityPowerAdd>0)
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p,this.dexterityPowerAdd), this.dexterityPowerAdd));
        addToTop(new ModifyDamageAction(m, new DamageInfo(p,damage , DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_HORIZONTAL,this.card));

        this.isDone = true;
    }
}
