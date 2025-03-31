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
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.ModifyDamageAction;
import gkmasmod.actions.ModifyDamageRandomEnemyAction;
import gkmasmod.actions.SayGoodbyeToDislikeMyselfAction;
import gkmasmod.cards.sense.Achievement;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.utils.NameHelper;

public class SayGoodbyeToDislikeMyselfPower extends AbstractPower {
    private static final String CLASSNAME = SayGoodbyeToDislikeMyselfPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static int AchievementIDOffset;

    private int damage = 0;
    private int magicNumber = 250;

    AbstractCreature target;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);;
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);;

    public SayGoodbyeToDislikeMyselfPower(AbstractCreature owner, int Damage) {
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
        this.description = String.format(DESCRIPTIONS[0], this.damage,this.magicNumber);
    }

    @Override
    public void onSpecificTrigger() {
        if(this.owner.isPlayer){
            AbstractCreature target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            if(target != null){
                addToBot(new SayGoodbyeToDislikeMyselfAction(target, new DamageInfo(this.owner, this.damage, DamageInfo.DamageType.NORMAL), this.magicNumber,null,true));
            }
        }
        else if(this.owner instanceof AbstractCharBoss){
            addToBot(new SayGoodbyeToDislikeMyselfAction(AbstractDungeon.player, new DamageInfo(this.owner, this.damage, DamageInfo.DamageType.NORMAL), this.magicNumber,null,true));
        }
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }
}
