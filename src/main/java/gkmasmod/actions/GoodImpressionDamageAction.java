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

public class GoodImpressionDamageAction extends AbstractGameAction {
    private AbstractMonster m;

    private AbstractPlayer p;


    private int goodImpressionAdd;

    private int currentGoodImpression;

    private float rate;

    public GoodImpressionDamageAction(float rate,int goodImpressionAdd,int currentGoodImpression,AbstractPlayer p, AbstractMonster m) {
        this.m = m;
        this.p = p;
        this.currentGoodImpression = currentGoodImpression;
        this.goodImpressionAdd = goodImpressionAdd;
        this.rate = rate;
    }

    public void update() {
        addToBot(new ApplyPowerAction(p, p, new GoodImpression(p,this.goodImpressionAdd), this.goodImpressionAdd));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, (int)(this.rate*(this.currentGoodImpression+goodImpressionAdd)) , DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        this.isDone = true;
    }
}
