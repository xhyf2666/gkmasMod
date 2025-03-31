package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class SleepyLullabyAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private boolean upgrade;
    private AbstractPlayer p;
    private int energyOnUse;

    public SleepyLullabyAction(AbstractPlayer p,boolean upgrade, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.upgrade = upgrade;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (effect > 0) {
            addToBot(new ApplyPowerAction(this.p,this.p,new DrawCardNextTurnPower(this.p,effect),effect));
            if(this.upgrade){
                effect = (int) (effect * 1.5F);
            }
            addToBot(new ApplyPowerAction(this.p,this.p,new EnergizedBluePower(this.p,effect),effect));
            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}