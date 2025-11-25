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
import gkmasmod.actions.common.ModifyDamageAction;
import gkmasmod.actions.common.ModifyDamageRandomEnemyAction;
import gkmasmod.cards.sense.CharmPerformance;
import gkmasmod.utils.NameHelper;

public class CharmPerformanceSPPower extends AbstractPower {
    private static final String CLASSNAME = CharmPerformanceSPPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static int CharmPerformanceIDOffset;

    AbstractCreature target;

    private int damage = 0;

    int turn = 2;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public CharmPerformanceSPPower(AbstractCreature owner, int Damage, AbstractCreature target) {
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
        this.description = String.format(DESCRIPTIONS[0],turn,this.damage);
    }

    public void atStartOfTurn() {
        this.turn--;
        updateDescription();
        if(turn==0){
            if(!target.halfDead && !target.isDying && !target.isEscaping){
                addToBot(new ModifyDamageAction(target, new DamageInfo(this.owner, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL,new CharmPerformance(),false,1,2.0F));
            }
            else{
                addToBot(new ModifyDamageRandomEnemyAction(new DamageInfo(this.owner, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL,new CharmPerformance(),false,1,2.0F));
            }
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }

}
