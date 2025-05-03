package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.growEffect.EnergyGrow;
import gkmasmod.utils.GrowHelper;
import gkmasmod.utils.NameHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class RinamiSecretPower extends AbstractPower {
    private static final String CLASSNAME = RinamiSecretPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean flag = false;

    private static final int LIMIT = 3;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png","SaySomethingToYouPower");
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png","SaySomethingToYouPower");

    public RinamiSecretPower(AbstractCreature owner) {
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

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],LIMIT);
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        flashWithoutSound();
        this.amount++;
        if(this.amount==LIMIT){
            AbstractCard card1 = card.makeCopy();
            GrowHelper.grow(card1, EnergyGrow.growID,1);
            addToBot(new MakeTempCardInHandAction(card1));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        this.amount=0;
    }
}
