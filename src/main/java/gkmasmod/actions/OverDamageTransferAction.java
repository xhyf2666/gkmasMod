package gkmasmod.actions;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.utils.PlayerHelper;

import java.util.Iterator;

public class OverDamageTransferAction extends AbstractGameAction {
    private AbstractCreature m;
    private int damage;

    public OverDamageTransferAction(AbstractCreature m, int damage) {
        this.m = m;
        this.damage = damage;
    }

    public void update() {
        float tmp = (float)this.damage;
        Iterator var9;
        AbstractPower p;

        for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL)) {
            p = (AbstractPower)var9.next();
        }

        for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalReceive(tmp, DamageInfo.DamageType.NORMAL)) {
            p = (AbstractPower)var9.next();
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        if(tmp > 0.0F) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(AbstractDungeon.player, MathUtils.floor(tmp), DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_VERTICAL));
        }

        this.isDone = true;
    }

}
