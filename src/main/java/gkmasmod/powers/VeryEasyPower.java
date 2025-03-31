package gkmasmod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.ModifyDamageAction;
import gkmasmod.actions.ModifyDamageRandomEnemyAction;
import gkmasmod.utils.NameHelper;

public class VeryEasyPower extends AbstractPower {
    private static final String CLASSNAME = VeryEasyPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static int VeryEasyIDOffset;

    private static int DAMAGE = 4;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);;
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);;

    public VeryEasyPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID + VeryEasyIDOffset;
        VeryEasyIDOffset++;
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
        this.description = String.format(DESCRIPTIONS[0], this.amount, DAMAGE);
    }

    public void atEndOfTurn(boolean isPlayer) {
        if(this.amount > 0){
            addToBot(new ReducePowerAction(this.owner, this.owner, ID, 1));
        }
        else
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
        if(this.owner.isPlayer)
            addToBot(new ModifyDamageRandomEnemyAction(new DamageInfo(this.owner, DAMAGE, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        else if(this.owner instanceof AbstractCharBoss){
            addToBot(new ModifyDamageAction(AbstractDungeon.player, new DamageInfo(this.owner, 4, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }
}
