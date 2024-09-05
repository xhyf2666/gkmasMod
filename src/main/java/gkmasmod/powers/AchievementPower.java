package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.utils.NameHelper;

public class AchievementPower extends AbstractPower {
    private static final String CLASSNAME = AchievementPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static int AchievementIDOffset;

    private int damage = 0;

    String path128 = String.format("img/powers/%s_84.png",CLASSNAME);;
    String path48 = String.format("img/powers/%s_32.png",CLASSNAME);;

    public AchievementPower(AbstractCreature owner, int Damage) {
        this.name = NAME;
        this.ID = POWER_ID + AchievementIDOffset;
        AchievementIDOffset++;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = 1;
        this.damage = Damage;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.damage);
    }

    public void atStartOfTurn() {
        addToBot(new DamageRandomEnemyAction(new DamageInfo(null, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

}
