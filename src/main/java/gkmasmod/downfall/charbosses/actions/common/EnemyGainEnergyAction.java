package gkmasmod.downfall.charbosses.actions.common;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

public class EnemyGainEnergyAction extends AbstractGameAction {
    private final int energyGain;
    private AbstractCharBoss boss;

    public EnemyGainEnergyAction(final int amount) {
        this(AbstractCharBoss.boss, amount);
    }

    public EnemyGainEnergyAction(final AbstractCharBoss target, final int amount) {
        this.setValues(target, target, 0);
        this.duration = Settings.ACTION_DUR_FAST;
        this.energyGain = amount;
        this.boss = target;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractCharBoss.boss != null) {
                this.boss = AbstractCharBoss.boss;
                this.boss.gainEnergy(this.energyGain);
                for (final AbstractCard c : this.boss.hand.group) {
                    c.triggerOnGainEnergy(this.energyGain, true);
                }
            } else {
                this.isDone = true;
                return;
            }
        }
        this.tickDuration();
    }
}
