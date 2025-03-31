package gkmasmod.downfall.charbosses.relics;

import gkmasmod.downfall.charbosses.actions.common.EnemyGainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.VioletLotus;
import com.megacrit.cardcrawl.stances.AbstractStance;


public class CBR_VioletLotus extends AbstractCharbossRelic {
    public static final String ID = "VioletLotus";

    public CBR_VioletLotus() {
        super(new VioletLotus());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onChangeStance(AbstractStance prevStance, AbstractStance newStance) {
        if (!prevStance.ID.equals(newStance.ID) && prevStance.ID.equals("Calm")) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new EnemyGainEnergyAction(1));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_VioletLotus();
    }
}