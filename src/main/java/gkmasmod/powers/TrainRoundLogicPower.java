package gkmasmod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.relics.Calipers;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BlurPower;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.relics.*;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.ThreeSizeHelper;

public class TrainRoundLogicPower extends AbstractPower {
    private static final String CLASSNAME = TrainRoundLogicPower.class.getSimpleName();
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean isDone = true;

    private int baseMagicNumber = 100;
    private int decreaseMagicNumber = 20;
    private int finalMagicNumber = 60;
    private int currentMagicNumber = 100;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public TrainRoundLogicPower(AbstractCreature owner, int Amount) {
        this(owner, Amount, true);
    }

    public TrainRoundLogicPower(AbstractCreature owner, int Amount,boolean isDone) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isDone = isDone;

        this.isPostActionPower = true;

        this.amount = Amount;

        if(AbstractDungeon.actNum==1){
            baseMagicNumber = 110;
            finalMagicNumber = 70;
        }
        else if(AbstractDungeon.actNum==2){
            baseMagicNumber = 90;
            finalMagicNumber = 60;
        }
        else if(AbstractDungeon.actNum==3){
            baseMagicNumber = 80;
            finalMagicNumber = 50;
        }
        else{
            baseMagicNumber = 80;
            finalMagicNumber = 50;
        }
        this.currentMagicNumber = baseMagicNumber;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }

    @Override
    public void reducePower(int reduceAmount) {
        if(!AbstractDungeon.player.hasPower(TrainRoundProducePower.POWER_ID)&&this.amount-reduceAmount==1){
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
        this.description = String.format(DESCRIPTIONS[0], this.amount,this.currentMagicNumber, decreaseMagicNumber, finalMagicNumber);
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

    public AbstractPower updateMagic(int count){
        for(int i=0;i<count;i++){
            if(this.currentMagicNumber > finalMagicNumber){
                this.currentMagicNumber -= decreaseMagicNumber;
            }
        }
        this.updateDescription();
        return this;
    }

    @Override
    public void atEndOfRound() {
        int loseBlock = (int) (AbstractDungeon.player.currentBlock*1.0F*(100-this.currentMagicNumber)/100);
        if(loseBlock<0){
            loseBlock = -loseBlock;
            addToBot(new GainBlockAction(this.owner, this.owner, loseBlock));

            GkmasMod.loseBlock = true;
            return;
        }

        if(AbstractDungeon.player.hasRelic(Calipers.ID)){
            loseBlock = Math.min(loseBlock,15);
        }
        if(AbstractDungeon.player.hasPower(BlurPower.POWER_ID)||AbstractDungeon.player.hasPower(BarricadePower.POWER_ID)){
            loseBlock = 0;
        }
        if(loseBlock>0){
            AbstractDungeon.player.loseBlock(loseBlock);
        }
        updateMagic(1);
        GkmasMod.loseBlock = true;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        GkmasMod.loseBlock = false;
    }

    @Override
    public void onSpecificTrigger() {

    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(card instanceof AbstractBossCard)
            return;
        flash();
        if(this.amount == 1){
        }
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
        if(AbstractDungeon.player.hasPower(TrainRoundProducePower.POWER_ID)){
            return;
        }
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
        if(!this.isDone){
            ThreeSizeHelper.addThreeSize(true);
            this.isDone = true;
        }
    }


    public void onInitialApplication() {
    }




}
