package gkmasmod.downfall.charbosses.relics;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.powers.BossIntangiblePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.IncenseBurner;

public class CBR_IncenseBurner extends AbstractCharbossRelic {
    public static final String ID = "IncenseBurner";

    public CBR_IncenseBurner(int counter) {
        super(new IncenseBurner());
        this.counter = counter;
    }

    public CBR_IncenseBurner() {
        this(1);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

//    @Override
//    public void onEquip() {
//        this.counter = 1;
//    }

    @Override
    public void onEnergyRecharge() {
        if (this.counter == -1) {
            this.counter += 2;
        } else {
            ++this.counter;
        }
        if (this.counter == 6) {
            this.counter = 0;
            this.beginLongPulse();
            this.addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
            this.addToBot(new ApplyPowerAction(AbstractCharBoss.boss, null, new BossIntangiblePower(AbstractCharBoss.boss, 1), 1));
        } else {
            this.stopPulse();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_IncenseBurner();
    }
}
