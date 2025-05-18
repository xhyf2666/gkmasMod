package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.utils.NameHelper;

public class TrueLateBloomerPower extends AbstractPower {
    private static final String CLASSNAME = TrueLateBloomerPower.class.getSimpleName();
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    private static int GREAT_GOOD_TUNE = 1;

    private int magic2;

    public TrueLateBloomerPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;
        magic2 = 3;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public TrueLateBloomerPower setMagic2(int magic2){
        if(magic2<this.magic2)
            this.magic2 = magic2;
        this.updateDescription();
        return this;
    }

    public void updateDescription() {
        if(this.magic2>0)
            this.description = String.format(DESCRIPTIONS[0],this.amount,this.magic2,GREAT_GOOD_TUNE);
        else{
            this.description = String.format(DESCRIPTIONS[1],this.amount,GREAT_GOOD_TUNE);
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new ApplyPowerAction(owner, owner, new GoodTune(owner, this.amount), this.amount));
        if(this.magic2<=0){
            addToBot(new ApplyPowerAction(owner, owner, new GreatGoodTune(owner, GREAT_GOOD_TUNE), GREAT_GOOD_TUNE));
        }
        else{
            this.magic2--;
            this.updateDescription();
        }
    }

    @Override
    public void onSpecificTrigger() {
        addToBot(new ApplyPowerAction(owner, owner, new GoodTune(owner, this.amount), this.amount));
        if(this.magic2<=0){
            addToBot(new ApplyPowerAction(owner, owner, new GreatGoodTune(owner, GREAT_GOOD_TUNE), GREAT_GOOD_TUNE));
        }
        else{
            this.magic2--;
            this.updateDescription();
        }
    }

}
