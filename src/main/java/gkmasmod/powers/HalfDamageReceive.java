package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.utils.NameHelper;

public class HalfDamageReceive extends AbstractPower {
    private static final String CLASSNAME = HalfDamageReceive.class.getSimpleName();
    // 能力的ID
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);;
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);;

    public HalfDamageReceive(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.amount = Amount;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }


    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        return damage * 0.5F;
    }

    public void atEndOfRound(){
        flash();

        if(this.amount > 0){
                addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }
        else
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if(info.type == DamageInfo.DamageType.HP_LOSS){
            return (int) (damageAmount*0.5F);
        }
        return damageAmount;
    }



}
