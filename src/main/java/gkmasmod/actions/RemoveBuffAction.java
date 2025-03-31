package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.cards.special.Rumor;
import gkmasmod.powers.*;

import java.util.ArrayList;

public class RemoveBuffAction extends AbstractGameAction {
    AbstractCreature owner;
    private int count;

    public RemoveBuffAction(AbstractCreature owner,int count) {
        this.owner = owner;
        this.count = count;
    }

    public void update() {
        ArrayList<String> buffs = new ArrayList<>();
        for (AbstractPower power : this.owner.powers) {
            if (power.type == AbstractPower.PowerType.BUFF) {
                if(power.ID.equals(AllEffort.POWER_ID)||power.ID.equals(TempSavePower.POWER_ID)||power.ID.equals(TrainRoundAnomalyPower.POWER_ID)||power.ID.equals(TrainRoundSensePower.POWER_ID)||power.ID.equals(TrainRoundLogicPower.POWER_ID)){
                    continue;
                }
                buffs.add(power.ID);
            }
        }
        int buffsToRemove = Math.min(buffs.size(), count);
        for (int i = 0; i < buffsToRemove; i++) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, buffs.get(i)));
        }

        this.isDone = true;
    }
}