package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.ModifyDamageAction;
import gkmasmod.actions.ModifyDamageRandomEnemyAction;
import gkmasmod.cards.sense.Achievement;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.utils.NameHelper;

public class DayDreamPower extends AbstractPower {
    private static final String CLASSNAME = DayDreamPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static int AchievementIDOffset;

    private int rate = 60;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public DayDreamPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID + AchievementIDOffset;
        AchievementIDOffset++;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public DayDreamPower(AbstractCreature owner, int amount, int rate) {
        this(owner, amount);
        this.rate = rate;
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount,this.rate);
    }

    public void atEndOfRound() {
        int count = (int) (this.owner.currentBlock * this.rate * 1.0F/100);
        if(this.owner instanceof AbstractPlayer){
            if(count>0)
                addToBot(new DamageAllEnemiesAction(AbstractDungeon.player,count, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
        else if(this.owner instanceof AbstractCharBoss){
            addToBot(new ModifyDamageAction(AbstractDungeon.player,new DamageInfo(this.owner, count, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL, null));
        }
        addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
    }

    public void atStartOfTurn() {

    }

}
