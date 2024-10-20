package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.utils.NameHelper;

public class WhatDoesSheDoPower extends AbstractPower {
    private static final String CLASSNAME = WhatDoesSheDoPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private AbstractCard card;

    private static int WhatDoesSheDoIDOffset;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public WhatDoesSheDoPower(AbstractCreature owner, int amount, AbstractCard card) {
        this.name = NAME;
        this.ID = POWER_ID + WhatDoesSheDoIDOffset;
        WhatDoesSheDoIDOffset++;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.card = card;

        loadRegion("nightmare");

        // 添加一大一小两张能力图
//        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.card.name);
    }

    public void atStartOfTurn(){
        AbstractDungeon.player.drawPile.moveToHand(this.card, AbstractDungeon.player.drawPile);
        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player,this));
    }
}
