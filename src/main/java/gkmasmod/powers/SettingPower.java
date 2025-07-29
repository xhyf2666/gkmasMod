package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.FullPowerValueAction;
import gkmasmod.actions.GrowAction;
import gkmasmod.cardGrowEffect.DamageGrow;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.relics.PlasticUmbrellaThatDay;
import gkmasmod.utils.NameHelper;

public class SettingPower extends AbstractPower {
    private static final String CLASSNAME = SettingPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public SettingPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.priority = 100;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if (this.amount <= 0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        if(this.amount>999)
            this.amount = 999;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {

    }

    @Override
    public void atStartOfTurnPostDraw() {
        if(this.owner instanceof AbstractPlayer){
            addToBot(new GrowAction(DamageGrow.growID, GrowAction.GrowType.allHand,this.amount));
        }
        else if(this.owner instanceof AbstractCharBoss){
            //
        }
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    //    @Override
//    public void onSpecificTrigger() {
//        if(this.owner instanceof AbstractCharBoss){
//            addToBot(new FullPowerValueAction(this.owner));
//        }
//    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {

    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount);
    }

}
