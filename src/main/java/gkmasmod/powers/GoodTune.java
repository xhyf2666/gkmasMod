package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
import com.megacrit.cardcrawl.powers.DoubleDamagePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.relics.GreenUniformBracelet;
import gkmasmod.utils.NameHelper;
import org.lwjgl.Sys;

public class GoodTune extends AbstractPower {
    private static final String CLASSNAME = GoodTune.class.getSimpleName();
    // 能力的ID
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static boolean firstGet = true;

    private static float BASR_RATE = 1.5f;

    String path128 = String.format("img/powers/%s_84.png",CLASSNAME);;
    String path48 = String.format("img/powers/%s_32.png",CLASSNAME);;

    public GoodTune(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = Amount;

        // 添加一大一小两张能力图
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    public void stackPower(int stackAmount) {
        if(this.amount == 0){
            firstGet = true;
        }
        else{
            firstGet = false;
        }
        super.stackPower(stackAmount);
        if (this.amount == 0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    public void atEndOfRound() {
        flash();

        if(this.amount > 0){
            if(firstGet)
                firstGet = false;
            else
                addToBot((AbstractGameAction)new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
            }
        else
            addToBot((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }


    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            int count = AbstractDungeon.player.getPower(GreatGoodTune.POWER_ID)==null?0:AbstractDungeon.player.getPower(GreatGoodTune.POWER_ID).amount;
            if(count > 0)
                return damage * (1.5F+amount*0.1F);
            else
                return damage * 1.5F;
        }
        return damage;
    }

    public void onVictory() {
        firstGet = true;
    }

    public void atStartOfTurn() {
        if (this.amount == 0) {
            firstGet = true;
        }
    }

    public void onInitialApplication() {
        firstGet = true;
    }
}
