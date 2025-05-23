package gkmasmod.powers;

import gkmasmod.downfall.charbosses.actions.common.EnemyMakeTempCardInHandAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.cards.free.JustAngel;
import gkmasmod.cards.free.JustDemon;
import gkmasmod.downfall.cards.free.ENJustAngel;
import gkmasmod.downfall.cards.free.ENJustDemon;
import gkmasmod.utils.NameHelper;

public class AngelAndDemonPower extends AbstractPower {
    private static final String CLASSNAME = AngelAndDemonPower.class.getSimpleName();
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public AngelAndDemonPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0]);

    }

    public void atStartOfTurn() {
        if(this.owner.isPlayer){
            addToBot(new MakeTempCardInHandAction(new JustAngel()));
            addToBot(new MakeTempCardInHandAction(new JustDemon()));
        }
        else if(this.owner instanceof AbstractCharBoss){
            addToBot(new EnemyMakeTempCardInHandAction(new ENJustAngel()));
            addToBot(new EnemyMakeTempCardInHandAction(new ENJustDemon()));
        }

    }
}
