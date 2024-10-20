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
import gkmasmod.characters.IdolCharacter;

import java.util.Iterator;

public class BlockDamageWallopAction extends AbstractGameAction {
    private AbstractMonster m;

    private AbstractPlayer p;

    private int blockAdd;

    private float rate;

    private float yarukiRate;

    private boolean damageAll;

    private AbstractCard card;

    public BlockDamageWallopAction(float rate, int blockAdd, AbstractPlayer p, AbstractMonster m, AbstractCard card) {
        this(rate, blockAdd, p, m,card, false);
    }

    public BlockDamageWallopAction(float rate, int blockAdd, AbstractPlayer p, AbstractMonster m, AbstractCard card, boolean damageAll) {
        this(rate, blockAdd, p, m,card,damageAll,1.0f);
    }

    public BlockDamageWallopAction(float rate, int blockAdd, AbstractPlayer p, AbstractMonster m, AbstractCard card, boolean damageAll, float yarukiRate) {
        this.m = m;
        this.p = p;
        this.blockAdd = blockAdd;
        this.rate = rate;
        this.damageAll = damageAll;
        this.card = card;
        this.yarukiRate = yarukiRate;
    }

    public void update() {
        if(yarukiRate>1.0f){
            this.blockAdd += (yarukiRate-1.0f)*this.blockAdd;
        }
        if(this.blockAdd > 0)
            p.addBlock(this.blockAdd);
        int damage = (int)(this.rate*p.currentBlock);
        damage = calculateDamage(damage);
        int count = damage;
        if(AbstractDungeon.player instanceof IdolCharacter){
            IdolCharacter idol = (IdolCharacter) AbstractDungeon.player;
            if(idol.finalDamageRate > 0){
                count = (int) (count / idol.finalDamageRate);
            }
        }
        p.addBlock(count);
        if(damageAll){
            addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(damage, true), DamageInfo.DamageType.NORMAL, AttackEffect.SLASH_HORIZONTAL));
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
