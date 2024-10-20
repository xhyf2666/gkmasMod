package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.powers.TrainRoundLogicPower;
import gkmasmod.powers.TrainRoundSensePower;
import gkmasmod.utils.CommonEnum;

import java.util.Iterator;


public class GainTrainRoundPowerAction extends AbstractGameAction {
    private static final float DUR = 0.25F;

    public GainTrainRoundPowerAction(AbstractCreature target,int amount) {
        this.target = target;
        this.amount = amount;
        this.duration = 0.25F;
        this.startDuration = 0.25F;
    }


    public GainTrainRoundPowerAction(AbstractCreature target,int amount, boolean superFast) {
        this(target,amount);
        if (superFast) {
            this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
        }

    }


    public void update() {
        if(this.target instanceof IdolCharacter){
            IdolCharacter idol = (IdolCharacter) this.target;
            CommonEnum.IdolType type = idol.idolData.getType(idol.skinIndex);
            if(type==CommonEnum.IdolType.SENSE){
                if(idol.hasPower(TrainRoundSensePower.POWER_ID)){
                    addToBot(new ApplyPowerAction(this.target, this.target, new TrainRoundSensePower(AbstractDungeon.player, this.amount), this.amount));
                }
            }
            else if (type==CommonEnum.IdolType.LOGIC){
                if(idol.hasPower(TrainRoundLogicPower.POWER_ID)){
                    addToBot(new ApplyPowerAction(this.target, this.target, new TrainRoundLogicPower(AbstractDungeon.player,  this.amount), this.amount));
                }
            }
        }
        addToBot(new GainEnergyAction(this.amount));
        this.isDone = true;
    }
}
