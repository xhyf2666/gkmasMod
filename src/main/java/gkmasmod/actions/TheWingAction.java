package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.relics.CBR_FrogFan;
import gkmasmod.downfall.relics.CBR_TheWing;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.TheWingPower;
import gkmasmod.relics.FrogFan;
import gkmasmod.relics.TheWing;
import gkmasmod.utils.PlayerHelper;

public class TheWingAction extends AbstractGameAction {
    private AbstractCreature p;
    private int require;
    private float rate;
    private int HP_COST;

    public TheWingAction(AbstractCreature p, int require, float rate) {
        this.p = p;
        this.require = require;
        this.rate = rate;
    }

    public void update() {
        int count = this.p.currentBlock;
        if(count < this.require){
            this.isDone = true;
            return;
        }
        if(p.isPlayer){
            if(AbstractDungeon.player.getRelic(TheWing.ID).counter <= 0){
                this.isDone = true;
                return;
            }
        }
        else if(p instanceof AbstractCharBoss){
            if(AbstractCharBoss.boss.getRelic(CBR_TheWing.ID2).counter <= 0){
                this.isDone = true;
                return;
            }
        }

        int change = PlayerHelper.getPowerAmount(this.p, GoodImpression.POWER_ID);
        change = (int) (1.0F*change*rate);
        if(change>0){
            addToBot(new ApplyPowerAction(this.p, this.p, new GoodImpression(this.p, change), change));
        }

        addToBot(new ApplyPowerAction(this.p, this.p, new TheWingPower(this.p)));
        if(p.isPlayer){
            AbstractDungeon.player.getRelic(TheWing.ID).counter--;
            if(AbstractDungeon.player.getRelic(TheWing.ID).counter == 0){
                AbstractDungeon.player.getRelic(TheWing.ID).grayscale = true;
            }
        }
        else if (p instanceof AbstractCharBoss){
            AbstractCharBoss.boss.getRelic(CBR_TheWing.ID2).counter--;
            if(AbstractCharBoss.boss.getRelic(CBR_TheWing.ID2).counter == 0){
                AbstractCharBoss.boss.getRelic(CBR_TheWing.ID2).grayscale = true;
            }
        }

        this.isDone = true;
    }

}
