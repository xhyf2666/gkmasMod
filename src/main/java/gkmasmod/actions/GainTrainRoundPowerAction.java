package gkmasmod.actions;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.downfall.charbosses.actions.common.EnemyGainEnergyAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.powers.*;
import gkmasmod.relics.*;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.CommonEnum;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.PlayerHelper;


public class GainTrainRoundPowerAction extends AbstractGameAction {
    private static final float DUR = 0.25F;
    private boolean withEnergy = true;

    public GainTrainRoundPowerAction(AbstractCreature target,int amount) {
        this(target,amount,true);
    }

    public GainTrainRoundPowerAction(AbstractCreature target,int amount,boolean withEnergy) {
        this.target = target;
        this.amount = amount;
        this.duration = 0.25F;
        this.startDuration = 0.25F;
        this.withEnergy = withEnergy;
    }

    public void update() {
        if (this.withEnergy) {
            if(this.target.isPlayer)
                addToTop(new GainEnergyAction(this.amount));
            else{
                addToTop(new EnemyGainEnergyAction(this.amount));
            }
            if(this.target.hasPower(TimeLoopPower.POWER_ID)){
                this.target.getPower(TimeLoopPower.POWER_ID).onSpecificTrigger();
            }
        }
        if(this.target.isPlayer){
            if(this.target instanceof IdolCharacter){
                IdolCharacter idol = (IdolCharacter) this.target;
                CommonEnum.IdolType type = idol.idolData.getType(idol.skinIndex);
                int count =0;

                if(type==CommonEnum.IdolType.SENSE){
                    if(this.target.hasPower(TrainRoundSensePower.POWER_ID))
                        addToBot(new ApplyPowerAction(this.target, this.target, new TrainRoundSensePower(AbstractDungeon.player, this.amount), this.amount));
                    count = PlayerHelper.getPowerAmount(AbstractDungeon.player, TrainRoundSensePower.POWER_ID);
                    if(count ==1){
                        if(AbstractDungeon.player.hasRelic(SidewalkResearchNotes.ID)){
                            ((SidewalkResearchNotes)AbstractDungeon.player.getRelic(SidewalkResearchNotes.ID)).onTrainRoundRemove();
                        }
                        if(AbstractDungeon.player.hasRelic(LifeSizeLadyLip.ID)){
                            ((LifeSizeLadyLip)AbstractDungeon.player.getRelic(LifeSizeLadyLip.ID)).onTrainRoundRemove();
                        }
                        if(AbstractDungeon.player.hasRelic(UltimateSleepMask.ID)){
                            ((UltimateSleepMask)AbstractDungeon.player.getRelic(UltimateSleepMask.ID)).onTrainRoundRemove();
                        }
                        if(AbstractDungeon.player.hasRelic(HeartFlutteringCup.ID)){
                            ((HeartFlutteringCup)AbstractDungeon.player.getRelic(HeartFlutteringCup.ID)).onTrainRoundRemove();
                        }
                        if(AbstractDungeon.player.hasRelic(FirstHeartProofChina.ID)){
                            ((FirstHeartProofChina)AbstractDungeon.player.getRelic(FirstHeartProofChina.ID)).onTrainRoundRemove();
                        }
                        if(AbstractDungeon.player.hasRelic(ChristmasLion.ID)){
                            ((ChristmasLion)AbstractDungeon.player.getRelic(ChristmasLion.ID)).onTrainRoundRemove();
                        }
                    }
                }
                else if (type==CommonEnum.IdolType.LOGIC){
                    if(this.target.hasPower(TrainRoundLogicPower.POWER_ID))
                        addToBot(new ApplyPowerAction(this.target, this.target, new TrainRoundLogicPower(AbstractDungeon.player, this.amount), this.amount));
                    count = PlayerHelper.getPowerAmount(AbstractDungeon.player, TrainRoundLogicPower.POWER_ID);
                    if(count ==1){
                        if(AbstractDungeon.player.hasRelic(SidewalkResearchNotes.ID)){
                            ((SidewalkResearchNotes)AbstractDungeon.player.getRelic(SidewalkResearchNotes.ID)).onTrainRoundRemove();
                        }
                        if(AbstractDungeon.player.hasRelic(LifeSizeLadyLip.ID)){
                            ((LifeSizeLadyLip)AbstractDungeon.player.getRelic(LifeSizeLadyLip.ID)).onTrainRoundRemove();
                        }
                        if(AbstractDungeon.player.hasRelic(UltimateSleepMask.ID)){
                            ((UltimateSleepMask)AbstractDungeon.player.getRelic(UltimateSleepMask.ID)).onTrainRoundRemove();
                        }
                        if(AbstractDungeon.player.hasRelic(HeartFlutteringCup.ID)){
                            ((HeartFlutteringCup)AbstractDungeon.player.getRelic(HeartFlutteringCup.ID)).onTrainRoundRemove();
                        }
                        if(AbstractDungeon.player.hasRelic(FirstHeartProofChina.ID)){
                            ((FirstHeartProofChina)AbstractDungeon.player.getRelic(FirstHeartProofChina.ID)).onTrainRoundRemove();
                        }
                        if(AbstractDungeon.player.hasRelic(ChristmasLion.ID)){
                            ((ChristmasLion)AbstractDungeon.player.getRelic(ChristmasLion.ID)).onTrainRoundRemove();
                        }
                    }
                }
                else if (type == CommonEnum.IdolType.ANOMALY){
                    if(this.target.hasPower(TrainRoundAnomalyPower.POWER_ID))
                        addToBot(new ApplyPowerAction(this.target, this.target, new TrainRoundAnomalyPower(AbstractDungeon.player, this.amount), this.amount));
                    count = PlayerHelper.getPowerAmount(AbstractDungeon.player, TrainRoundAnomalyPower.POWER_ID);
                    if(count ==1){
                        if(AbstractDungeon.player.hasRelic(SidewalkResearchNotes.ID)){
                            ((SidewalkResearchNotes)AbstractDungeon.player.getRelic(SidewalkResearchNotes.ID)).onTrainRoundRemove();
                        }
                        if(AbstractDungeon.player.hasRelic(LifeSizeLadyLip.ID)){
                            ((LifeSizeLadyLip)AbstractDungeon.player.getRelic(LifeSizeLadyLip.ID)).onTrainRoundRemove();
                        }
                        if(AbstractDungeon.player.hasRelic(UltimateSleepMask.ID)){
                            ((UltimateSleepMask)AbstractDungeon.player.getRelic(UltimateSleepMask.ID)).onTrainRoundRemove();
                        }
                        if(AbstractDungeon.player.hasRelic(HeartFlutteringCup.ID)){
                            ((HeartFlutteringCup)AbstractDungeon.player.getRelic(HeartFlutteringCup.ID)).onTrainRoundRemove();
                        }
                        if(AbstractDungeon.player.hasRelic(FirstHeartProofChina.ID)){
                            ((FirstHeartProofChina)AbstractDungeon.player.getRelic(FirstHeartProofChina.ID)).onTrainRoundRemove();
                        }
                        if(AbstractDungeon.player.hasRelic(ChristmasLion.ID)){
                            ((ChristmasLion)AbstractDungeon.player.getRelic(ChristmasLion.ID)).onTrainRoundRemove();
                        }
                    }
                }

            }
            else if(this.target instanceof MisuzuCharacter){
                addToTop(new ApplyPowerAction(this.target,this.target,new TrainRoundMisuzuPower(AbstractDungeon.player,this.amount),this.amount));
            }
            else{
                CommonEnum.IdolType type = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getType(SkinSelectScreen.Inst.skinIndex);
                if(type==CommonEnum.IdolType.SENSE){
                    addToBot(new ApplyPowerAction(this.target, this.target, new TrainRoundSensePower(AbstractDungeon.player, this.amount), this.amount));
                }
                else if (type==CommonEnum.IdolType.LOGIC){
                    addToBot(new ApplyPowerAction(this.target, this.target, new TrainRoundLogicPower(AbstractDungeon.player,  this.amount), this.amount));
                }
                else if (type==CommonEnum.IdolType.ANOMALY){
                    addToBot(new ApplyPowerAction(this.target, this.target, new TrainRoundAnomalyPower(AbstractDungeon.player,  this.amount), this.amount));
                }
            }
            for(AbstractMonster mo: AbstractDungeon.getMonsters().monsters){
                if(!mo.isDeadOrEscaped()&& mo.hasPower(SenaMoreActionPower.POWER_ID)){
                    mo.getPower(SenaMoreActionPower.POWER_ID).onSpecificTrigger();
                }
            }
        }

        this.isDone = true;
    }
}
