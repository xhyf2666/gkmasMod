package gkmasmod.powers;


import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class IntangibleSpecialPower extends AbstractPower {
    public static final String POWER_ID = "IntangibleSpecialPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public boolean flag = false;

    public IntangibleSpecialPower(AbstractCreature owner, int turns) {
        this.name = NAME;
        this.ID = "IntangibleSpecialPower";
        this.owner = owner;
        this.amount = turns;
        this.updateDescription();
        this.loadRegion("intangible");
        this.priority = 75;
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_INTANGIBLE", 0.05F);
    }

    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        if(type == DamageType.THORNS && this.flag){
            return damage;
        }
        if (damage > 1.0F) {
            damage = 1.0F;
        }
        return damage;
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        this.flag = false;
    }

    @Override
    public void onSpecificTrigger() {
        this.flag = true;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public void atEndOfRound() {
        this.flash();
        if (this.amount == 0) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "IntangibleSpecialPower"));
        } else {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, "IntangibleSpecialPower", 1));
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("IntangiblePlayer");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
