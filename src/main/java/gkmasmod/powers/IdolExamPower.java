package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.FullPowerValueAction;
import gkmasmod.relics.PlasticUmbrellaThatDay;
import gkmasmod.utils.NameHelper;

public class IdolExamPower extends AbstractPower {
    private static final String CLASSNAME = IdolExamPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    private int maxAmt;

    private int threshold1 = 0;
    private int threshold2 = 0;

    public IdolExamPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.maxAmt = amount;
        this.threshold1 = (int) (0.5 * maxAmt);
        this.threshold2 = (int) (0.8 * maxAmt);
        this.priority = 99;

        // 添加一大一小两张能力图
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount > this.amount)
            damageAmount = this.amount;
        this.amount -= damageAmount;
        if (this.amount < 0)
            this.amount = 0;
        updateDescription();
        return damageAmount;
    }

    public float getRate(){
        if(this.maxAmt-this.amount > this.threshold2)
            return 0.2F;
        if(this.maxAmt-this.amount > this.threshold1)
            return 0.5F;
        return 1.0F;
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        if(this.maxAmt-this.amount > this.threshold2)
            return 0.2F*damage;
        if(this.maxAmt-this.amount > this.threshold1)
            return 0.5F*damage;
        return damage;
    }

    public void atStartOfTurn() {
        this.amount = this.maxAmt;
        updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.maxAmt,this.maxAmt-this.amount,this.threshold1,this.threshold2);
    }


}
