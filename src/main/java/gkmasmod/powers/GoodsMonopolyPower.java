package gkmasmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import gkmasmod.actions.PinkGirlAction;
import gkmasmod.monster.friend.FriendNunu;
import gkmasmod.monster.friend.FriendOnigiri;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;
import gkmasmod.utils.SoundHelper;

public class GoodsMonopolyPower extends AbstractPower {
    private static final String CLASSNAME = GoodsMonopolyPower.class.getSimpleName();

    public static final String POWER_ID = NameHelper.makePath(CLASSNAME);

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CLASSNAME);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final int MAGIC = 50;
    private static final int MAGIC2 = 1;
    private static final int MAGIC3 = 2;

    public int wordCount = 0;

    String path128 = String.format("gkmasModResource/img/powers/%s_84.png",CLASSNAME);
    String path48 = String.format("gkmasModResource/img/powers/%s_32.png",CLASSNAME);

    public GoodsMonopolyPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],MAGIC,MAGIC2,MAGIC3);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.onSpecificTrigger();
    }

    @Override
    public void onSpecificTrigger() {
        boolean left = false;
        int count = PlayerHelper.getPowerAmount(this.owner,MoneyPower.POWER_ID);
        int nunuCount=0;
        for (AbstractMonster monster: AbstractDungeon.getMonsters().monsters){
            if(monster instanceof FriendNunu && !monster.isDeadOrEscaped() && AbstractMonsterPatch.friendlyField.friendly.get(monster)==false){
                nunuCount++;
                FriendNunu nunu = (FriendNunu)monster;
                if(nunu.left)
                    left = true;
            }
        }
        if(this.owner.hasPower(FearPinkGirlPower.POWER_ID)){
            if(count==0){
                if(this.owner.hasPower(PinkGirlPower.POWER_ID)){
                    this.owner.getPower(PinkGirlPower.POWER_ID).flash();
                    addToBot(new PinkGirlAction(this.owner));
                }
            }
            return;
        }
        while(nunuCount<MAGIC3){
            if(count>=MAGIC){
                this.flash();
                if(wordCount==0){
                    SoundHelper.playSound("gkmasModResource/audio/voice/monster/nadeshiko/adv_dear_kcna_014_andk_017.ogg");
                    AbstractDungeon.effectList.add(new SpeechBubble(this.owner.hb.cX + this.owner.dialogX - 50, this.owner.hb.cY + this.owner.dialogY + 50, 5.0F, CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:MonsterNadeshiko").DIALOG[5], false));
                    wordCount=(wordCount+1)%3;
                }
                this.owner.getPower(MoneyPower.POWER_ID).onSpecificTrigger();
                this.owner.getPower(PinkGirlPower.POWER_ID).onSpecificTrigger();
                count -= MAGIC;
                if(!left){
                    addToBot(new SpawnMonsterAction(new FriendNunu(this.owner.hb_x-300, this.owner.hb_y,this.owner,true),true));
                    left = true;
                }
                else{
                    addToBot(new SpawnMonsterAction(new FriendNunu(this.owner.hb_x+300, this.owner.hb_y,this.owner),true));
                    left = false;
                }
                nunuCount++;
            }
            else{
                addToBot(new PinkGirlAction(this.owner));
                break;
            }
        }
    }
}
