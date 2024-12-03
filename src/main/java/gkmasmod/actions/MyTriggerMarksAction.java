package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;
import gkmasmod.utils.PlayerHelper;

public class MyTriggerMarksAction extends AbstractGameAction {

    AbstractCard card;

    public MyTriggerMarksAction(AbstractCreature target) {
        this.target = target;
        this.card = card;
    }

    public void update() {
        int amount = PlayerHelper.getPowerAmount(this.target,MarkPower.POWER_ID);
        if(amount>0)
            addToBot(new LoseHPAction(target, null, amount, AttackEffect.FIRE));
        this.isDone = true;
    }

}
