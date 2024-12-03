package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.utils.NameHelper;

public class StrongPower extends AbstractPower {
    private static final String CLASSNAME = StrongPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);;
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);;

    public StrongPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        clearState();

        // 添加一大一小两张能力图
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0]);
    }

    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        return damage * 1.5F;
    }

    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        return damage * 1.5F;
    }

    private void clearState(){
        if(this.owner.hasPower(FullPower.POWER_ID)){
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, FullPower.POWER_ID));
        }
        if(this.owner.hasPower(Tenderness.POWER_ID)){
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, Tenderness.POWER_ID));
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        addToBot(new LoseHPAction(AbstractDungeon.player, this.owner, 1));
    }
}
