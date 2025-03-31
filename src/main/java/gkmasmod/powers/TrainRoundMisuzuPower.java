package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import gkmasmod.actions.GashaAction;
import gkmasmod.cards.free.EatEmptyYourRefrigerator;
import gkmasmod.cards.free.Sleepy;
import gkmasmod.cards.special.Kiss;
import gkmasmod.cards.special.ResultWillNotChange;
import gkmasmod.cards.special.Rumor;
import gkmasmod.cards.special.WorkFighter;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.relics.*;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.ThreeSizeHelper;

public class TrainRoundMisuzuPower extends AbstractPower {
    private static final String CLASSNAME = TrainRoundMisuzuPower.class.getSimpleName();
    // 能力的ID
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int MAGIC = 1;
    private int MAGIC2 = 1;
    private int MAGIC3 = 2;
    private int MAGIC4 = 50;
    private int MAGIC5 = 1;
    private int MAGIC6 = 2;

    private int playCount = 0;

    private boolean isDone = true;


    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);;
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);;

    public TrainRoundMisuzuPower(AbstractCreature owner, int Amount) {
        this(owner,Amount,true);
    }

    public TrainRoundMisuzuPower(AbstractCreature owner, int Amount, boolean isDone) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.amount = Amount;
        this.isDone = isDone;

        this.isPostActionPower = true;

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
        this.description = String.format(DESCRIPTIONS[0], this.amount, MAGIC, MAGIC2, MAGIC3, MAGIC4, MAGIC5, MAGIC6);
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        flash();
//        if(EnergyPanel.totalCount > 0){
//            addToTop(new HealAction(this.owner, this.owner, 1));
//        }
    }

    @Override
    public void atStartOfTurn() {
        playCount=0;
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
        if(card.type== AbstractCard.CardType.STATUS||card.type== AbstractCard.CardType.CURSE){
            if(playCount<3){
                playCount++;
                return;
            }
        }
        flash();

        addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
    }

    public void onVictory() {
        if(!this.isDone){
            ThreeSizeHelper.addThreeSize(false);
            this.isDone = true;
        }
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if(card instanceof Sleepy||(card.type == AbstractCard.CardType.CURSE&&this.owner.hasPower(EvilSourcePower.POWER_ID))) {
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner,MAGIC),MAGIC));
        }
        if(card.cardID== ResultWillNotChange.ID){
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new DexterityPower(this.owner,MAGIC2),MAGIC2));
        }
        else if(card.cardID== EatEmptyYourRefrigerator.ID){
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new RegenPower(this.owner,MAGIC3),MAGIC3));
        }
        else if(card.cardID== WorkFighter.ID){
            AbstractDungeon.player.gainGold(MAGIC4);
            AbstractDungeon.effectList.add(new GainPennyEffect(this.owner, this.owner.hb.cX, this.owner.hb.cY, this.owner.hb.cX, this.owner.hb.cY, true));
        }
        else if(card.cardID== Kiss.ID){
            this.addToBot(new GashaAction(AbstractDungeon.player,true));
        }
        else if(card.cardID== Rumor.ID){
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new GoodTune(this.owner,MAGIC6),MAGIC6));
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




    public void onInitialApplication() {
    }

}
