package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.StageDeviceFlowAction;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.NameHelper;

public class UmeStancePower extends AbstractPower {
    private static final String CLASSNAME = UmeStancePower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int limit = 10;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png","TopSkyPower");
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png","TopSkyPower");

    public UmeStancePower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = 0;

        // 添加一大一小两张能力图
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    public UmeStancePower(AbstractCreature owner,int limit) {
        this(owner);
        this.limit = limit;
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.limit);
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        flashWithoutSound();
        this.amount++;
        if (this.amount == this.limit) {
            this.amount = 0;
            playApplyPowerSfx();
            this.flash();
            if(AbstractDungeon.player.stance.ID.equals(PreservationStance.STANCE_ID)){
                addToBot(new ChangeStanceAction(ConcentrationStance.STANCE_ID));
            }
            else{
                addToBot(new ChangeStanceAction(PreservationStance.STANCE_ID));
            }
        }
    }
}
