package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.utils.NameHelper;

public class NextXTurnAnxietyPower extends AbstractPower {
    private static final String CLASSNAME = NextXTurnAnxietyPower.class.getSimpleName();
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int magic =3;
    private static int AchievementIDOffset;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png","AnxietyPower");
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png","AnxietyPower");

    public NextXTurnAnxietyPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID + AchievementIDOffset;
        AchievementIDOffset++;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = Amount;
        this.magic = 3;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }

    public NextXTurnAnxietyPower(AbstractCreature owner, int Amount, int magic) {
        this.name = NAME;
        this.ID = POWER_ID + AchievementIDOffset;
        AchievementIDOffset++;
        this.owner = owner;
        this.type = PowerType.DEBUFF;
        this.amount = Amount;
        this.magic = magic;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        if(this.owner.isPlayer){
            if(this.amount > 1){
                addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
            }else{
                addToBot(new ApplyPowerAction(this.owner, this.owner,new AnxietyPower(this.owner, this.magic), this.magic));
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            }
        }
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount,magic);
    }

}
