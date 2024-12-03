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
    // 能力的ID
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    private static int GOOD_TUNE = 2;
    private static int GREAT_GOOD_TUNE = 1;

    private int magic2;

    public TrueLateBloomerPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;
        magic2 = 3;

        // 添加一大一小两张能力图
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    public TrueLateBloomerPower setMagic2(int magic2){
        if(magic2<this.magic2)
            this.magic2 = magic2;
        return this;
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        if(this.magic2>0)
            this.description = String.format(DESCRIPTIONS[0],GOOD_TUNE,this.magic2,GREAT_GOOD_TUNE);
        else{
            this.description = String.format(DESCRIPTIONS[1],GOOD_TUNE,GREAT_GOOD_TUNE);
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new ApplyPowerAction(owner, owner, new GoodTune(owner, GOOD_TUNE), GOOD_TUNE));
        if(this.magic2<=0){
            addToBot(new ApplyPowerAction(owner, owner, new GreatGoodTune(owner, GREAT_GOOD_TUNE), GREAT_GOOD_TUNE));
        }
        else{
            this.magic2--;
        }
    }

}
