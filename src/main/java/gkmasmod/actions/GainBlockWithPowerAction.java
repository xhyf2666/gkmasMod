package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.NoBlockPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import gkmasmod.powers.AnxietyPower;

import java.util.Iterator;


public class GainBlockWithPowerAction extends AbstractGameAction {
    private static final float DUR = 0.25F;
    private float dexterityEffectRate = 1.0F;

    public GainBlockWithPowerAction(AbstractCreature target, int amount) {
        this.target = target;
        this.amount = amount;
        this.actionType = ActionType.BLOCK;
        this.duration = 0.25F;
        this.startDuration = 0.25F;
    }

    public GainBlockWithPowerAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.BLOCK;
        this.duration = 0.25F;
        this.startDuration = 0.25F;
    }

    public GainBlockWithPowerAction(AbstractCreature target, AbstractCreature source, int amount,float dexterityEffectRate) {
        this(target, source, amount);
        this.dexterityEffectRate = dexterityEffectRate;
    }

//    public GainBlockWithPowerAction(AbstractCreature target, int amount, boolean superFast) {
//        this(target, amount);
//        if (superFast) {
//            this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
//        }
//
//    }

    public GainBlockWithPowerAction(AbstractCreature target, AbstractCreature source, int amount, boolean superFast) {
        this(target, source, amount);
        if (superFast) {
            this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
        }

    }

    public void update() {
        if (!this.target.isDying && !this.target.isDead && this.duration == this.startDuration) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SHIELD));

            int DexterityPowerAmount = this.target.getPower(DexterityPower.POWER_ID) == null ? 0 : this.target.getPower(DexterityPower.POWER_ID).amount;
            DexterityPowerAmount = (int) (1.0F * DexterityPowerAmount * dexterityEffectRate);
            this.amount = this.amount + DexterityPowerAmount > 0 ? this.amount + DexterityPowerAmount : 0;

            if(this.target.getPower(FrailPower.POWER_ID) != null){
                this.amount = (int)((float)this.amount * 0.75F);
            }

            if(this.target.hasPower(AnxietyPower.POWER_ID)){
                this.amount = (int)((float)this.amount * 0.5F);
            }

            if(this.target.hasPower(NoBlockPower.POWER_ID)){
                this.amount = 0;
            }

            this.target.addBlock(this.amount);
            if(this.target.isPlayer){
                Iterator var1 = AbstractDungeon.player.hand.group.iterator();

                while(var1.hasNext()) {
                    AbstractCard c = (AbstractCard)var1.next();
                    c.applyPowers();
                }
            }
        }

        this.tickDuration();
    }
}
