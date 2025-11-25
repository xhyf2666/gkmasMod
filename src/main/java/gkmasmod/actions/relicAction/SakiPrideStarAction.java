package gkmasmod.actions.relicAction;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.powers.GoodImpression;
import gkmasmod.utils.PlayerHelper;

public class SakiPrideStarAction extends AbstractGameAction {
    private AbstractCreature p;
    private int require;
    private int rate;
    private int append;
    AbstractRelic relic = null;

    public SakiPrideStarAction(AbstractCreature p, int require, int rate, int append,
                               AbstractRelic relic) {
        this.p = p;
        this.require = require;
        this.rate = rate;
        this.append = append;
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
        int add = (int) (count*1.0F*this.rate/100) + append;
        addToBot(new ApplyPowerAction(this.p, this.p, new GoodImpression(this.p, add), add));
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, relic));
        relic.flash();
        relic.counter--;
        if(relic.counter == 0){
            relic.grayscale = true;
        }

        this.isDone = true;
    }

}
