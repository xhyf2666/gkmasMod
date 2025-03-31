package gkmasmod.actions;


import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.downfall.charbosses.stances.ENConcentrationStance;
import gkmasmod.downfall.relics.CBR_AbsoluteNewSelf;
import gkmasmod.relics.AbsoluteNewSelf;
import gkmasmod.stances.ConcentrationStance;

public class AbsoluteNewSelfAction extends AbstractGameAction {
    private AbstractCreature p;
    private AbstractCreature m;
    private int damage;
    private int HP;

    public AbsoluteNewSelfAction(AbstractCreature p, AbstractCreature m, int damage, int HP) {
        this.p = p;
        this.m = m;
        this.damage = damage;
        this.HP = HP;
    }

    public void update() {
        if(p.isPlayer){
            if(AbstractDungeon.player.getRelic(AbsoluteNewSelf.ID).counter <= 0){
                this.isDone = true;
                return;
            }
            if(!AbstractDungeon.player.stance.ID.equals(ConcentrationStance.STANCE_ID)){
                this.isDone = true;
                return;
            }
        }
        else if(p instanceof AbstractCharBoss){
            if(AbstractCharBoss.boss.getRelic(CBR_AbsoluteNewSelf.ID2).counter <= 0){
                this.isDone = true;
                return;
            }
            if(!AbstractCharBoss.boss.stance.ID.equals(ENConcentrationStance.STANCE_ID)){
                this.isDone = true;
                return;
            }
        }
        addToBot(new HealAction(this.p, this.p, HP));
        addToBot(new ModifyDamageAction(this.m, new DamageInfo(this.p, damage, DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_VERTICAL));
        if(p.isPlayer){
            AbstractDungeon.player.getRelic(AbsoluteNewSelf.ID).flash();
            AbstractDungeon.player.getRelic(AbsoluteNewSelf.ID).counter--;
            if(AbstractDungeon.player.getRelic(AbsoluteNewSelf.ID).counter == 0){
                AbstractDungeon.player.getRelic(AbsoluteNewSelf.ID).grayscale = true;
            }
        }
        else if (p instanceof AbstractCharBoss){
            AbstractCharBoss.boss.getRelic(CBR_AbsoluteNewSelf.ID2).counter--;
            if(AbstractCharBoss.boss.getRelic(CBR_AbsoluteNewSelf.ID2).counter == 0){
                AbstractCharBoss.boss.getRelic(CBR_AbsoluteNewSelf.ID2).grayscale = true;
            }
        }
        this.isDone = true;
    }

}
