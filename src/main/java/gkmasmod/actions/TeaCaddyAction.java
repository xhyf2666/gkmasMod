package gkmasmod.actions;


import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.relics.TeaCaddy;
import gkmasmod.stances.ConcentrationStance;

public class TeaCaddyAction extends AbstractGameAction {
    private AbstractCreature p;
    private AbstractCreature m;
    private int damage;
    private int HP;
    AbstractRelic relic = null;

    public TeaCaddyAction(AbstractCreature p, AbstractCreature m, int damage, int HP,AbstractRelic relic) {
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
