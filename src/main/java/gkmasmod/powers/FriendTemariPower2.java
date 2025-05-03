package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import gkmasmod.cards.free.EatEmptyYourRefrigerator;
import gkmasmod.utils.NameHelper;

public class FriendTemariPower2 extends AbstractPower {
    private static final String CLASSNAME = FriendTemariPower2.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png","SayGoodbyeToDislikeMyselfPower");
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png","SayGoodbyeToDislikeMyselfPower");


    private static final int MAGIC = 1;
    private static final int MAGIC3 = 2;

    public FriendTemariPower2(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],MAGIC,MAGIC3);
    }


    public void eatSomething(int cardNum){
        int count1 = EnergyPanel.totalCount;
        int count2 = cardNum;
        if(count1>0){
            this.owner.maxHealth += MAGIC*count1;
//            AbstractDungeon.player.loseEnergy(count1);
        }
        if(count2>0){
            this.owner.currentHealth += MAGIC3*count2;
        }
        AbstractDungeon.effectList.add(new SpeechBubble(this.owner.hb.cX + this.owner.dialogX - 50, this.owner.hb.cY + this.owner.dialogY + 50, 2.0F,"美铃美铃…这些东西…我帮你吃掉吧", true));
        if(this.owner.currentHealth>this.owner.maxHealth){
            this.owner.currentHealth = this.owner.maxHealth;
            addToBot(new MakeTempCardInHandAction(new EatEmptyYourRefrigerator()));
        }
    }

}
