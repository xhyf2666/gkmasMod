package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.cards.free.JustAngel;
import gkmasmod.cards.free.JustDemon;
import gkmasmod.downfall.cards.free.ENJustAngel;
import gkmasmod.downfall.cards.free.ENJustDemon;
import gkmasmod.downfall.charbosses.actions.common.EnemyMakeTempCardInHandAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.utils.NameHelper;

public class SurgingEmotionPower extends AbstractIncreaseModifyPower {
    private static final String CLASSNAME = SurgingEmotionPower.class.getSimpleName();
    // 能力的ID
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public static int MAGIC = 0;
    public static int MAGIC2 = 100;
    public static final String targetID = GoodImpression.POWER_ID;

    private static int offset;

    public SurgingEmotionPower(AbstractCreature owner,int amount) {
        super(targetID,MAGIC,MAGIC2);
        this.name = NAME;
        this.ID = POWER_ID + offset;
        offset++;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;

        // 添加一大一小两张能力图
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount,MAGIC2);
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer){
        flash();
        if(this.amount == 0){
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
        else
            addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
    }

}
