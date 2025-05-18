package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.actions.ProducerPlayCardAction;
import gkmasmod.downfall.bosses.ProducerBoss;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.utils.NameHelper;

public class ProducerIdolPower extends AbstractPower {
    private static final String CLASSNAME = ProducerIdolPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int limit = 3;

    private static final int MAGIC = 1;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png","WorkHardSPPower");
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png","WorkHardSPPower");

    public ProducerIdolPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount=0;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public ProducerIdolPower setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.limit,MAGIC);
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(card instanceof AbstractBossCard)
            return;
        flashWithoutSound();
        this.amount++;
        if (this.amount == this.limit) {
            this.amount = 0;
            playApplyPowerSfx();
            this.flash();
            if(this.owner instanceof ProducerBoss){
                addToBot(new ProducerPlayCardAction());
            }
        }
    }
}
