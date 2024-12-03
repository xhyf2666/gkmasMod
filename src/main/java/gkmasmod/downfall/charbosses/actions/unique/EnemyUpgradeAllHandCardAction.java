package gkmasmod.downfall.charbosses.actions.unique;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;

public class EnemyUpgradeAllHandCardAction extends AbstractGameAction {
    private final AbstractCharBoss p;

    public EnemyUpgradeAllHandCardAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractCharBoss.boss;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration != Settings.ACTION_DUR_FAST) {
            this.tickDuration();
            return;
        }
        if (this.p.hand.group.size() <= 0) {
            this.isDone = true;
            return;
        }
        final CardGroup upgradeable = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (final AbstractCard c : this.p.hand.group) {
            if (c.canUpgrade() && c.type != AbstractCard.CardType.STATUS) {
                upgradeable.addToTop(c);
            }
        }
        for (AbstractCard c : upgradeable.group) {
            c.upgrade();
            c.superFlash();
            c.applyPowers();
        }
        this.isDone = true;
    }
}
