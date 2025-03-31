package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import gkmasmod.actions.ModifyDamageAction;
import gkmasmod.actions.ModifyDamageRandomEnemyAction;
import gkmasmod.cards.sense.Achievement;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.ThreeSizeHelper;

public class RebirthPower extends AbstractPower {
    private static final String CLASSNAME = RebirthPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static int RebirthPowerIDOffset;

    AbstractCreature target;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public RebirthPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID + RebirthPowerIDOffset;
        RebirthPowerIDOffset++;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    @Override
    public void onSpecificTrigger() {
        this.flash();
        this.owner.heal(this.amount);
        AbstractDungeon.effectsQueue.add(new HealEffect(this.owner.hb.cX, this.owner.hb.cY, this.amount));
        addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    public void onVictory() {
        if(this.owner.isPlayer)
            AbstractDungeon.player.heal((int) (1.0f*this.amount*0.8F));
    }
}
