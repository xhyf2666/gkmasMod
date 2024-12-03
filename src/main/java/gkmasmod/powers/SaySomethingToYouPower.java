package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import gkmasmod.utils.NameHelper;

public class SaySomethingToYouPower extends AbstractPower {
    private static final String CLASSNAME = SaySomethingToYouPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    private int magic= 1;
    public boolean breakBlock = false;

    public SaySomethingToYouPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.priority = 110;

        // 添加一大一小两张能力图
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    public SaySomethingToYouPower setMagic(int magic){
        if(magic>this.magic)
            this.magic = magic;
        return this;
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],2,magic);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        if(this.owner.isPlayer){
            for(AbstractMonster monster : AbstractDungeon.getMonsters().monsters){
                if(!monster.isDeadOrEscaped()){
                    addToBot(new GainBlockAction(monster,2));
                }
            }
        }
        else{
            addToBot(new GainBlockAction(AbstractDungeon.player,2));
        }
        breakBlock = false;
    }

    public void onBreakBlock(AbstractCreature target) {
        if(!breakBlock){
            flash();
            breakBlock = true;
            addToBot(new ApplyPowerAction(target,this.owner,new VulnerablePower(target,magic,false)));
        }
    }
}
