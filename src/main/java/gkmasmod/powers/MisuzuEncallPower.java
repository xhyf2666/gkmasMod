package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.stances.SleepStance;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class MisuzuEncallPower extends AbstractPower {
    private static final String CLASSNAME = MisuzuEncallPower.class.getSimpleName();
    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png","EncallPower");
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png","EncallPower");

    public MisuzuEncallPower(AbstractCreature owner) {
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
    public void onDeath() {
        this.flash();
        addToBot(new ChangeStanceAction(SleepStance.STANCE_ID));
        for(AbstractMonster mo: AbstractDungeon.getMonsters().monsters){
            if(mo!=this.owner&&!mo.isDeadOrEscaped()&& !AbstractMonsterPatch.friendlyField.friendly.get(mo)){
                int count = (int) (PlayerHelper.getPowerAmount(mo,StrengthPower.POWER_ID) *0.5F);
                if(count>0){
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo,this.owner,new StrengthPower(mo,count),count));
                }
            }
        }
    }
}
