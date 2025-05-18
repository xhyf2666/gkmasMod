package gkmasmod.powers;

import gkmasmod.downfall.charbosses.actions.unique.EnemyChangeStanceAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.downfall.charbosses.stances.ENPreservationStance;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.NameHelper;

public class TrainingResultPower extends AbstractPower {
    private static final String CLASSNAME = TrainingResultPower.class.getSimpleName();
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public TrainingResultPower(AbstractCreature owner,int amount) {
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
        this.description = String.format(DESCRIPTIONS[0],this.amount);

    }

    public void atStartOfTurnPostDraw() {
        if(this.owner.isPlayer){
            for (int i = 0; i < this.amount; i++) {
                addToBot(new ChangeStanceAction(PreservationStance.STANCE_ID));
            }
        }
        else if(this.owner instanceof AbstractCharBoss){
            for (int i = 0; i < this.amount; i++) {
                addToBot(new EnemyChangeStanceAction(ENPreservationStance.STANCE_ID));
            }
        }
        addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this.ID));
    }
}
