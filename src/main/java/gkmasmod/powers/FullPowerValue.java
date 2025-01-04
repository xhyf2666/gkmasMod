package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import gkmasmod.actions.FullPowerValueAction;
import gkmasmod.relics.PlasticUmbrellaThatDay;
import gkmasmod.stances.FullPowerStance;
import gkmasmod.utils.NameHelper;

public class FullPowerValue extends AbstractPower {
    private static final String CLASSNAME = FullPowerValue.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);;
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);;

    public FullPowerValue(AbstractCreature owner,int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.priority = 90;

        // 添加一大一小两张能力图
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
//        if(this.amount<=0){
//            addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
//        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new FullPowerValueAction());
        if(AbstractDungeon.player.hasRelic(PlasticUmbrellaThatDay.ID)){
            AbstractDungeon.player.getRelic(PlasticUmbrellaThatDay.ID).onTrigger();
        }
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0]);
    }


}
