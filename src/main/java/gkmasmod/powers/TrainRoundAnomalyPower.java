package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import gkmasmod.actions.PotentialAbilityAction;
import gkmasmod.actions.GrowAction;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.growEffect.BlockGrow;
import gkmasmod.growEffect.DamageGrow;
import gkmasmod.relics.*;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.ThreeSizeHelper;

public class TrainRoundAnomalyPower extends AbstractPower {
    private static final String CLASSNAME = TrainRoundAnomalyPower.class.getSimpleName();
    // 能力的ID
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean isDone = true;


    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);;
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);;

    public TrainRoundAnomalyPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.isPostActionPower = true;
        this.isDone = true;
        this.amount = Amount;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }

    public TrainRoundAnomalyPower(AbstractCreature owner, int Amount, boolean isDone) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isDone = isDone;

        this.isPostActionPower = true;

        this.amount = Amount;
        this.priority = 99;

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

    public void atStartOfTurnPostDraw() {
        addToBot(new DrawCardAction(1));
        addToBot(new GrowAction(DamageGrow.growID, GrowAction.GrowType.allHand,2));
        addToBot(new GrowAction(BlockGrow.growID, GrowAction.GrowType.allHand,2));
        addToBot(new PotentialAbilityAction(1));
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount == 0){
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        flash();
        if(EnergyPanel.totalCount > 0){
            addToTop(new HealAction(this.owner, this.owner, 1));
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        flash();
        if(this.amount > 0){
            addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }
        else
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
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
        AbstractDungeon.player.heal((int) (1.0f*this.amount*2/3));
        if(!this.isDone){
            ThreeSizeHelper.addThreeSize(false);
            this.isDone = true;
        }
    }

    public void onRemove() {
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
        if(!this.isDone){
            ThreeSizeHelper.addThreeSize(true);
            this.isDone = true;
        }
    }


    public void onInitialApplication() {
    }




}
