package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.GrowAction;
import gkmasmod.cardGrowEffect.BlockGrow;
import gkmasmod.cardGrowEffect.DamageGrow;
import gkmasmod.utils.NameHelper;

public class ThunderWillStopSPPower extends AbstractPower {
    private static final String CLASSNAME = ThunderWillStopSPPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    private static final int magic1 = 2;
    private static final int magic2 = 3;

    public ThunderWillStopSPPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount*magic1, this.amount*magic2);
    }

    @Override
    public void onSpecificTrigger() {
        addToBot(new GrowAction(DamageGrow.growID, GrowAction.GrowType.allHand, this.amount*magic1));
        addToBot(new GrowAction(DamageGrow.growID, GrowAction.GrowType.allTempSave, this.amount*magic1));
        addToBot(new GrowAction(BlockGrow.growID, GrowAction.GrowType.allHand, this.amount*magic2));
        addToBot(new GrowAction(BlockGrow.growID, GrowAction.GrowType.allTempSave, this.amount*magic2));
    }
}
