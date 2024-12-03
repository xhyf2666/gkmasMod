package gkmasmod.actions;


import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.downfall.relics.CBR_PigDreamPiggyBank;
import gkmasmod.powers.GoodImpression;
import gkmasmod.relics.PigDreamPiggyBank;
import gkmasmod.utils.PlayerHelper;

public class PigDreamPiggyBankAction extends AbstractGameAction {
    private AbstractCreature p;
    private int require;
    private int reward;

    public PigDreamPiggyBankAction(AbstractCreature p, int require, int reward) {
        this.p = p;
        this.require = require;
        this.reward = reward;
    }

    public void update() {
        int count = PlayerHelper.getPowerAmount(p, GoodImpression.POWER_ID);
        if(p.isPlayer){
            if(AbstractDungeon.player.getRelic(PigDreamPiggyBank.ID).counter <= 0){
                this.isDone = true;
                return;
            }
        }
        else if(p instanceof AbstractCharBoss){
            if(AbstractCharBoss.boss.getRelic(CBR_PigDreamPiggyBank.ID2).counter <= 0){
                this.isDone = true;
                return;
            }
        }
        if(count > require){
            addToBot(new ApplyPowerAction(p, p, new GoodImpression(p, reward), reward));
            addToBot(new GainTrainRoundPowerAction(p,1));
        }
        else{
            this.isDone = true;
            return;
        }
        if(p.isPlayer){
            AbstractDungeon.player.getRelic(PigDreamPiggyBank.ID).counter--;
            if(AbstractDungeon.player.getRelic(PigDreamPiggyBank.ID).counter == 0){
                AbstractDungeon.player.getRelic(PigDreamPiggyBank.ID).grayscale = true;
            }
        }
        else if(p instanceof AbstractCharBoss){
            AbstractCharBoss.boss.getRelic(CBR_PigDreamPiggyBank.ID2).counter--;
            if(AbstractCharBoss.boss.getRelic(CBR_PigDreamPiggyBank.ID2).counter == 0){
                AbstractCharBoss.boss.getRelic(CBR_PigDreamPiggyBank.ID2).grayscale = true;
            }
        }
        this.isDone = true;
    }

}
