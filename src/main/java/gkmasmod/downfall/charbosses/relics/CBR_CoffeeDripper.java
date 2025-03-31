package gkmasmod.downfall.charbosses.relics;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CoffeeDripper;

public class CBR_CoffeeDripper extends AbstractCharbossRelic {
    public static final String ID = "Coffee Dripper";

    public CBR_CoffeeDripper() {
        super(new CoffeeDripper());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[1] + this.DESCRIPTIONS[0];
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
        return new CBR_CoffeeDripper();
    }
}
