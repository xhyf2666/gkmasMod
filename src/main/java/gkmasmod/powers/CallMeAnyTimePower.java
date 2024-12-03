package gkmasmod.powers;

import gkmasmod.downfall.charbosses.actions.common.EnemyGainEnergyAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.downfall.relics.CBR_ProducerPhone;
import gkmasmod.relics.ProducerPhone;
import gkmasmod.utils.NameHelper;

public class CallMeAnyTimePower extends AbstractPower {
    private static final String CLASSNAME = CallMeAnyTimePower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);;
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);;

    public CallMeAnyTimePower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        // 添加一大一小两张能力图
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],1);
    }


    @Override
    public void atStartOfTurnPostDraw() {
        if(this.owner.isPlayer){
            float amount = 1.0F * this.owner.currentHealth /  this.owner.maxHealth;
            if(amount >=0.5f){
                addToBot(new GainEnergyAction(1));
            }
            else{
                addToBot(new ApplyPowerAction(this.owner,this.owner,new HalfDamageReceive(this.owner,1),1));
            }
            if(AbstractDungeon.player.hasRelic(ProducerPhone.ID)){
                AbstractDungeon.player.getRelic(ProducerPhone.ID).flash();
                AbstractDungeon.player.getRelic(ProducerPhone.ID).counter++;
            }
        }
        else if(this.owner instanceof AbstractCharBoss){
            float amount = 1.0F * this.owner.currentHealth /  this.owner.maxHealth;
            if(amount >=0.5f){
                addToBot(new EnemyGainEnergyAction(1));
            }
            else{
                addToBot(new ApplyPowerAction(this.owner,this.owner,new HalfDamageReceive(this.owner,1),1));
            }
            if(AbstractCharBoss.boss.hasRelic(CBR_ProducerPhone.ID2)){
                AbstractCharBoss.boss.getRelic(CBR_ProducerPhone.ID2).flash();
                AbstractCharBoss.boss.getRelic(CBR_ProducerPhone.ID2).counter++;
            }
        }
    }
}
