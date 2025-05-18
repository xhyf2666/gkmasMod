package gkmasmod.downfall.charbosses.actions.unique;


import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.stances.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.powers.StanceLock;
import gkmasmod.powers.StepOnStagePower;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.stances.FullPowerStance;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.StanceHelper;

import java.util.Iterator;

public class EnemyChangeStanceAction extends AbstractGameAction {
    private final String id;
    private AbstractEnemyStance newStance;

    public EnemyChangeStanceAction(String stanceId) {
        this.newStance = null;
        this.duration = Settings.ACTION_DUR_FAST;
        this.id = stanceId;
    }

    public EnemyChangeStanceAction(AbstractEnemyStance newStance) {
        this(newStance.ID);
        this.newStance = newStance;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractCharBoss.boss != null) {
                if (AbstractCharBoss.boss.hasPower("CannotChangeStancePower")) {
                    this.isDone = true;
                    return;
                }

                AbstractEnemyStance oldStance = (AbstractEnemyStance) AbstractCharBoss.boss.stance;

                if(oldStance.ID.equals(ENFullPowerStance.STANCE_ID)){
                    // 全力姿态下，不允许切换姿态
                    if(this.id.equals(ENFullPowerStance.STANCE_ID2)){
                        this.newStance = AbstractEnemyStance.getStanceFromName(EnNeutralStance.STANCE_ID);
                        for (AbstractPower p : AbstractCharBoss.boss.powers)
                            p.onChangeStance(oldStance, newStance);
                        for (AbstractRelic r : AbstractCharBoss.boss.relics)
                            r.onChangeStance(oldStance, newStance);
                        oldStance.onExitStance();
                        AbstractCharBoss.boss.stance = newStance;
                        newStance.onEnterStance();
                        AbstractCharBoss.boss.switchedStance();
                        AbstractCharBoss.boss.onStanceChange(this.id);
                    }
                    this.isDone = true;
                    return;
                }

                if(AbstractCharBoss.boss.hasPower(StanceLock.POWER_ID)){
                    this.isDone = true;
                    return;
                }

                //从悠闲进入其他姿态时
                if(oldStance.ID.equals(ENPreservationStance.STANCE_ID)){
                    if(StanceHelper.enemyIsInStance(oldStance,ENPreservationStance.STANCE_ID,2)){
                        //无法从悠闲进入温存
                        if(this.id.equals(ENPreservationStance.STANCE_ID)||this.id.equals(ENPreservationStance.STANCE_ID2)||this.id.equals(ENPreservationStance.STANCE_ID3)){
                            isDone = true;
                            return;
                        }
                        if(this.id.equals(ConcentrationStance.STANCE_ID2)){
                            if(AbstractCharBoss.boss.hasPower(StepOnStagePower.POWER_ID)){
                                AbstractCharBoss.boss.getPower(StepOnStagePower.POWER_ID).onSpecificTrigger();
                            }
                        }
                        newStance = AbstractEnemyStance.getStanceFromName(this.id);
                        for (AbstractPower p : AbstractCharBoss.boss.powers)
                            p.onChangeStance(oldStance, newStance);
                        for (AbstractRelic r : AbstractCharBoss.boss.relics)
                            r.onChangeStance(oldStance, newStance);
                        ENPreservationStance preservationStance = (ENPreservationStance) oldStance;
                        preservationStance.onExitSpecialStance(newStance);
                        AbstractCharBoss.boss.stance = newStance;
                        newStance.onEnterStance();
                        AbstractCharBoss.boss.switchedStance();
                        AbstractCharBoss.boss.onStanceChange(this.id);
                    }
                }

                if(this.id.equals(ENPreservationStance.STANCE_ID)||this.id.equals(ENPreservationStance.STANCE_ID2)){
                    if(oldStance.ID.equals(ENPreservationStance.STANCE_ID)){
                        //如果之前处于温存
                        ENPreservationStance current = (ENPreservationStance)oldStance;
                        if(StanceHelper.enemyIsInStance(oldStance,ENPreservationStance.STANCE_ID)){
                            //从温存1进入温存2
                            if(AbstractCharBoss.boss.hasPower(StepOnStagePower.POWER_ID)){
                                AbstractCharBoss.boss.getPower(StepOnStagePower.POWER_ID).onSpecificTrigger();
                            }
                        }
                        current.onEnterSameStance();
                    }
                    else{
                        if(this.id.equals(ENPreservationStance.STANCE_ID2)){
                            if(AbstractCharBoss.boss.hasPower(StepOnStagePower.POWER_ID)){
                                AbstractCharBoss.boss.getPower(StepOnStagePower.POWER_ID).onSpecificTrigger();
                            }
                        }
                        this.newStance = AbstractEnemyStance.getStanceFromName(this.id);
                        for (AbstractPower p : AbstractCharBoss.boss.powers)
                            p.onChangeStance(oldStance, newStance);
                        for (AbstractRelic r : AbstractCharBoss.boss.relics)
                            r.onChangeStance(oldStance, newStance);
                        oldStance.onExitStance();
                        AbstractCharBoss.boss.stance = newStance;
                        newStance.onEnterStance();
                        AbstractCharBoss.boss.switchedStance();
                        AbstractCharBoss.boss.onStanceChange(this.id);
                    }
                }
                else if(this.id.equals(ENPreservationStance.STANCE_ID3)){
                    if(oldStance.ID.equals(ENPreservationStance.STANCE_ID)){
                        //从温存进入悠闲
                        ENPreservationStance current = (ENPreservationStance)oldStance;
                        if(!StanceHelper.enemyIsInStance(oldStance,ENPreservationStance.STANCE_ID,2)){
                            //之前不处于悠闲
                            if(AbstractCharBoss.boss.hasPower(StepOnStagePower.POWER_ID)){
                                AbstractCharBoss.boss.getPower(StepOnStagePower.POWER_ID).onSpecificTrigger();
                            }
                        }
                        current.onEnterSpecialStance();
                    }
                    else{
                        //从其他姿态进入悠闲
                        this.newStance = AbstractEnemyStance.getStanceFromName(this.id);
                        for (AbstractPower p : AbstractCharBoss.boss.powers)
                            p.onChangeStance(oldStance, newStance);
                        for (AbstractRelic r : AbstractCharBoss.boss.relics)
                            r.onChangeStance(oldStance, newStance);
                        oldStance.onExitStance();
                        AbstractCharBoss.boss.stance = newStance;
                        newStance.onEnterStance();
                        AbstractCharBoss.boss.switchedStance();
                        AbstractCharBoss.boss.onStanceChange(this.id);
                    }
                }
                else if(this.id.equals(ENConcentrationStance.STANCE_ID)||this.id.equals(ENConcentrationStance.STANCE_ID2)){
                    if(oldStance.ID.equals(ENConcentrationStance.STANCE_ID)){
                        ENConcentrationStance current = (ENConcentrationStance)oldStance;
                        int stage = current.stage;
                        if(stage ==0){
                            //从强气1进入强气2
                            if(AbstractCharBoss.boss.hasPower(StepOnStagePower.POWER_ID)){
                                AbstractCharBoss.boss.getPower(StepOnStagePower.POWER_ID).onSpecificTrigger();
                            }
                        }
                        current.onEnterSameStance();
                    }
                    else{
                        if(this.id.equals(ENConcentrationStance.STANCE_ID2)){
                            if(AbstractCharBoss.boss.hasPower(StepOnStagePower.POWER_ID)){
                                AbstractCharBoss.boss.getPower(StepOnStagePower.POWER_ID).onSpecificTrigger();
                            }
                        }
                        this.newStance = AbstractEnemyStance.getStanceFromName(this.id);
                        for (AbstractPower p : AbstractCharBoss.boss.powers)
                            p.onChangeStance(oldStance, newStance);
                        for (AbstractRelic r : AbstractCharBoss.boss.relics)
                            r.onChangeStance(oldStance, newStance);
                        oldStance.onExitStance();
                        AbstractCharBoss.boss.stance = newStance;
                        newStance.onEnterStance();
                        AbstractCharBoss.boss.switchedStance();
                        AbstractCharBoss.boss.onStanceChange(this.id);
                    }
                }
                else if(this.id.equals(ENFullPowerStance.STANCE_ID)){
                    this.newStance = AbstractEnemyStance.getStanceFromName(this.id);
                    for (AbstractPower p : AbstractCharBoss.boss.powers)
                        p.onChangeStance(oldStance, newStance);
                    for (AbstractRelic r : AbstractCharBoss.boss.relics)
                        r.onChangeStance(oldStance, newStance);
                    oldStance.onExitStance();
                    AbstractCharBoss.boss.stance = newStance;
                    newStance.onEnterStance();
                    AbstractCharBoss.boss.switchedStance();
                    AbstractCharBoss.boss.onStanceChange(this.id);
                }
                else if(this.id.equals(ENFullPowerStance.STANCE_ID2)){
                    this.isDone = true;
                    return;
                }
                else{
                    if (!oldStance.ID.equals(this.id)) {
                        if (this.newStance == null) {
                            this.newStance = AbstractEnemyStance.getStanceFromName(this.id);
                        }

                        Iterator var2 = AbstractCharBoss.boss.powers.iterator();

                        while (var2.hasNext()) {
                            AbstractPower p = (AbstractPower) var2.next();
                            p.onChangeStance(oldStance, this.newStance);
                        }

                        var2 = AbstractCharBoss.boss.relics.iterator();

                        while (var2.hasNext()) {
                            AbstractRelic r = (AbstractRelic) var2.next();
                            r.onChangeStance(oldStance, this.newStance);
                        }

                        oldStance.onExitStance();
                        AbstractCharBoss.boss.stance = this.newStance;
                        this.newStance.onEnterStance();
                        AbstractCharBoss.boss.switchedStance();
                        AbstractCharBoss.boss.onStanceChange(this.id);
                    }
                }

                AbstractDungeon.onModifyPower();
                this.isDone = true;
                return;
            }
        }

        this.tickDuration();
    }
}
