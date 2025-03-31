package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.powers.GoodTune;
import gkmasmod.relics.AbsoluteNewSelf;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.utils.PlayerHelper;

public class BalanceAction extends AbstractGameAction {
    private AbstractCreature p;
    private int damage;
    private int HP;

    public BalanceAction(AbstractCreature p) {
        this.p = p;
    }

    public void update() {
        int count_goodTune = PlayerHelper.getPowerAmount(this.p, GoodTune.POWER_ID);
        int count_strength = PlayerHelper.getPowerAmount(this.p, StrengthPower.POWER_ID);
        int count = (count_goodTune + count_strength+1)/2;
        if(count<=0)
            return;
        if(this.p.hasPower(GoodTune.POWER_ID))
            addToBot(new ApplyPowerAction(this.p, this.p, new GoodTune(this.p, count-count_goodTune), count-count_goodTune));
        else{
            addToBot(new ApplyPowerAction(this.p, this.p, new GoodTune(this.p, count), count));
        }
        if(this.p.hasPower(StrengthPower.POWER_ID))
            addToBot(new ApplyPowerAction(this.p, this.p, new StrengthPower(this.p, count-count_strength), count-count_strength));
        else{
            addToBot(new ApplyPowerAction(this.p, this.p, new StrengthPower(this.p, count), count));
        }
        this.isDone = true;
    }

}
