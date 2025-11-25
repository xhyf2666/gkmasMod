package gkmasmod.powers.increaseModifyPower;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import gkmasmod.powers.FullPower;
import gkmasmod.utils.NameHelper;
import org.apache.commons.lang3.StringUtils;

public class FullPowerAddRatePower extends AbstractIncreaseModifyPower {
    private static final String CLASSNAME = FullPowerAddRatePower.class.getSimpleName();
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public static final String targetID = FullPower.POWER_ID;

    public static final int RATE = 50;

    public FullPowerAddRatePower(AbstractCreature owner, int amount) {
        this(owner, amount, RATE, null);
    }

    public FullPowerAddRatePower(AbstractCreature owner, int amount, int rate) {
        this(owner, amount, rate, null);
    }

    public FullPowerAddRatePower(AbstractCreature owner, int amount, int rate, String source){
        super(targetID, 0, rate, amount);
        this.name = NAME;
        if(StringUtils.isEmpty(source)){
            this.ID = POWER_ID;
        }
        else{
            this.ID = POWER_ID + "_" + source;
        }
        this.owner = owner;
        this.type = PowerType.BUFF;
        if(amount != AbstractIncreaseModifyPower.MAGIC)
            this.amount = amount;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }


    public void updateDescription() {
        if(this.affectTurn==MAGIC){
            this.description = String.format(DESCRIPTIONS[1], this.affectRate);
        }
        else{
            this.description = String.format(DESCRIPTIONS[0],this.amount, this.affectRate);
        }    }

}
