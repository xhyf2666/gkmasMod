package gkmasmod.powers;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import gkmasmod.characters.IdolCharacter;
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
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

public class CallMeAnyTimePower extends AbstractPower {
    private static final String CLASSNAME = CallMeAnyTimePower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public CallMeAnyTimePower(AbstractCreature owner,int amount) {
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

    @Override
    public void atStartOfTurnPostDraw() {
        if(this.owner.isPlayer){
            for(AbstractMonster mo:AbstractDungeon.getCurrRoom().monsters.monsters){
                if(!mo.isDeadOrEscaped()&&mo.hasPower(NoPhoneInClassPower.POWER_ID)){
                    if(AbstractDungeon.player instanceof IdolCharacter){
                        if(SkinSelectScreen.Inst.idolName.equals(IdolData.ttmr)){
                            AbstractDungeon.effectList.add(new SpeechBubble(this.owner.hb.cX + this.owner.dialogX - 50, this.owner.hb.cY + this.owner.dialogY + 50, 3.0F, String.format(CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:MonsterAsari").DIALOG[3],CardCrawlGame.playerName), false));
                        }
                    }
                    return;
                }
            }
            float amount = 1.0F * this.owner.currentHealth /  this.owner.maxHealth;
            if(amount >=0.5f){
                addToBot(new GainEnergyAction(this.amount));
            }
            else{
                addToBot(new ApplyPowerAction(this.owner,this.owner,new HalfDamageReceive(this.owner,this.amount),this.amount));
            }
            if(AbstractDungeon.player.hasRelic(ProducerPhone.ID)){
                AbstractDungeon.player.getRelic(ProducerPhone.ID).flash();
                AbstractDungeon.player.getRelic(ProducerPhone.ID).counter+=this.amount;
            }
        }
        else if(this.owner instanceof AbstractCharBoss){
            float amount = 1.0F * this.owner.currentHealth /  this.owner.maxHealth;
            if(amount >=0.5f){
                addToBot(new EnemyGainEnergyAction(this.amount));
            }
            else{
                addToBot(new ApplyPowerAction(this.owner,this.owner,new HalfDamageReceive(this.owner,this.amount),this.amount));
            }
            if(AbstractCharBoss.boss.hasRelic(CBR_ProducerPhone.ID2)){
                AbstractCharBoss.boss.getRelic(CBR_ProducerPhone.ID2).flash();
                AbstractCharBoss.boss.getRelic(CBR_ProducerPhone.ID2).counter+=this.amount;
            }
        }
    }
}
