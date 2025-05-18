package gkmasmod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.utils.NameHelper;

public class NextXTurnMoreActionPower extends AbstractPower {
    private static final String CLASSNAME = NextXTurnMoreActionPower.class.getSimpleName();
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int moreAction =1;
    private static int AchievementIDOffset;

//    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
//    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public NextXTurnMoreActionPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID + AchievementIDOffset;
        AchievementIDOffset++;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = Amount;
        this.moreAction = 1;

        this.loadRegion("carddraw");
        this.updateDescription();
    }

    public NextXTurnMoreActionPower(AbstractCreature owner, int Amount, int moreAction) {
        this.name = NAME;
        this.ID = POWER_ID + AchievementIDOffset;
        AchievementIDOffset++;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = Amount;
        this.moreAction = moreAction;

        this.loadRegion("carddraw");
        this.updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        if(this.owner.isPlayer){
            if(this.amount > 1){
                addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
            }else{
                addToBot(new GainTrainRoundPowerAction(this.owner, this.moreAction));
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            }
        }
    }

    public void updateDescription() {
        if(this.moreAction>1)
            this.description = String.format(DESCRIPTIONS[1], this.amount,this.moreAction);
        else
            this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

}
