package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.ModifyDamageAction;
import gkmasmod.actions.ModifyDamageRandomEnemyAction;
import gkmasmod.cards.sense.CharmPerformance;
import gkmasmod.utils.NameHelper;

public class CharmPerformancePower extends AbstractPower {
    private static final String CLASSNAME = CharmPerformancePower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static int CharmPerformanceIDOffset;

    AbstractCreature target;

    private int damage = 0;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);;
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);;

    public CharmPerformancePower(AbstractCreature owner, int Damage,AbstractCreature target) {
        this.name = NAME;
        this.ID = POWER_ID + CharmPerformanceIDOffset;
        CharmPerformanceIDOffset++;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = 1;
        this.damage = Damage;
        this.target = target;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.damage);
    }

    public void atStartOfTurn() {
        if(!target.halfDead && !target.isDying && !target.isEscaping){
            addToBot(new ModifyDamageAction(target, new DamageInfo(this.owner, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL,new CharmPerformance()));
        }
        else{
            addToBot(new ModifyDamageRandomEnemyAction(new DamageInfo(this.owner, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL,new CharmPerformance()));
        }
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

}
