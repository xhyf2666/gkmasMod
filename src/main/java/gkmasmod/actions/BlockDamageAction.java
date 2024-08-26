package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.powers.GoodImpression;

public class BlockDamageAction extends AbstractGameAction {
    private AbstractMonster m;

    private AbstractPlayer p;

    private int blockAdd;

    private float rate;

    public BlockDamageAction(float rate, int blockAdd, AbstractPlayer p, AbstractMonster m) {
        this.m = m;
        this.p = p;
        this.blockAdd = blockAdd;
        this.rate = rate;
    }

    public void update() {
        if(this.blockAdd > 0)
            p.addBlock(this.blockAdd);
        int damage = (int)(this.rate*p.currentBlock);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage , DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_HORIZONTAL));

        this.isDone = true;
    }
}
