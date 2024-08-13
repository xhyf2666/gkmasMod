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

public class GenkiChangeAction extends AbstractGameAction {
    private AbstractMonster m;

    private AbstractPlayer p;

    private DamageInfo info;

    private int magicAdd;

    private int magicNumber;

    private float rate;

    public GenkiChangeAction(float rate, int magicAdd, int magicnumber, AbstractPlayer p, AbstractMonster m, DamageInfo info) {
        this.m = m;
        this.p = p;
        this.info = info;
        this.magicNumber = magicnumber;
        this.magicAdd = magicAdd;
        this.rate = rate;
    }

    public void update() {
        System.out.println("this.magicNumber "+this.magicNumber);
        System.out.println("this.magicAdd "+this.magicAdd);
        System.out.println("this.rate "+this.rate);


        addToBot((AbstractGameAction)new ApplyPowerAction(p, p, (AbstractPower)new GoodImpression(p,this.magicAdd), this.magicAdd));
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)m, new DamageInfo((AbstractCreature)p, (int)(this.rate*this.magicNumber) , DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_HORIZONTAL));

        this.isDone = true;
    }
}
