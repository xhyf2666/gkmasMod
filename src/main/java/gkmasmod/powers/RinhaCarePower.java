package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
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
import gkmasmod.patches.AbstractPlayerPatch;
import gkmasmod.utils.NameHelper;

public class RinhaCarePower extends AbstractPower {
    private static final String CLASSNAME = RinhaCarePower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final int MAGIC = 20;

    private static final int MAGIC2 = 3;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public RinhaCarePower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = 0;
        this.priority = 4;

        // 添加一大一小两张能力图
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],MAGIC);
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (info.output > 0) {
            int count = (int) (info.output * MAGIC *1.0F / 100);
            if(AbstractPlayerPatch.FinalCircleRoundField.finalCircleRound.get(AbstractDungeon.player).size()>0){
                count/= (AbstractPlayerPatch.FinalDamageRateField.finalDamageRate.get(AbstractDungeon.player)*1.0f);
            }
            if(count == 0)
                count = 1;
            AbstractDungeon.player.currentBlock += count;
            if(AbstractDungeon.player.currentBlock > 99999){
                AbstractDungeon.player.currentBlock = 99999;
            }

        }

        return damageAmount;
    }

}
