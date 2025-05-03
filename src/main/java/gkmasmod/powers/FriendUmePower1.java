package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BlurPower;
import gkmasmod.utils.NameHelper;

public class FriendUmePower1 extends AbstractPower {
    private static final String CLASSNAME = FriendUmePower1.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png","ShareSomethingWithYouPower");
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png","ShareSomethingWithYouPower");


    private static final int MAGIC = 1;
    private static final int TIME = 2;

    private int current = 0;

    private boolean isTrigger = false;

    public FriendUmePower1(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],TIME,MAGIC,MAGIC);
    }

    @Override
    public void atStartOfTurn() {
        if(current < TIME)
            current = (current + 1) % TIME;
        if(current == 0) {
            this.flash();
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new BlurPower(AbstractDungeon.player,MAGIC),MAGIC));
        }
    }

    @Override
    public void onSpecificTrigger() {
        if(!isTrigger) {
            isTrigger = true;
            this.flash();
            AbstractDungeon.player.maxHealth+=1;
            AbstractDungeon.player.currentHealth+=1;
        }
    }
}
