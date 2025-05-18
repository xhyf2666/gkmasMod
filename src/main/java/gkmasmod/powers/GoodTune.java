package gkmasmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DoubleDamagePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.cards.sense.UntilNowAndFromNow;
import gkmasmod.downfall.cards.sense.ENUntilNowAndFromNow;
import gkmasmod.relics.GreenUniformBracelet;
import gkmasmod.utils.NameHelper;
import org.lwjgl.Sys;

public class GoodTune extends AbstractPower {
    private static final String CLASSNAME = GoodTune.class.getSimpleName();
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final PowerStrings powerStrings2 = CardCrawlGame.languagePack.getPowerStrings("NegativeGoodTune");
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static float BASR_RATE = 1.5f;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);
    Texture tex84 = ImageMaster.loadImage(path128);
    Texture tex32 = ImageMaster.loadImage(path48);
    Texture tex84_2 = ImageMaster.loadImage(String.format("gkmasModResource/img/powers/%s_84.png","NegativeGoodTune"));
    Texture tex32_2 = ImageMaster.loadImage(String.format("gkmasModResource/img/powers/%s_32.png","NegativeGoodTune"));

    public GoodTune(AbstractCreature owner, int Amount) {
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

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if (this.amount == 0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        if(this.amount>99999)
            this.amount = 99999;
        if(this.amount<-99999)
            this.amount = -99999;
        updateDescription();
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer){
        flash();
        if(this.amount == 0){
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            }
        else
            addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if(card instanceof UntilNowAndFromNow||card instanceof ENUntilNowAndFromNow){
            return atDamageFinalGiveSpecial(damage,type,3.0F);
        }
        return atDamageFinalGive(damage,type);
    }

    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        float goodTuneEffectRate = 1.0F;
        if(this.amount>0){
            if (type == DamageInfo.DamageType.NORMAL) {
                int count = this.owner.getPower(GreatGoodTune.POWER_ID)==null?0:this.owner.getPower(GreatGoodTune.POWER_ID).amount;
                if(count > 0)
                    return damage * (1+(0.5F+amount*0.1F)*goodTuneEffectRate);
                else
                    return damage * (1+(0.5F*goodTuneEffectRate));
            }
        }
        else if(this.amount<0){
            if (type == DamageInfo.DamageType.NORMAL) {
                int count = this.owner.getPower(GreatGoodTune.POWER_ID)==null?0:this.owner.getPower(GreatGoodTune.POWER_ID).amount;
                if(count > 0){
                    int dmg = (int) (damage * (0.667F+amount*0.1F));
                    return dmg>0?dmg:0;
                }
                else
                    return damage * 0.667F;
            }
        }
        return damage;
    }

    public float atDamageFinalGiveSpecial(float damage, DamageInfo.DamageType type,float rate) {
        float goodTuneEffectRate = rate;
        if(this.amount>0){
            if (type == DamageInfo.DamageType.NORMAL) {
                int count = this.owner.getPower(GreatGoodTune.POWER_ID)==null?0:this.owner.getPower(GreatGoodTune.POWER_ID).amount;
                if(count > 0)
                    return damage * (1+(0.5F+amount*0.1F)*goodTuneEffectRate);
                else
                    return damage * (1+(0.5F*goodTuneEffectRate));
            }
        }
        else if(this.amount<0){
            if (type == DamageInfo.DamageType.NORMAL) {
                int count = this.owner.getPower(GreatGoodTune.POWER_ID)==null?0:this.owner.getPower(GreatGoodTune.POWER_ID).amount;
                if(count > 0){
                    int dmg = (int) (damage * (0.667F+amount*0.1F));
                    return dmg>0?dmg:0;
                }
                else
                    return damage * 0.667F;
            }
        }
        return damage;
    }
}
