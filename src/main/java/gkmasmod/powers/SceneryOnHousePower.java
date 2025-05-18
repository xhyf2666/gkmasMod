package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.downfall.charbosses.actions.unique.EnemyChangeStanceAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.stances.AbstractEnemyStance;
import gkmasmod.downfall.charbosses.stances.ENPreservationStance;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.StanceHelper;

public class SceneryOnHousePower extends AbstractPower {
    private static final String CLASSNAME = SceneryOnHousePower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public SceneryOnHousePower(AbstractCreature owner) {
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

    @Override
    public void atStartOfTurnPostDraw() {
        if(this.owner.isPlayer){
            if(AbstractDungeon.player.stance.ID.equals(PreservationStance.STANCE_ID)){
                if(!StanceHelper.isInStance(AbstractDungeon.player.stance,PreservationStance.STANCE_ID,3))
                    addToBot(new ChangeStanceAction(PreservationStance.STANCE_ID3));
            }
        }
        else if(this.owner instanceof AbstractCharBoss){
            if(AbstractCharBoss.boss.stance.ID.equals(ENPreservationStance.STANCE_ID)){
                if(!StanceHelper.enemyIsInStance((AbstractEnemyStance) AbstractCharBoss.boss.stance,ENPreservationStance.STANCE_ID,3))
                    addToBot(new EnemyChangeStanceAction(ENPreservationStance.STANCE_ID3));
            }
        }
    }

}
