package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class BlockDamageAction extends AbstractGameAction {
    private AbstractCreature m;

    private AbstractCreature p;

    private int blockAdd;

    private float rate;

    private boolean damageAll;

    private AbstractCard card;

    private float blockReduceRate = 0.0f;

    public BlockDamageAction(float rate, int blockAdd, AbstractCreature p, AbstractCreature m, AbstractCard card) {
        this(rate, blockAdd, p, m,card, false,0.0f);
    }

    public BlockDamageAction(float rate, int blockAdd, AbstractCreature p, AbstractCreature m, AbstractCard card,boolean damageAll,float blockReduceRate) {
        this.m = m;
        this.p = p;
        this.blockAdd = blockAdd;
        this.rate = rate;
        this.damageAll = damageAll;
        this.card = card;
        this.blockReduceRate = blockReduceRate;
    }

    public void update() {
//        if(yarukiRate>1.0f){
//            int count = PlayerHelper.getPowerAmount(p, DexterityPower.POWER_ID);
//            this.blockAdd = (int) (1.0f*count*(yarukiRate)*this.blockAdd);
//        }
        if(this.blockAdd > 0)
            p.addBlock(this.blockAdd);
        int damage = (int)(this.rate*p.currentBlock);
        if(this.blockReduceRate>0){
            int blockLoss = (int) (1.0F*p.currentBlock*blockReduceRate);
            p.loseBlock(blockLoss);
        }
        if(damageAll&&p.isPlayer){
            addToTop(new ModifyDamageAllEnemyAction(damage, AttackEffect.SLASH_HORIZONTAL,this.card));
        }
        else{
            addToTop(new ModifyDamageAction(m, new DamageInfo(p, damage , DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_HORIZONTAL,this.card));
        }

        this.isDone = true;
    }

}
