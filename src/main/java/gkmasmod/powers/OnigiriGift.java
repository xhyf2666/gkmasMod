package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.utils.NameHelper;

public class OnigiriGift extends AbstractPower {
    private static final String CLASSNAME = OnigiriGift.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final int RATE = 20;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png","ShareSomethingWithYouPower");
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png","ShareSomethingWithYouPower");

    public OnigiriGift(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.priority = 100;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],RATE);
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (info.output > 0) {
            int count = (int) (info.output * RATE *1.0F / 100);
            if(count == 0)
                count = 1;
            if(info.owner!=null)
                addToBot(new GainBlockAction(info.owner,info.owner,count));
        }

        return damageAmount;
    }

//    public int onAttacked(DamageInfo info, int damageAmount) {
//        if (info.output > 0) {
//            int count = (int) (info.output * RATE *1.0F / 100);
//            if(count == 0)
//                count = 1;
//            addToBot(new ApplyPowerAction(this.owner,this.owner,new GoodImpression(this.owner,count),count));
//            addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
//        }
//
//        return damageAmount;
//    }
}
