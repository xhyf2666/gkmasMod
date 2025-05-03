package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.relics.CBR_FrogFan;
import gkmasmod.powers.GoodImpression;
import gkmasmod.relics.FirstStarChallenge;
import gkmasmod.relics.FrogFan;
import gkmasmod.utils.PlayerHelper;

public class FirstStarChallengeAction extends AbstractGameAction {
    private AbstractCreature p;
    private int require;
    private int magic;
    private int magic2;
    AbstractRelic relic = null;

    public FirstStarChallengeAction(AbstractCreature p, int require, int magic, int magic2,AbstractRelic relic) {
        this.p = p;
        this.require = require;
        this.magic = magic;
        this.magic2 = magic2;
        this.relic = relic;
    }

    public void update() {
        int count = PlayerHelper.getPowerAmount(this.p, GoodImpression.POWER_ID);
        if(count < this.require){
            this.isDone = true;
            return;
        }
        if(relic.counter <= 0){
            this.isDone = true;
            return;
        }
        addToBot(new ApplyPowerAction(p,p,new GoodImpression(p,magic),magic));
        addToBot(new ApplyPowerAction(p,p,new DexterityPower(p,magic2),magic2));
        relic.flash();
        relic.counter--;
        if(relic.counter == 0){
            relic.grayscale = true;
        }

        this.isDone = true;
    }

}
