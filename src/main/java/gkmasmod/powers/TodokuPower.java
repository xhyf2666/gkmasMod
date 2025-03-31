package gkmasmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.actions.GoodImpressionAutoDamageAction;
import gkmasmod.utils.NameHelper;

public class TodokuPower extends AbstractPower {
    private static final String CLASSNAME = TodokuPower.class.getSimpleName();
    // 能力的ID
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final PowerStrings powerStrings2 = CardCrawlGame.languagePack.getPowerStrings("BadImpression");

    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);
    Texture tex84 = ImageMaster.loadImage(path128);
    Texture tex32 = ImageMaster.loadImage(path48);
    Texture tex84_2 = ImageMaster.loadImage(String.format("gkmasModResource/img/powers/%s_84.png","BadImpression"));
    Texture tex32_2 = ImageMaster.loadImage(String.format("gkmasModResource/img/powers/%s_32.png","BadImpression"));

    public TodokuPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.priority = 9;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atEndOfRound() {
        this.flash();
        AbstractDungeon.player.loseBlock();
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }
}
