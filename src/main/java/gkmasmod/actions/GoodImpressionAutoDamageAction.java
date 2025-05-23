package gkmasmod.actions;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.patches.AbstractPlayerPatch;
import gkmasmod.powers.*;
import gkmasmod.utils.PlayerHelper;

import java.util.Iterator;

public class GoodImpressionAutoDamageAction extends AbstractGameAction {

    private AbstractCreature p;

    public GoodImpressionAutoDamageAction(AbstractCreature player) {
        this.p = player;
    }

    public void update() {
        System.out.println("GoodImpressionAutoDamageAction");
        int amount = this.p.getPower(GoodImpression.POWER_ID)==null?0:this.p.getPower(GoodImpression.POWER_ID).amount;
        int count = this.p.getPower(SSDSecretPower.POWER_ID)==null?0:this.p.getPower(SSDSecretPower.POWER_ID).amount;
        count++;
        int damage;
        float rate = 1.0f;
        if(p.hasPower(AllEffort.POWER_ID)){
            if(AbstractPlayerPatch.FinalCircleRoundField.finalCircleRound.get(p).size()>0){
                rate = (float) (AbstractPlayerPatch.FinalDamageRateField.finalDamageRate.get(p)*1.0f);
            }
        }
        if(p.hasPower(MasterRollerPower.POWER_ID))
            rate *= 0.5f;
        if(p.hasPower(MasterTreadmillPower.POWER_ID))
            rate *= 0.8f;
        if(p.hasPower(IdolExamPower.POWER_ID)){
            rate *= ((IdolExamPower)p.getPower(IdolExamPower.POWER_ID)).getRate();
        }
        int countNotGoodTune = 0;
        int countGreatNotGoodTune = 0;
        if(p.hasPower(NotGoodTune.POWER_ID))
            countNotGoodTune = p.getPower(NotGoodTune.POWER_ID).amount;
        if(p.hasPower(GreatNotGoodTune.POWER_ID))
            countGreatNotGoodTune = p.getPower(GreatNotGoodTune.POWER_ID).amount;
        if(countNotGoodTune>0){
            rate *= (0.667f-countNotGoodTune*0.05f*(countGreatNotGoodTune>0?1:0));
        }
        if (rate<0)
            rate = 0;

        for (int i = 0; i < count; i++) {
            damage = (int)(1.0f*amount * rate);
            if(damage>0){
                if(p.isPlayer){
                    if(p.hasPower(OccupyTheWorldPower.POWER_ID)){
                        addToTop(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.POISON));
                    }
                    else{
                        int tmp = damage;
                        AbstractCreature target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                        if(target!=null){
                            int tmp2 = PlayerHelper.getPowerAmount(target,StarNature.POWER_ID);
                            tmp *= 1.0F*(100-tmp2)/100;
                            addToTop(new DamageAction(target,new DamageInfo(p, tmp, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.POISON));
                        }
                    }
                }
                else{
                    addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(p, damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.POISON));
                }
            }
            else if(amount<0){
                if(p.isPlayer){
                    if(p.hasPower(OccupyTheWorldPower.POWER_ID)){
                        for(AbstractMonster m:AbstractDungeon.getCurrRoom().monsters.monsters){
                            addToBot(new HealAction(m, p, -damage));
                        }
                    }
                    else{
                        AbstractCreature mo = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                        if(mo!=null) {
                            addToBot(new HealAction(mo, p, -damage));
                        }
                    }
                }
                else{
                    addToBot(new HealAction(AbstractDungeon.player, p, -damage));
                }
            }
            amount--;
        }
        addToBot(new ApplyPowerAction(p,p,new GoodImpression(p,-count),-count));
        if(p.hasPower(IsENotAPower.POWER_ID)){
            int IsENotAPowerAmount = p.getPower(IsENotAPower.POWER_ID).amount;
            for (int i = 0; i < count-1; i++) {
                addToBot(new GainBlockWithPowerAction(p, p, IsENotAPowerAmount));
            }
        }
        this.isDone = true;
    }

}
