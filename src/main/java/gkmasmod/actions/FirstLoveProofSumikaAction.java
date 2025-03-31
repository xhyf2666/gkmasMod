package gkmasmod.actions;


import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import gkmasmod.downfall.relics.CBR_FirstLoveProofSumika;
import gkmasmod.relics.FirstLoveProofSumika;
import gkmasmod.utils.PlayerHelper;

public class FirstLoveProofSumikaAction extends AbstractGameAction {
    private AbstractCreature p;
    private AbstractCreature m;
    private int require;
    private float rate;
    private int HP_COST;

    public FirstLoveProofSumikaAction(AbstractCreature p, AbstractCreature m, int require, float rate, int HP_COST) {
        this.p = p;
        this.m = m;
        this.require = require;
        this.rate = rate;
        this.HP_COST = HP_COST;
    }

    public void update() {
        int count = PlayerHelper.getPowerAmount(this.p, DexterityPower.POWER_ID);
        if(count < this.require){
            this.isDone = true;
            return;
        }
        if(p.isPlayer){
            if(AbstractDungeon.player.getRelic(FirstLoveProofSumika.ID).counter <= 0){
                this.isDone = true;
                return;
            }
        }
        else if(p instanceof AbstractCharBoss){
            if(AbstractCharBoss.boss.getRelic(CBR_FirstLoveProofSumika.ID2).counter <= 0){
                this.isDone = true;
                return;
            }
        }
        int damage = (int) (count*1.0F*this.rate);
        addToBot(new LoseHPAction(this.p, this.p, HP_COST));
        addToBot(new ModifyDamageAction(this.m, new DamageInfo(this.p, damage, DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_VERTICAL));
        if(p.isPlayer){
            AbstractDungeon.player.getRelic(FirstLoveProofSumika.ID).counter--;
            if(AbstractDungeon.player.getRelic(FirstLoveProofSumika.ID).counter == 0){
                AbstractDungeon.player.getRelic(FirstLoveProofSumika.ID).grayscale = true;
            }
        }
        else if(p instanceof AbstractCharBoss){
            AbstractCharBoss.boss.getRelic(CBR_FirstLoveProofSumika.ID).counter--;
            if(AbstractCharBoss.boss.getRelic(CBR_FirstLoveProofSumika.ID).counter == 0){
                AbstractCharBoss.boss.getRelic(CBR_FirstLoveProofSumika.ID).grayscale = true;
            }
        }
        this.isDone = true;
    }

}
