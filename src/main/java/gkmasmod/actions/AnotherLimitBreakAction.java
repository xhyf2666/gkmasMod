package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.patches.AbstractPowerPatch;

public class AnotherLimitBreakAction extends AbstractGameAction{
    private AbstractCreature p;

    public AnotherLimitBreakAction(AbstractCreature p) {
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.p = p;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST && this.p.hasPower("Strength")) {
            int strAmt = this.p.getPower("Strength").amount;
            AbstractPower power = new StrengthPower(this.p, strAmt);
            AbstractPowerPatch.IgnoreIncreaseModifyField.flag.set(power, true);
            this.addToTop(new ApplyPowerAction(this.p, this.p, power, strAmt));
        }

        this.tickDuration();
    }
}
