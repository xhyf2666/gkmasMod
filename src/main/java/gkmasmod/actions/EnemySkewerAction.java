package gkmasmod.actions;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

public class EnemySkewerAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private int damage;
    private AbstractCreature p;
    private AbstractCreature m;
    private DamageInfo.DamageType damageTypeForTurn;
    private int energyOnUse;

    public EnemySkewerAction(AbstractCreature p, AbstractCreature m, int damage, DamageInfo.DamageType damageTypeForTurn, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.m = m;
        this.damage = damage;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.damageTypeForTurn = damageTypeForTurn;
        this.energyOnUse = energyOnUse;
        //TODO 需要适配连击
    }

    public void update() {
        int effect = AbstractCharBoss.boss.energyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (AbstractCharBoss.boss.hasRelic("Chemical X")) {
            effect += 2;
            AbstractCharBoss.boss.getRelic("Chemical X").flash();
        }

        if (effect > 0) {
            for(int i = 0; i < effect; ++i) {
                this.addToBot(new DamageAction(this.m, new DamageInfo(this.p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }

            if (!this.freeToPlayOnce) {
                AbstractCharBoss.boss.energy.use(AbstractCharBoss.boss.energyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
