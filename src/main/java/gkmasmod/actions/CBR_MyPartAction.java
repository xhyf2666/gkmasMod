package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.cards.anomaly.Starlight;
import gkmasmod.downfall.charbosses.actions.common.EnemyMakeTempCardInHandAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.growEffect.DamageGrow;
import gkmasmod.powers.GoodTune;
import gkmasmod.utils.GrowHelper;
import gkmasmod.utils.PlayerHelper;

public class CBR_MyPartAction extends AbstractGameAction {
    private boolean grow = true;

    public CBR_MyPartAction(boolean grow) {
        this.grow = grow;
    }

    public void update() {
        if(AbstractCharBoss.boss.drawPile.isEmpty())
        {
            this.isDone = true;
            return;
        }
        AbstractCard card2 = AbstractCharBoss.boss.drawPile.getBottomCard();
        AbstractCharBoss.boss.drawPile.removeCard(card2);
        if(this.grow&&card2 instanceof Starlight)
            GrowHelper.grow(card2, DamageGrow.growID,4);
        addToBot(new EnemyMakeTempCardInHandAction(card2));

        if(AbstractCharBoss.boss.drawPile.isEmpty())
        {
            this.isDone = true;
            return;
        }
        AbstractCard card3 = AbstractCharBoss.boss.drawPile.getBottomCard();
        AbstractCharBoss.boss.drawPile.removeCard(card3);
        if(this.grow&&card3 instanceof Starlight)
            GrowHelper.grow(card3, DamageGrow.growID,4);
        addToBot(new EnemyMakeTempCardInHandAction(card3));

        this.isDone = true;
    }

}
