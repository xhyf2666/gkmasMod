package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.utils.NameHelper;

public class TauntPower extends AbstractPower {
    private static final String CLASSNAME = TauntPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final int MAGIC = 50;
    private static final int MAGIC2 = 1;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    private static final int LIMIT = 1;

    private int count = 0;

    public TauntPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        // 添加一大一小两张能力图
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],MAGIC,MAGIC2,MAGIC2);
    }

    @Override
    public void atEndOfRound() {
        this.count = 0;
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (info.output > 0) {
            if(count < LIMIT){
                count++;
                addToBot(new ApplyPowerAction(this.owner,this.owner,new StrengthPower(this.owner,MAGIC2),MAGIC2));
            }
            else{
                addToBot(new ApplyPowerAction(this.owner,this.owner,new StrengthPower(this.owner,-MAGIC2),-MAGIC2));
            }
        }
        return damageAmount;
    }

    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        return damage * 1.5F;
    }

}
