package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.utils.NameHelper;

public class CatchTheLightPower extends AbstractPower {
    private static final String CLASSNAME = CatchTheLightPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public CatchTheLightPower(AbstractCreature owner,int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(!(this.owner instanceof AbstractCharBoss)&&card instanceof AbstractBossCard)
            return;
        if(this.owner instanceof AbstractCharBoss&&(!(card instanceof AbstractBossCard)))
            return;
        if(card instanceof GkmasCard && card.hasTag(GkmasCardTag.IDOL_CARD_TAG)){
            addToBot(new GainBlockWithPowerAction(this.owner,this.amount));
        }
    }
}
