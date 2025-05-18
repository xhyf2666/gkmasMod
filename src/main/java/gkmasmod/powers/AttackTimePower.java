package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.FullPowerValueAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.monster.beyond.MonsterMao1;
import gkmasmod.monster.beyond.MonsterMisuzu1;
import gkmasmod.monster.beyond.MonsterSena1;
import gkmasmod.monster.beyond.MonsterUme1;
import gkmasmod.relics.PlasticUmbrellaThatDay;
import gkmasmod.utils.NameHelper;

public class AttackTimePower extends AbstractPower {
    private static final String CLASSNAME = AttackTimePower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public AttackTimePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        setAttackTime();
        this.updateDescription();
    }

    private void setAttackTime(){
        if(this.owner instanceof MonsterSena1){
            MonsterSena1 m = (MonsterSena1)this.owner;
            m.attackTime = this.amount;
            m.updateMove();
        }
        else if(this.owner instanceof MonsterUme1){
            MonsterUme1 m = (MonsterUme1)this.owner;
            m.attackTime = this.amount;
            m.updateMove();
        }
        else if(this.owner instanceof MonsterMisuzu1){
            MonsterMisuzu1 m = (MonsterMisuzu1)this.owner;
            m.attackTime = this.amount;
            m.updateMove();
        }
        else if(this.owner instanceof MonsterMao1){
            MonsterMao1 m = (MonsterMao1)this.owner;
            m.attackTime = this.amount;
            m.updateMove();
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        setAttackTime();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount);
    }

}
