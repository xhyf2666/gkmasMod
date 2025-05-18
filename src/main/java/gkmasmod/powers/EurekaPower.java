package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.BlockDamageAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.utils.NameHelper;

public class EurekaPower extends AbstractPower {
    private static final String CLASSNAME = EurekaPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static int AchievementIDOffset;

    private int magicNumber = 30;
    private int HP = 5;


    AbstractCreature target;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public EurekaPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID + AchievementIDOffset;
        AchievementIDOffset++;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.magicNumber,this.HP);
    }


    @Override
    public void atStartOfTurn() {
        float amount = 1.0F*this.owner.currentHealth / this.owner.maxHealth;
        if(amount<=0.5F){
            this.flash();
            if(this.owner.isPlayer){
                AbstractCreature target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                if(target != null){
                    addToBot(new BlockDamageAction(1.0F*this.magicNumber/100,0,this.owner,target,null));
                }
            }
            else if(this.owner instanceof AbstractCharBoss){
                addToBot(new BlockDamageAction(1.0F*this.magicNumber/100,0,this.owner,AbstractDungeon.player,null));
            }
            addToBot(new HealAction(this.owner,this.owner, (int) (this.owner.maxHealth*1.0F*this.HP/100)));
        }
    }
}
