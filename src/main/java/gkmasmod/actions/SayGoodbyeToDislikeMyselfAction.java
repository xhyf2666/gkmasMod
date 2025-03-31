package gkmasmod.actions;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.growEffect.AttackTimeGrow;
import gkmasmod.growEffect.DamageGrow;

import java.util.Iterator;

public class SayGoodbyeToDislikeMyselfAction extends AbstractGameAction {
    private DamageInfo info;
    private AbstractCard card;
    private AbstractCreature owner;
    private int rate;
    private boolean countTime = false;


    public SayGoodbyeToDislikeMyselfAction(AbstractCreature target, DamageInfo info,int rate, AbstractCard card, boolean countTime) {
        this.target = target;
        this.info = info;
        this.owner = info.owner;
        this.rate = rate;
        this.card = card;
        this.countTime = countTime;
    }

    @Override
    public void update() {
        int attackTime = 1;
        if (this.target != null) {
            int damage = this.info.base;
            if(this.card!=null){
                for (AbstractCardModifier mod : CardModifierManager.modifiers(this.card)) {
                    if(mod instanceof DamageGrow)
                        damage += ((DamageGrow)mod).getAmount();
                    if(this.countTime&&mod instanceof AttackTimeGrow)
                        attackTime += ((AttackTimeGrow)mod).getAmount();
                }
            }

            AbstractPower strength = this.owner.getPower("Strength");
            int amount = 0;
            if (strength != null) {
                amount = strength.amount;
                strength.amount = (int) (strength.amount * (1.0 * this.rate / 100));
            }

            if(this.owner instanceof AbstractCharBoss)
                damage = calculateDamage2(damage, this.target);
            else
                damage = calculateDamage(damage, this.target);
            for (int i = 0; i < attackTime; i++) {
                addToBot(new DamageAction(this.target, new DamageInfo(this.info.owner, damage, this.info.type), this.attackEffect));
            }
            if (strength != null) {
                strength.amount = amount;
            }
        }
        this.isDone = true;
    }

    public int calculateDamage(int baseDamage, AbstractCreature m){
        AbstractPlayer player = AbstractDungeon.player;
        if (m != null) {
            float tmp = (float)baseDamage;
            Iterator var9 = player.relics.iterator();

            if(this.card!=null){
                while(var9.hasNext()) {
                    AbstractRelic r = (AbstractRelic)var9.next();
                    tmp = r.atDamageModify(tmp, this.card);
                }
            }

            AbstractPower p;
            for(var9 = this.owner.powers.iterator(); var9.hasNext(); tmp = p.atDamageGive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            if(this.owner.isPlayer)
                tmp = player.stance.atDamageGive(tmp, DamageInfo.DamageType.NORMAL);

            for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = this.owner.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL)) {
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

    private int calculateDamage2(int baseDamage,AbstractCreature m) {
        AbstractCharBoss charBoss = (AbstractCharBoss) this.owner;
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
