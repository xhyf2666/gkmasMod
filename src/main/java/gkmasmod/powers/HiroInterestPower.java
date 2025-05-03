package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.utils.NameHelper;

public class HiroInterestPower extends AbstractPower {
    private static final String CLASSNAME = HiroInterestPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int limit = 2;

    private static final int MAGIC = 1;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png","WorkHardSPPower");
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png","WorkHardSPPower");

    public HiroInterestPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount=0;

        // 添加一大一小两张能力图
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    public HiroInterestPower setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.limit,MAGIC);
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        flashWithoutSound();
        this.amount++;
        if (this.amount == this.limit) {
            this.amount = 0;
            playApplyPowerSfx();
            this.flash();
            addToBot(new GainBlockWithPowerAction(this.owner,MAGIC));
        }
    }
}
