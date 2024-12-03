package gkmasmod.powers;

import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
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
import gkmasmod.utils.NameHelper;

public class AutumnOfAppetitePower extends AbstractPower {
    private static final String CLASSNAME = AutumnOfAppetitePower.class.getSimpleName();
    // 能力的ID
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int count = 0;

    private static final int NUM = 3;


    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);;
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);;

    public AutumnOfAppetitePower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.isPostActionPower = true;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }


    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], NUM,1);
    }


    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        if(AbstractDungeon.player.hasPower(TrainRoundLogicPower.POWER_ID)||AbstractDungeon.player.hasPower(TrainRoundSensePower.POWER_ID)||AbstractDungeon.player.hasPower(TrainRoundAnomalyPower.POWER_ID)){
            count++;
        }
    }

    @Override
    public void atEndOfRound() {
        if(count==NUM){
            count=0;
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
        }
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(card instanceof AbstractBossCard)
            return;
        if(AbstractDungeon.player.hasPower(TrainRoundLogicPower.POWER_ID)||AbstractDungeon.player.hasPower(TrainRoundSensePower.POWER_ID)||AbstractDungeon.player.hasPower(TrainRoundAnomalyPower.POWER_ID)){
            count++;
            if(count==NUM){
                count=0;
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
            }
        }
    }



    public void onInitialApplication() {
    }




}
