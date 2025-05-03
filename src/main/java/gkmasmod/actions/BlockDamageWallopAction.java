package gkmasmod.actions;


import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.patches.AbstractPlayerPatch;
import gkmasmod.relics.PocketBook;

import java.util.Iterator;

public class BlockDamageWallopAction extends AbstractGameAction {
    private AbstractCreature m;
    private AbstractCreature p;
    private int blockAdd;
    private float rate;
    private boolean damageAll;
    private AbstractCard card;

    public BlockDamageWallopAction(float rate, int blockAdd, AbstractCreature p, AbstractCreature m, AbstractCard card) {
        this(rate, blockAdd, p, m,card, false);
    }

    /**
     * 格挡倍率版当头Action
     * @param rate 伤害倍率
     * @param blockAdd 格挡增加量
     * @param p 触发者
     * @param m 目标
     * @param card 触发卡牌
     * @param damageAll 是否为aoe伤害
     **/
    public BlockDamageWallopAction(float rate, int blockAdd, AbstractCreature p, AbstractCreature m, AbstractCard card, boolean damageAll) {
        this.m = m;
        this.p = p;
        this.blockAdd = blockAdd;
        this.rate = rate;
        this.damageAll = damageAll;
        this.card = card;
    }


    public void update() {
        if(this.blockAdd > 0)
            p.addBlock(this.blockAdd);
        int damage = (int)(this.rate*p.currentBlock);
        int count;
        if(this.p instanceof AbstractCharBoss) {
            damage = calculateDamage2(damage, this.m);
            count = damage;
        }
        else{
            damage = calculateDamage(damage, this.m);
            count = damage;
            if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                if(AbstractPlayerPatch.FinalCircleRoundField.finalCircleRound.get(AbstractDungeon.player).size()>0){
                    count = (int) (1.0f*count / (AbstractPlayerPatch.FinalDamageRateField.finalDamageRate.get(AbstractDungeon.player)*1.0f));
                }
            }
        }
        p.addBlock(count);
        if(damageAll&&p.isPlayer){
            addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(damage, true), DamageInfo.DamageType.NORMAL, AttackEffect.SLASH_HORIZONTAL));
        }
        else{
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage , DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_HORIZONTAL));
        }

        this.isDone = true;
    }

    public int calculateDamage(int baseDamage,AbstractCreature m){
        AbstractPlayer player = AbstractDungeon.player;
        if (m != null) {
            float tmp = (float)baseDamage;
            Iterator var9 = player.relics.iterator();

            while(var9.hasNext()) {
                AbstractRelic r = (AbstractRelic)var9.next();
                tmp = r.atDamageModify(tmp, this.card);
            }

            AbstractPower p;
            for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageGive(tmp, DamageInfo.DamageType.NORMAL, this.card)) {
                p = (AbstractPower)var9.next();
            }

            tmp = player.stance.atDamageGive(tmp, DamageInfo.DamageType.NORMAL, this.card);

            for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL, this.card)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL, this.card)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalReceive(tmp, DamageInfo.DamageType.NORMAL, this.card)) {
                p = (AbstractPower)var9.next();
            }

            if (tmp < 0.0F) {
                tmp = 0.0F;
            }

            return MathUtils.floor(tmp);
        }
        return baseDamage;
    }

    private int calculateDamage2(int baseDamage,AbstractCreature m) {
        AbstractCharBoss charBoss = (AbstractCharBoss) this.p;
        if (m != null) {
            float tmp = (float)baseDamage;
            Iterator var9 = charBoss.relics.iterator();

            if(this.card!=null){
                while(var9.hasNext()) {
                    AbstractRelic r = (AbstractRelic)var9.next();
                    tmp = r.atDamageModify(tmp, this.card);
                }
            }

            AbstractPower p;
            for(var9 = charBoss.powers.iterator(); var9.hasNext(); tmp = p.atDamageGive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            tmp = charBoss.stance.atDamageGive(tmp, DamageInfo.DamageType.NORMAL);

            for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = charBoss.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalReceive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            if (tmp < 0.0F) {
                tmp = 0.0F;
            }

            return MathUtils.floor(tmp);
        }
        return baseDamage;
    }
}
