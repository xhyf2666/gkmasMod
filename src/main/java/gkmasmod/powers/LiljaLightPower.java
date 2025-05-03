package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class LiljaLightPower extends AbstractPower {
    private static final String CLASSNAME = LiljaLightPower.class.getSimpleName();
    // 能力的ID
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png","KiraKiraPrismPower");
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png","KiraKiraPrismPower");

    private static final int MAGIC = 50;

    public LiljaLightPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.amount = Amount;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }


    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],MAGIC,this.amount);
    }


    @Override
    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        if(this.owner.currentBlock>0){
            int goodImpressionAmount = PlayerHelper.getPowerAmount(this.owner, GoodImpression.POWER_ID);
            goodImpressionAmount = (int) (1.0F*MAGIC*goodImpressionAmount/100);
            if(goodImpressionAmount>0)
                addToBot(new ApplyPowerAction(this.owner, this.owner, new GoodImpression(this.owner, goodImpressionAmount), goodImpressionAmount));
        }
        else{
            addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount), this.amount));
        }
    }
}
