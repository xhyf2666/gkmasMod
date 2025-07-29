package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import gkmasmod.utils.NameHelper;

public class StarDustPower extends AbstractIncreaseModifyPower {
    private static final String CLASSNAME = StarDustPower.class.getSimpleName();
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public static int MAGIC = 0;
    public static int MAGIC2 = 50;
    public static final String targetID = GoodImpression.POWER_ID;

    private static int offset;

    public StarDustPower(AbstractCreature owner, int amount) {
        super(targetID,MAGIC,MAGIC2);
        this.name = NAME;
        this.ID = POWER_ID + offset;
        offset++;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount,MAGIC2);
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer){
        flash();
        if(this.amount == 0){
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
        else
            addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
    }

}
