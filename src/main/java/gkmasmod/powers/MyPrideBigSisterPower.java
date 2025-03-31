package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.GoodImpressionDamageAction;
import gkmasmod.actions.SayGoodbyeToDislikeMyselfAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.utils.NameHelper;

public class MyPrideBigSisterPower extends AbstractPower {
    private static final String CLASSNAME = MyPrideBigSisterPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static int AchievementIDOffset;

    private int magicNumber = 180;

    AbstractCreature target;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);;
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);;

    public MyPrideBigSisterPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID + AchievementIDOffset;
        AchievementIDOffset++;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }

    public MyPrideBigSisterPower(AbstractCreature owner,int magicNumber) {
        this.name = NAME;
        this.ID = POWER_ID + AchievementIDOffset;
        AchievementIDOffset++;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.magicNumber = magicNumber;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],4, this.magicNumber);
    }

    @Override
    public void onSpecificTrigger() {
        if(this.owner.isPlayer){
            AbstractCreature target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            if(target != null){
                addToBot(new GoodImpressionDamageAction(1.0F*this.magicNumber/100,0,this.owner,target));
            }
        }
        else if(this.owner instanceof AbstractCharBoss){
            addToBot(new GoodImpressionDamageAction(1.0F*this.magicNumber/100,0,this.owner,AbstractDungeon.player));
        }
    }
}
