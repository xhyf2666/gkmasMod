package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.TalkToTheHand;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.patches.AbstractPlayerPatch;
import gkmasmod.relics.*;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.ThreeSizeHelper;

public class TrainRoundSensePower extends AbstractPower {
    private static final String CLASSNAME = TrainRoundSensePower.class.getSimpleName();
    // 能力的ID
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int baseMagicNumber = 40;

    private int decreaseMagicNumber = 10;

    private int finalMagicNumber = 20;

    private int currentMagicNumber = baseMagicNumber;

    private boolean isDone = true;


    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);;
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);;

    public TrainRoundSensePower(AbstractCreature owner, int Amount) {
        this(owner,Amount,true);
    }

    public TrainRoundSensePower(AbstractCreature owner, int Amount,boolean isDone) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.amount = Amount;
        this.isDone = isDone;

        this.isPostActionPower = true;

        if(AbstractDungeon.actNum==1){
            baseMagicNumber = 50;
            decreaseMagicNumber = 10;
            finalMagicNumber = 30;
        }
        else if(AbstractDungeon.actNum==2){
            baseMagicNumber = 50;
            decreaseMagicNumber = 10;
            finalMagicNumber = 40;
        }
        else if(AbstractDungeon.actNum==3){
            baseMagicNumber = 60;
            decreaseMagicNumber = 10;
            finalMagicNumber = 40;
        }
        else{
            baseMagicNumber = 60;
            decreaseMagicNumber = 10;
            finalMagicNumber = 50;
        }

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }

    @Override
    public void reducePower(int reduceAmount) {
        if(this.amount-reduceAmount==1){
            if(AbstractDungeon.player.hasRelic(SidewalkResearchNotes.ID)){
                ((SidewalkResearchNotes)AbstractDungeon.player.getRelic(SidewalkResearchNotes.ID)).onTrainRoundRemove();
            }
            if(AbstractDungeon.player.hasRelic(LifeSizeLadyLip.ID)){
                ((LifeSizeLadyLip)AbstractDungeon.player.getRelic(LifeSizeLadyLip.ID)).onTrainRoundRemove();
            }
            if(AbstractDungeon.player.hasRelic(ChristmasLion.ID)){
                ((ChristmasLion)AbstractDungeon.player.getRelic(ChristmasLion.ID)).onTrainRoundRemove();
            }
        }
        if (this.amount-reduceAmount == 0){
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
        super.reducePower(reduceAmount);
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount == 0){
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount,currentMagicNumber,decreaseMagicNumber, finalMagicNumber);
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        flash();
//        if(EnergyPanel.totalCount > 0){
//            addToTop(new HealAction(this.owner, this.owner, 1));
//        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        flash();
        if(this.amount > 0){
            addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
            if(this.amount<4){
                for(AbstractPower power:AbstractDungeon.player.powers){
                    if(power instanceof MyPrideBigSisterPower){
                        power.flash();
                        power.onSpecificTrigger();
                    }
                }
            }
        }
        else
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(card instanceof AbstractBossCard)
            return;
        flash();

        addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
    }

    public void onVictory() {
//        AbstractDungeon.player.heal((int) (1.0f*this.amount*2/3));
        if(!this.isDone){
            ThreeSizeHelper.addThreeSize(false);
            this.isDone = true;
        }
    }

    public void onRemove() {
        if(this.amount == 1){
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
            for(AbstractPower power:AbstractDungeon.player.powers){
                if(power instanceof SayGoodbyeToDislikeMyselfPower){
                    power.flash();
                    power.onSpecificTrigger();
                }
                if(power instanceof ContinuousExpandWorldPower){
                    power.flash();
                    power.onSpecificTrigger();
                }
            }
        }
        if(!this.isDone){
            ThreeSizeHelper.addThreeSize(true);
            this.isDone = true;
        }
    }

    public void atStartOfTurn() {
        if(this.currentMagicNumber > this.finalMagicNumber){
            this.currentMagicNumber -= decreaseMagicNumber;

            updateDescription();
        }
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(info.name!=null&&(info.name.equals(TopWisdomPlusPower.POWER_ID)||info.name.equals(TopWisdomPower.POWER_ID))){
            return;
        }
        int count = (int) (1.0F*damageAmount * currentMagicNumber /100);
        if(AbstractDungeon.player instanceof IdolCharacter){
            IdolCharacter idol = (IdolCharacter) AbstractDungeon.player;
            if(AbstractPlayerPatch.FinalCircleRoundField.finalCircleRound.get(AbstractDungeon.player).size()>0){
                count = (int) (1.0f*count / (AbstractPlayerPatch.FinalDamageRateField.finalDamageRate.get(AbstractDungeon.player)*1.0f));
            }
        }
        addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, count));
    }



    public void onInitialApplication() {
    }

}
