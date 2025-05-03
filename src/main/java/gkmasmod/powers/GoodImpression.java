package gkmasmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.actions.GoodImpressionAutoDamageAction;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.patches.AbstractPlayerPatch;
import gkmasmod.relics.GreenUniformBracelet;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;
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
            if(this.owner.hasPower(IsENotAPower.POWER_ID)){
                int count = this.owner.getPower(IsENotAPower.POWER_ID).amount;
                addToBot(new GainBlockWithPowerAction(this.owner, this.owner, count));
            }
        }
        if (this.amount == 0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        if(this.amount>99999)
            this.amount = 99999;
        if(this.amount<-99999)
            this.amount = -99999;
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
        if(isPlayer){
            addToTop(new GoodImpressionAutoDamageAction(this.owner));
        }
        else{
            goodImpressionDamage();
        }
    }

    private void goodImpressionDamage(){
        int count = this.owner.getPower(SSDSecretPower.POWER_ID)==null?0:this.owner.getPower(SSDSecretPower.POWER_ID).amount;
        count++;
        int damage;
        float rate = 1.0f;
        if(this.owner.hasPower(IdolExamPower.POWER_ID)){
            rate *= ((IdolExamPower)this.owner.getPower(IdolExamPower.POWER_ID)).getRate();
        }
        int countNotGoodTune = 0;
        int countGreatNotGoodTune = 0;
        if(this.owner.hasPower(NotGoodTune.POWER_ID))
            countNotGoodTune = this.owner.getPower(NotGoodTune.POWER_ID).amount;
        if(this.owner.hasPower(GreatNotGoodTune.POWER_ID))
            countGreatNotGoodTune = this.owner.getPower(GreatNotGoodTune.POWER_ID).amount;
        if(countNotGoodTune>0){
            rate *= (0.667f-countNotGoodTune*0.05f*(countGreatNotGoodTune>0?1:0));
        }
        if (rate<0)
            rate = 0;

        for (int i = 0; i < count; i++) {
            damage = (int)(1.0f*amount * rate);
            if(damage>0){
                AbstractDungeon.player.damage(new DamageInfo(this.owner, damage, DamageInfo.DamageType.THORNS));
            }
            else if(amount<0){
                addToBot(new HealAction(AbstractDungeon.player, this.owner, -damage));
            }
            amount--;
        }
        updateDescription();
        if(this.owner.hasPower(IsENotAPower.POWER_ID)){
            int IsENotAPowerAmount = this.owner.getPower(IsENotAPower.POWER_ID).amount;
            for (int i = 0; i < count; i++) {
                addToBot(new GainBlockWithPowerAction(this.owner, this.owner, IsENotAPowerAmount));
            }
        }
    }

}
