package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import gkmasmod.monster.exordium.MonsterShion;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.SoundHelper;

public class CrisisTime extends AbstractPower {
    private static final String CLASSNAME = CrisisTime.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    private static int MAGIC = 60;
    private static int MAGIC2 = 20;
    private static int MAGIC3 = 2;
    private static int MAGIC4 = 1;

    public boolean flag1 = false;
    public boolean flag2 = false;

    public CrisisTime(AbstractCreature owner) {
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
        this.description = String.format(DESCRIPTIONS[0],MAGIC,MAGIC2,MAGIC3,MAGIC4);
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        float count = 1.0F*this.owner.currentHealth / this.owner.maxHealth;
        if(!flag1&&count<=0.6){
            flag1 = true;
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new NotGoodTune(AbstractDungeon.player, MAGIC3), MAGIC3));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new GreatNotGoodTune(AbstractDungeon.player, MAGIC4), MAGIC4));
            if(this.owner instanceof MonsterShion){
                ((MonsterShion) this.owner).attackTime+=1;
                ((MonsterShion) this.owner).updateMove();
                SoundHelper.playSound("gkmasModResource/audio/voice/monster/shion/adv_dear_amao_015_sson_010.ogg");
                AbstractDungeon.effectList.add(new SpeechBubble(this.owner.hb.cX + this.owner.dialogX - 50, this.owner.hb.cY + this.owner.dialogY + 50, 5.0F, CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:MonsterShion").DIALOG[4], false));
            }
        }
        if(!flag2&&count<=0.2){
            flag2 = true;
//            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new NotGoodTune(AbstractDungeon.player, MAGIC3), MAGIC3));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new GreatNotGoodTune(AbstractDungeon.player, MAGIC4), MAGIC4));
            if(this.owner instanceof MonsterShion){
                ((MonsterShion) this.owner).attackTime+=1;
                ((MonsterShion) this.owner).updateMove();
                SoundHelper.playSound("gkmasModResource/audio/voice/monster/shion/adv_dear_amao_015_sson_011.ogg");
//                AbstractDungeon.actionManager.addToBottom(new TalkAction(this.owner, CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:MonsterShion").DIALOG[5], 5.0F, 5.0F));
                AbstractDungeon.effectList.add(new SpeechBubble(this.owner.hb.cX + this.owner.dialogX - 50, this.owner.hb.cY + this.owner.dialogY + 50, 5.0F, CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:MonsterShion").DIALOG[5], false));
            }

        }
    }

}
