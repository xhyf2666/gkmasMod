package gkmasmod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import gkmasmod.actions.ModifyDamageRandomEnemyAction;
import gkmasmod.monster.exordium.MonsterNadeshiko;

public class ExplosiveSpecialPower extends AbstractPower {
    public static final String POWER_ID = "ExplosiveSpecialPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private static final int DAMAGE_AMOUNT = 30;
    private boolean isPlayer = false;

    public ExplosiveSpecialPower(AbstractCreature owner, int damage) {
        this.name = NAME;
        this.ID = "Explosive";
        this.owner = owner;
        this.amount = damage;
        this.updateDescription();
        this.loadRegion("explosive");
    }

    public  ExplosiveSpecialPower(AbstractCreature owner, int damage, boolean isPlayer) {
        this(owner, damage);
        this.isPlayer = isPlayer;
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[3] + 30 + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + 30 + DESCRIPTIONS[2];
        }

    }

    public void duringTurn() {
        if (this.amount == 1 && !this.owner.isDying) {
            this.addToBot(new VFXAction(new ExplosionSmallEffect(this.owner.hb.cX, this.owner.hb.cY), 0.1F));
            this.addToBot(new SuicideAction((AbstractMonster)this.owner));
            DamageInfo damageInfo = new DamageInfo(this.owner, 30, DamageInfo.DamageType.THORNS);
            if(this.isPlayer){
                this.addToBot(new ModifyDamageRandomEnemyAction(damageInfo, AbstractGameAction.AttackEffect.FIRE, null));
            }
            else{
                AbstractMonster nadeshiko = null;
                for(AbstractMonster monster: AbstractDungeon.getMonsters().monsters){
                    if(monster instanceof MonsterNadeshiko){
                        nadeshiko = monster;
                    }
                }
                if(nadeshiko!=null)
                    nadeshiko.getPower(GoodsMonopolyPower.POWER_ID).onSpecificTrigger();
                this.addToBot(new DamageAction(AbstractDungeon.player, damageInfo, AbstractGameAction.AttackEffect.FIRE, true));
            }
        } else {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, "Explosive", 1));
            this.updateDescription();
        }

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Explosive");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
