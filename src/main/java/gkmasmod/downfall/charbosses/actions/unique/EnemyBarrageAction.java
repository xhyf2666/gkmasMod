package gkmasmod.downfall.charbosses.actions.unique;


import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.orbs.EnemyEmptyOrbSlot;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class EnemyBarrageAction extends AbstractGameAction {
    private DamageInfo info = null;
    private final AbstractCreature target;

    public EnemyBarrageAction(AbstractCreature m, DamageInfo info) {
        this.info = info;
        this.target = m;
    }

    public void update() {
        for (int i = 0; i < AbstractCharBoss.boss.orbs.size(); ++i) {
            if (!(AbstractCharBoss.boss.orbs.get(i) instanceof EnemyEmptyOrbSlot)) {
                this.addToTop(new DamageAction(this.target, this.info, AttackEffect.BLUNT_LIGHT, true));
            }
        }

        this.isDone = true;
    }
}
