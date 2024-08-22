package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
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
import org.lwjgl.Sys;

public class GoodImpression extends AbstractPower {
    private static final String CLASSNAME = GoodImpression.class.getSimpleName();
    // 能力的ID
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static boolean firstGet = true;

    String path128 = String.format("img/powers/%s_84.png",CLASSNAME);;
    String path48 = String.format("img/powers/%s_32.png",CLASSNAME);;

    public GoodImpression(AbstractCreature owner, int Amount) {
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
        if(this.amount == 0){
            firstGet = true;
        }
        else{
            firstGet = false;
        }
        super.stackPower(stackAmount);
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount,this.amount);
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        flash();

        if(this.amount > 0){
            if(firstGet)
                firstGet = false;
            else
                addToBot((AbstractGameAction)new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
            addToBot((AbstractGameAction)new DamageRandomEnemyAction(new DamageInfo(null, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.POISON));
        }
        else
            addToBot((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

}
