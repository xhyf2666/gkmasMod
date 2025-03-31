package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class FriendChinaPower2 extends AbstractPower {
    private static final String CLASSNAME = FriendChinaPower2.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png","CharmPerformancePower");
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png","CharmPerformancePower");


    private static final int MAGIC = 5;
    private static final int MAGIC2 = 3;

    public FriendChinaPower2(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],MAGIC);
    }

    @Override
    public void atStartOfTurn() {
        int count = PlayerHelper.getPowerAmount(this.owner, DexterityPower.POWER_ID);
        this.flash();
        addToTop(new GainBlockAction(AbstractDungeon.player,count+MAGIC));
    }
}
