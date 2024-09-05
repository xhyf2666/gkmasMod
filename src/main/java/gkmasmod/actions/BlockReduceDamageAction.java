package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BlockReduceDamageAction extends AbstractGameAction {
    private AbstractMonster m;

    private AbstractPlayer p;

    private float blockReduceRate;

    private float damageRate;

    public BlockReduceDamageAction(float damageRate, float blockReduceRate, AbstractPlayer p, AbstractMonster m) {
        this.m = m;
        this.p = p;
        this.blockReduceRate = blockReduceRate;
        this.damageRate = damageRate;
    }

    public void update() {
        int damage = (int)(this.damageRate*p.currentBlock);
        int count = (int) (1.0F*p.currentBlock*blockReduceRate);
        p.loseBlock(count);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage , DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_HORIZONTAL));
        this.isDone = true;
    }
}
