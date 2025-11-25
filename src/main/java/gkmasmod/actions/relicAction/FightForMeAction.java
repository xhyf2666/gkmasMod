package gkmasmod.actions.relicAction;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.actions.common.GainTrainRoundPowerAction;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.GreatGoodTune;
import gkmasmod.utils.PlayerHelper;

public class FightForMeAction extends AbstractGameAction {
    private AbstractCreature p;
    private int draw;
    AbstractRelic relic = null;

    public FightForMeAction(AbstractCreature p, int draw, AbstractRelic relic) {
        this.p = p;
        this.relic = relic;
        this.draw = draw;
    }

    public void update() {
        if(relic.counter <= 0){
            this.isDone = true;
            return;
        }
        int count = PlayerHelper.getPowerAmount(p, GreatGoodTune.POWER_ID);
        if(count <= 0){
            this.isDone = true;
            return;
        }
        addToBot(new DrawCardAction(draw));
        addToBot(new GainTrainRoundPowerAction(p,1));
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, relic));
        relic.flash();
        relic.counter--;
        if(relic.counter == 0){
            relic.grayscale = true;
        }

        this.isDone = true;
    }

}
