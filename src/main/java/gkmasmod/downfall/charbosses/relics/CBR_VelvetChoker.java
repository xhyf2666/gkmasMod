package gkmasmod.downfall.charbosses.relics;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.VelvetChoker;

public class CBR_VelvetChoker extends AbstractCharbossRelic {
    public static final String ID = "Velvet Choker";
    private int numCards;

    public CBR_VelvetChoker() {
        super(new VelvetChoker());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[2] + this.DESCRIPTIONS[0] + 6 + this.DESCRIPTIONS[1];
    }

    @Override
    public void onEquip() {
        final EnergyManager energy = AbstractCharBoss.boss.energy;
        ++energy.energyMaster;
    }

    @Override
    public void onUnequip() {
        final EnergyManager energy = AbstractCharBoss.boss.energy;
        --energy.energyMaster;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_VelvetChoker();
    }
}
