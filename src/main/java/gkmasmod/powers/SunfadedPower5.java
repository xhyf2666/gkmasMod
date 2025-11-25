package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.common.GainTrainRoundPowerAction;
import gkmasmod.utils.NameHelper;

public class SunfadedPower5 extends AbstractPower {
    private static final String CLASSNAME = SunfadedPower5.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    private static final int magic = 5;
    private static final int magic2 = 2;

    public SunfadedPower5(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],magic,magic2,this.amount);
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        boolean flag = 1.0F*this.owner.currentHealth/this.owner.maxHealth <= 0.5F;
        int healNum = Math.max(1,(int) (1.0F*magic*this.owner.maxHealth)/100);
        if(flag&&this.amount>0){
            addToBot(new HealAction(this.owner, this.owner, healNum));
            addToBot(new GainTrainRoundPowerAction(this.owner,1));
            this.amount--;
        }
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        this.amount +=magic2;
    }
}
