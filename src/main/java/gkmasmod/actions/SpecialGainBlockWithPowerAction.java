package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import gkmasmod.utils.PlayerHelper;

import java.util.Iterator;


public class SpecialGainBlockWithPowerAction extends AbstractGameAction {
    private static final float DUR = 0.25F;
    private float dexterityEffectRate = 1.0F;

    public SpecialGainBlockWithPowerAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.BLOCK;
        this.duration = 0.25F;
        this.startDuration = 0.25F;
    }


    public void update() {
        if (!this.source.isDying && !this.source.isDead && this.duration == this.startDuration) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.source.hb.cX, this.source.hb.cY, AttackEffect.SHIELD));

            int DexterityPowerAmount = PlayerHelper.getPowerAmount(this.target, DexterityPower.POWER_ID);

            this.amount = this.amount + DexterityPowerAmount;

            if(this.source.getPower(FrailPower.POWER_ID) != null){
                this.amount = (int)((float)this.amount * 0.75F);
            }

            this.source.addBlock(this.amount);
            if(this.source.isPlayer){
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
