package gkmasmod.actions;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import gkmasmod.downfall.bosses.AbstractIdolBoss;

import java.util.Iterator;

public class SpecialTreasureAction extends AbstractGameAction {
    private int amount;
    private AbstractCreature owner;
    private AbstractCard card;


    private static final float DURATION = 0.1F;

    public SpecialTreasureAction(AbstractCreature owner,AbstractCreature target, int amount) {
        this.card = null;
        this.owner = owner;
        this.amount = amount;
        this.target = target;
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1F;
    }

    public void update() {
        if (this.duration == 0.1F &&
                this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.NONE));
            int goldLoss = 0;
            if(this.owner.isPlayer){
                goldLoss = (int) (AbstractDungeon.player.gold * 1.0F*this.amount / 100);
            }
            else if(this.owner instanceof AbstractIdolBoss){
                goldLoss = (int) (((AbstractIdolBoss) this.owner).getGold()*1.0F*this.amount/100);
            }
            if(goldLoss==0){
                this.isDone = true;
                return;
            }
            int damage = goldLoss;
            if(this.owner instanceof AbstractCharBoss)
                damage = calculateDamage2(damage, this.target);
            else
                damage = calculateDamage(damage, this.target);
            if (damage > 0) {
                this.target.damage(new DamageInfo(this.owner, damage, DamageInfo.DamageType.NORMAL));
            }

            if (((this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead) {
                this.isDone = true;
                return;
            }
            if(this.owner.isPlayer){
                AbstractDungeon.player.loseGold(goldLoss);
                for (int i = 0; i < goldLoss; i++)
                    AbstractDungeon.effectList.add(new GainPennyEffect(this.owner, this.target.hb.cX, this.target.hb.cY, this.owner.hb.cX, this.owner.hb.cY, true));
            }
            else if(this.owner instanceof AbstractIdolBoss){
                ((AbstractIdolBoss) this.owner).setGold(((AbstractIdolBoss) this.owner).getGold()-goldLoss);
                AbstractDungeon.player.gainGold(goldLoss);
                for (int i = 0; i < goldLoss; i++)
                    AbstractDungeon.effectList.add(new GainPennyEffect(this.target, this.owner.hb.cX, this.owner.hb.cY, this.target.hb.cX, this.target.hb.cY, true));
            }
            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
        }
        tickDuration();
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
            for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageGive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            tmp = player.stance.atDamageGive(tmp, DamageInfo.DamageType.NORMAL);

            for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL)) {
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
