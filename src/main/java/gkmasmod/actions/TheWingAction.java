package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.relics.CBR_FrogFan;
import gkmasmod.downfall.relics.CBR_TheWing;
import gkmasmod.patches.AbstractPowerPatch;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.TheWingPower;
import gkmasmod.relics.FrogFan;
import gkmasmod.relics.TheWing;
import gkmasmod.utils.PlayerHelper;

public class TheWingAction extends AbstractGameAction {
    private AbstractCreature p;
    private int require;
    private float rate;
    AbstractRelic relic = null;

    public TheWingAction(AbstractCreature p, int require, float rate,AbstractRelic relic) {
        this.p = p;
        this.require = require;
        this.rate = rate;
        this.relic = relic;
    }

    public void update() {
        int count = this.p.currentBlock;
        if(count < this.require){
            this.isDone = true;
            return;
        }
        if(relic.counter <= 0){
            this.isDone = true;
            return;
        }

        int change = PlayerHelper.getPowerAmount(this.p, GoodImpression.POWER_ID);
        int add = (int) (1.0F*change*rate);
        if(add>0){
            AbstractPower power = new GoodImpression(this.p, add);
            AbstractPowerPatch.IgnoreIncreaseModifyField.flag.set(power, true);
            addToBot(new ApplyPowerAction(this.p, this.p, power, add));
        }

        addToBot(new ApplyPowerAction(this.p, this.p, new TheWingPower(this.p)));
        relic.flash();
        relic.counter--;
        if(relic.counter == 0){
            relic.grayscale = true;
        }

        this.isDone = true;
    }

}
