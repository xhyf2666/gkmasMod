package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.utils.NameHelper;

public class NotGoodTune extends AbstractPower {
    private static final String CLASSNAME = NotGoodTune.class.getSimpleName();
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    private static int limit = 9;

    public NotGoodTune(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.DEBUFF;
        this.amount = Amount;
        if(this.amount>limit)
            this.amount = limit;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if(this.amount>limit)
            this.amount = limit;
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount,limit);
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer){
        flash();
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        } else {
            addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }

    }


    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        float tmp = damage;
        if (type == DamageInfo.DamageType.NORMAL) {
            int count = this.owner.getPower(GreatNotGoodTune.POWER_ID)==null?0:this.owner.getPower(GreatNotGoodTune.POWER_ID).amount;
            if(count>0)
                tmp = tmp * (2F/3F-0.05F*this.amount);
            else
                tmp = tmp * 2F/3F;
            if(tmp<0)
                tmp = 0;
            return tmp;
        }
        return damage;
    }
}
