package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.utils.NameHelper;

public class GoodImpressionDamageAddPower extends AbstractPower {
    private static final String CLASSNAME = GoodImpressionDamageAddPower.class.getSimpleName();
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int magic = 70;

    private boolean isTimeLimited = true;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public GoodImpressionDamageAddPower(AbstractCreature owner, int Amount, String ID) {
        this(owner, Amount, ID,true);
    }

    public GoodImpressionDamageAddPower(AbstractCreature owner, int Amount, String ID, boolean isTimeLimited) {
        this.name = NAME;
        this.ID = ID;
        this.isTimeLimited = isTimeLimited;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = Amount;
        if(!isTimeLimited){
            this.magic = 10* this.amount;
        }
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }

    public void updateDescription() {
        if(isTimeLimited)
            this.description = String.format(DESCRIPTIONS[0],this.amount,magic);
        else{
            this.description = String.format(DESCRIPTIONS[1],magic);
        }
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if(!isTimeLimited){
            this.magic = 10*this.amount;
        }
        if (this.amount == 0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    public int getMagic() {
        return magic;
    }

    public GoodImpressionDamageAddPower setMagic(int magic) {
        this.magic = magic;
        this.updateDescription();
        return this;
    }

    @Override
    public void atStartOfTurn() {
        if(isTimeLimited){
            flash();
            if(this.amount > 0){
                addToBot(new ReducePowerAction(this.owner, this.owner, ID, 1));
            }
            else
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
        }
    }
}
