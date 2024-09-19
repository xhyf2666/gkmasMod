package gkmasmod.actions;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Iterator;

public class BlockReduceDamageAction extends AbstractGameAction {
    private AbstractMonster m;

    private AbstractPlayer p;

    private float blockReduceRate;

    private float damageRate;

    private boolean damageAll;

    private AbstractCard card;

    public BlockReduceDamageAction(float damageRate, float blockReduceRate, AbstractPlayer p, AbstractMonster m, AbstractCard card) {
        this(damageRate, blockReduceRate, p, m,card, false);
    }

    public BlockReduceDamageAction(float damageRate, float blockReduceRate, AbstractPlayer p, AbstractMonster m, AbstractCard card, boolean damageAll) {
        this.m = m;
        this.p = p;
        this.blockReduceRate = blockReduceRate;
        this.damageRate = damageRate;
        this.damageAll = damageAll;
        this.card = card;
    }

    public void update() {
        int damage = (int)(this.damageRate*p.currentBlock);
        damage = calculateDamage(damage);
        int count = (int) (1.0F*p.currentBlock*blockReduceRate);
        p.loseBlock(count);
        if(damageAll){
            addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(damage, true), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
        else{
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage , DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_HORIZONTAL));
        }
        this.isDone = true;
    }

    public int calculateDamage(int baseDamage){
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
}
