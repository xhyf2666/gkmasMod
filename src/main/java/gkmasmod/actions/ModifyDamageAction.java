package gkmasmod.actions;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
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
import gkmasmod.cardGrowEffect.AttackTimeGrow;
import gkmasmod.cardGrowEffect.DamageGrow;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.patches.AbstractPlayerPatch;
import gkmasmod.powers.AllEffort;
import gkmasmod.powers.GoodTune;

import java.util.Iterator;

public class ModifyDamageAction extends AbstractGameAction {
    private DamageInfo info;
    private AbstractCard card;
    private AbstractCreature owner;
    private boolean countTime = false;
    private float goodTuneAffectRate = 1.0F;
    private float strengthAffectRate = 1.0F;

    public ModifyDamageAction(AbstractCreature target, DamageInfo info, AttackEffect effect) {
        this(target, info, effect, null);
    }

    public ModifyDamageAction(AbstractCreature target, DamageInfo info, AttackEffect effect, AbstractCard card) {
        this(target, info, effect, card, false);
    }

    public ModifyDamageAction(AbstractCreature target, DamageInfo info, AttackEffect effect, AbstractCard card, boolean countTime) {
        this(target, info, effect, card, countTime, 1.0F,1.0F);
    }

    public ModifyDamageAction(AbstractCreature target, DamageInfo info, AttackEffect effect, AbstractCard card, boolean countTime, float strengthAffectRate,float goodTuneAffectRate) {
        this.target = target;
        this.info = info;
        this.owner = info.owner;
        this.attackEffect = effect;
        this.card = card;
        this.countTime = countTime;
        this.strengthAffectRate = strengthAffectRate;
        this.goodTuneAffectRate = goodTuneAffectRate;
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

            if(this.owner instanceof AbstractCharBoss)
                damage = calculateDamage2(damage, this.target);
            else if(!this.owner.isPlayer)
                damage = calculateDamage3(damage, this.target);
            else
                damage = calculateDamage(damage, this.target);
            for (int i = 0; i < attackTime; i++) {
                DamageInfo info = new DamageInfo(this.owner, damage, this.info.type);
                if(this.info.name!=null)
                    info.name = this.info.name;
                addToBot(new DamageAction(this.target, info, this.attackEffect));
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
            for(var9 = this.owner.powers.iterator(); var9.hasNext(); ) {
                p = (AbstractPower)var9.next();
                if(p instanceof StrengthPower &&strengthAffectRate>1.0f){
                    float damageChange = p.atDamageGive(tmp, DamageInfo.DamageType.NORMAL)-tmp;
                    if(damageChange>0){
                        damageChange *= strengthAffectRate;
                        tmp += damageChange;
                    }
                }
                else{
                    tmp = p.atDamageGive(tmp, DamageInfo.DamageType.NORMAL);
                }
            }

            if(this.owner.isPlayer)
                tmp = player.stance.atDamageGive(tmp, DamageInfo.DamageType.NORMAL);

            for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = this.owner.powers.iterator(); var9.hasNext(); ) {
                p = (AbstractPower)var9.next();
                if(p instanceof GoodTune&&goodTuneAffectRate>1.0f){
                    float damageChange = p.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL)-tmp;
                    if(damageChange>0){
                        damageChange *= goodTuneAffectRate;
                        tmp += damageChange;
                    }
                }
                else{
                    tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL);
                }
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

    private int calculateDamage3(int baseDamage,AbstractCreature m) {
        if (m != null) {
            float tmp = (float)baseDamage;
            Iterator var9;

            AbstractPower p;
            for(var9 = this.owner.powers.iterator(); var9.hasNext(); tmp = p.atDamageGive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = this.owner.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            if(AbstractMonsterPatch.friendlyField.friendly.get(this.owner)){
                if(AbstractDungeon.player.hasPower(AllEffort.POWER_ID)){
                    if(AbstractPlayerPatch.FinalCircleRoundField.finalCircleRound.get(AbstractDungeon.player).size()>0){
                        tmp *= (float) (AbstractPlayerPatch.FinalDamageRateField.finalDamageRate.get(AbstractDungeon.player)*1.0f);
                    }
                }
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
