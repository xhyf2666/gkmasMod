package gkmasmod.actions.relicAction;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.actions.common.GainBlockWithPowerAction;
import gkmasmod.actions.common.GainTrainRoundPowerAction;
import gkmasmod.actions.common.ModifyDamageAction;
import gkmasmod.actions.common.ModifyDamageRandomEnemyAction;
import gkmasmod.cards.sense.Achievement;
import gkmasmod.powers.BurstSkillPower;
import gkmasmod.powers.GoodImpression;
import gkmasmod.utils.PlayerHelper;

public class WantToBecomeIdolAction extends AbstractGameAction {
    private AbstractCreature p;
    private int require;
    private int rate;
    private int hp;
    AbstractRelic relic = null;

    public WantToBecomeIdolAction(AbstractCreature p, int require, int rate, int hp,
                                  AbstractRelic relic) {
        this.p = p;
        this.require = require;
        this.rate = rate;
        this.hp = hp;
        this.relic = relic;
    }

    public void update() {
        int count = p.currentBlock;
        if(count < this.require){
            this.isDone = true;
            return;
        }
        if(relic.counter <= 0){
            this.isDone = true;
            return;
        }
        relic.flash();
        int damage = PlayerHelper.getPowerAmount(p, GoodImpression.POWER_ID);
        damage = (int) (damage *1.0F * rate/100);
        if(damage>0){
            if(p.isPlayer)
                addToBot(new ModifyDamageRandomEnemyAction(new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            else
                addToBot(new ModifyDamageAction(AbstractDungeon.player, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, relic));
        addToBot(new HealAction(p, p, this.hp));
        relic.counter--;
        if(relic.counter == 0){
            relic.grayscale = true;
        }
        this.isDone = true;
    }

}
