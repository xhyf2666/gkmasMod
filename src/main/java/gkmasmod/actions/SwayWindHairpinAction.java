package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.stances.ENConcentrationStance;
import gkmasmod.stances.ConcentrationStance;

public class SwayWindHairpinAction extends AbstractGameAction {
    private AbstractCreature p;
    private AbstractCreature m;
    private int damage;
    private int HP;
    AbstractRelic relic = null;

    /**
     * 十王星南遗物：全新的我 Action
     * @param p 遗物持有者
     * @param m 目标
     * @param damage 基础伤害
     * @param HP 损失血量
     * @param relic 触发该Action的遗物
     */
    public SwayWindHairpinAction(AbstractCreature p, AbstractCreature m, int damage, int HP, AbstractRelic relic) {
        this.p = p;
        this.m = m;
        this.damage = damage;
        this.HP = HP;
        this.relic = relic;
    }

    public void update() {
        if(relic.counter <= 0){
            this.isDone = true;
            return;
        }
        if(p.isPlayer){
            if(!AbstractDungeon.player.stance.ID.equals(ConcentrationStance.STANCE_ID)){
                this.isDone = true;
                return;
            }
        }
        else if(p instanceof AbstractCharBoss){
            if(!AbstractCharBoss.boss.stance.ID.equals(ENConcentrationStance.STANCE_ID)){
                this.isDone = true;
                return;
            }
        }
        addToBot(new HealAction(this.p, this.p, HP));
        addToBot(new ModifyDamageAction(this.m, new DamageInfo(this.p, damage, DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_VERTICAL));
        relic.flash();
        relic.counter--;
        if(relic.counter == 0){
            relic.grayscale = true;
        }
        this.isDone = true;
    }

}
