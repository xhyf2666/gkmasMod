package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.patches.AbstractPowerPatch;
import gkmasmod.utils.PlayerHelper;


public class EnjoyAfterHotSpringAction extends AbstractGameAction {
    private AbstractCreature p;
    private float rate ;

    public EnjoyAfterHotSpringAction(AbstractCreature p, float rate) {
        this.p = p;
        this.rate = rate;
    }

    public void update() {
        int count = PlayerHelper.getPowerAmount(p, StrengthPower.POWER_ID);
        int amount = (int)(count * rate);
        if(amount>0){
            AbstractPower power = new StrengthPower(p, amount);
            AbstractPowerPatch.IgnoreIncreaseModifyField.flag.set(power, true);
            addToBot(new ApplyPowerAction(p, p, power, amount));
        }
        this.isDone = true;
    }

}
