package gkmasmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.actions.GoodImpressionAutoDamageAction;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.relics.GreenUniformBracelet;
import gkmasmod.utils.NameHelper;
import org.lwjgl.Sys;

public class GoodImpression extends AbstractPower {
    private static final String CLASSNAME = GoodImpression.class.getSimpleName();
    // 能力的ID
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final PowerStrings powerStrings2 = CardCrawlGame.languagePack.getPowerStrings("BadImpression");

    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);
    Texture tex84 = ImageMaster.loadImage(path128);
    Texture tex32 = ImageMaster.loadImage(path48);
    Texture tex84_2 = ImageMaster.loadImage(String.format("gkmasModResource/img/powers/%s_84.png","BadImpression"));
    Texture tex32_2 = ImageMaster.loadImage(String.format("gkmasModResource/img/powers/%s_32.png","BadImpression"));

    public GoodImpression(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.amount = Amount;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
        this.canGoNegative = true;
    }

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if(stackAmount<0){
            if(AbstractDungeon.player.hasPower(IsENotAPower.POWER_ID)){
                int count = AbstractDungeon.player.getPower(IsENotAPower.POWER_ID).amount;
                addToBot(new GainBlockWithPowerAction(this.owner, this.owner, count));
            }
        }
        if (this.amount == 0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        if(this.amount>9999)
            this.amount = 9999;
        if(this.amount<-9999)
            this.amount = -9999;
        updateDescription();
    }

    public void updateDescription() {
        if(amount>=0){
            this.name = NAME;
            this.description = String.format(DESCRIPTIONS[0], this.amount,this.amount);
            this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
            this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        }
        else{
            this.name = powerStrings2.NAME;
            this.description = String.format(powerStrings2.DESCRIPTIONS[0], this.amount,this.amount*-1);
            this.region128 = new TextureAtlas.AtlasRegion(tex84_2, 0, 0, 84, 84);
            this.region48 = new TextureAtlas.AtlasRegion(tex32_2, 0, 0, 32, 32);
        }
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        flash();
        addToBot(new GoodImpressionAutoDamageAction(this.owner));
    }


}
