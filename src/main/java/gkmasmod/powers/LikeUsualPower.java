package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import gkmasmod.downfall.charbosses.actions.common.EnemyGainEnergyAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.stances.ENPreservationStance;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.NameHelper;

public class LikeUsualPower extends AbstractPower {
    private static final String CLASSNAME = LikeUsualPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public LikeUsualPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = Amount;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }


    @Override
    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance) {
        if(this.owner instanceof AbstractPlayer){
            if(oldStance.ID.equals(PreservationStance.STANCE_ID)){
                PreservationStance preservationStance = (PreservationStance) oldStance;
                if(preservationStance.stage>=1){
                    addToBot(new GainEnergyAction(this.amount));
                }
            }
        }
        else if(this.owner instanceof AbstractCharBoss){
            if(oldStance.ID.equals(ENPreservationStance.STANCE_ID)){
                ENPreservationStance preservationStance = (ENPreservationStance) oldStance;
                if(preservationStance.stage>=1){
                    addToBot(new EnemyGainEnergyAction(this.amount));
                }
            }
        }
    }

}
