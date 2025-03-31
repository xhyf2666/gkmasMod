package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.utils.NameHelper;

public class KotoneCharmPower extends AbstractPower {
    private static final String CLASSNAME = KotoneCharmPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int limit = 2;

    private int count = 0;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png","FantasyCharmPower");
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png","FantasyCharmPower");

    public KotoneCharmPower(AbstractCreature owner,int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;

        // 添加一大一小两张能力图
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    public KotoneCharmPower(AbstractCreature owner,int amount,int limit) {
        this(owner,amount);
        this.limit = limit;
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.limit,this.amount);
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        this.flash();
        this.count++;
        if(this.count >= this.limit) {
            this.count = 0;
            this.flashWithoutSound();
            addToBot(new ApplyPowerAction(this.owner, this.owner, new GoodImpression(this.owner, this.amount), this.amount));
        }
    }
}
