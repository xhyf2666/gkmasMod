package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.powers.TrainRoundLogicPower;
import gkmasmod.powers.TrainRoundSensePower;
import gkmasmod.utils.CommonEnum;


public class GainTrainRoundPowerWithoutEnergyAction extends AbstractGameAction {
    private static final float DUR = 0.25F;

    public GainTrainRoundPowerWithoutEnergyAction(AbstractCreature target, int amount) {
        this.target = target;
        this.amount = amount;
        this.duration = 0.25F;
        this.startDuration = 0.25F;
    }


    public GainTrainRoundPowerWithoutEnergyAction(AbstractCreature target, int amount, boolean superFast) {
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
                addToBot(new ApplyPowerAction(this.target, this.target, new TrainRoundSensePower(AbstractDungeon.player, this.amount), this.amount));
            }
            else if (type==CommonEnum.IdolType.LOGIC){
                addToBot(new ApplyPowerAction(this.target, this.target, new TrainRoundLogicPower(AbstractDungeon.player,  this.amount), this.amount));
            }
        }
        this.isDone = true;
    }
}
