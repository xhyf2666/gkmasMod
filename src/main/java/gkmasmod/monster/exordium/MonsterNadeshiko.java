package gkmasmod.monster.exordium;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.combat.BloodShotEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import com.megacrit.cardcrawl.vfx.combat.ViceCrushEffect;
import gkmasmod.cards.anomaly.BecomeIdol;
import gkmasmod.cards.free.Sleepy;
import gkmasmod.cards.logic.SparklingConfetti;
import gkmasmod.cards.logic.WakeUp;
import gkmasmod.cards.sense.WantToGoThere;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.ChangeScene;
import gkmasmod.monster.LatterEffect;
import gkmasmod.monster.friend.FriendNunu;
import gkmasmod.potion.OolongTea;
import gkmasmod.powers.*;
import gkmasmod.relics.PocketBook;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.*;

import java.io.IOException;
import java.util.Random;

public class MonsterNadeshiko extends CustomMonster {
    public static final String ID = "MonsterNadeshiko";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:MonsterNadeshiko");

    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public static final String[] DIALOG = monsterStrings.DIALOG;


    private static int MAX_HEALTH = 350000;

    private static final byte BLOOD_SHOTS = 1;

    private static final byte ECHO_ATTACK = 2;

    private static final byte DEBILITATE = 3;

    private static final byte GAIN_ONE_STRENGTH = 4;

    public static final int DEBUFF_AMT = -1;

    private int bloodHitCount;

    private boolean isFirstMove = true;

    private int moveCount = 0, buffCount = 0;

    private int moneyAmt;


    private boolean isOutTriggered = false;

    private int stage = 0;

    private int secondMove = 0;

    private boolean multiDamage = false;
    private int last_buff = 5;

    public MonsterNadeshiko() {
        this(000.0F, 0.0F);
    }

    public MonsterNadeshiko(float x, float y) {
        super(NAME, ID, MAX_HEALTH, -8.0F, 0.0F, 360.0F, 480.0F, null, x, y);
        this.img = new Texture("gkmasModResource/img/monsters/Nadeshiko/MonsterNadeshiko.png");
        this.type = EnemyType.BOSS;
        if (AbstractDungeon.ascensionLevel >= 10) {
            setHp(300);
            this.moneyAmt=300;
        } else {
            setHp(220);
            this.moneyAmt=200;
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 6));
            this.damage.add(new DamageInfo(this, 20));
            this.bloodHitCount = 2;
        } else {
            this.damage.add(new DamageInfo(this, 4));
            this.damage.add(new DamageInfo(this, 15));
            this.bloodHitCount = 2;
        }
    }

    public void usePreBattleAction() {
        GkmasMod.renderScene = false;
        AbstractGameEffect effect =  new ChangeScene(ImageMaster.loadImage("gkmasModResource/img/bg/bg3.png"));
        AbstractDungeon.effectList.add(new LatterEffect(() -> {
            AbstractDungeon.effectsQueue.add(effect);
        }));
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        CardCrawlGame.music.playTempBgmInstantly("gkmasModResource/audio/bgm/bgm_adv_gokugetsu_001.ogg",true);

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MoneyPower(this, this.moneyAmt), this.moneyAmt));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new TauntPower(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new GoodsMonopolyPower(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PinkGirlPower(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new IntangibleSpecialPower(this,1),1));
        addToBot(new SpawnMonsterAction(new FriendNunu(this.hb_x-300, this.hb_y,this,true),true));
        addToBot(new SpawnMonsterAction(new FriendNunu(this.hb_x+300, this.hb_y,this),true));
        helloVoice();
    }

    public void takeTurn() {
        int i;
        switch (this.nextMove) {
            case 0:
                attackVoice();
                for (i = 0; i < this.bloodHitCount; i++)
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                            .get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
                break;
            case 1:
                attackVoice();
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ViceCrushEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.4F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                        .get(1), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break;
            case 2:
                attackVoice();
                addToBot(new GainBlockAction(this, this, 30* ThreeSizeHelper.getHealthRate(1)));
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        switch (moveCount){
            case 0:
                setMove((byte)0, Intent.ATTACK, (this.damage.get(0)).base, this.bloodHitCount, true);
                break;
            case 1:
                setMove((byte)1, Intent.ATTACK, (this.damage.get(1)).base);
                break;
            case 2:
                setMove((byte)2, Intent.DEFEND);
                break;
        }
        moveCount=(moveCount+1)%3;
    }

    public void die() {
        if (!(AbstractDungeon.getCurrRoom()).cannotLose) {
            super.die();
            deathVoice();
            onBossVictoryLogic();
            AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 5.0F, DIALOG[1], false));
        }
    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if(!this.isOutTriggered && this.currentHealth<=this.maxHealth/2) {
            CardCrawlGame.music.dispose();
            if(AbstractDungeon.player instanceof MisuzuCharacter){
                String song = String.format("gkmasModResource/audio/song/%s_00%s.ogg", IdolData.hmsz,2);
                if (Gdx.files.internal(song).exists())
                    CardCrawlGame.music.playTempBgmInstantly(song, true);
            }
            else{
                String song = String.format("gkmasModResource/audio/song/%s_00%s.ogg", SkinSelectScreen.Inst.idolName,
                        IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getSong(SkinSelectScreen.Inst.skinIndex));
                if (Gdx.files.internal(song).exists())
                    CardCrawlGame.music.playTempBgmInstantly(song, true);
                else {
                    song = String.format("gkmasModResource/audio/song/%s_00%s.ogg", SkinSelectScreen.Inst.idolName, 2);
                    CardCrawlGame.music.playTempBgmInstantly(song, true);
                }
            }
            this.isOutTriggered = true;
        }
    }

    public void helloVoice(){
        String[] voices = new String[]{
                "adv_dear_ssmk_015_andk_001.ogg",
                "adv_dear_kcna_015_andk_001.ogg"
        };
        if(AbstractDungeon.player instanceof IdolCharacter){
            if(((IdolCharacter) AbstractDungeon.player).getIdolName() == IdolData.jsna){
                voices = new String[]{
                        "adv_dear_jsna_015_andk_001.ogg",
                        "adv_dear_jsna_015_andk_003.ogg",
                };
                AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 5.0F, DIALOG[4], false));
                addToBot(new ApplyPowerAction(this,this,new FearPinkGirlPower(this)));
            }
        }
        Random random = new Random();
        int index = random.nextInt(voices.length);
        SoundHelper.playSound("gkmasModResource/audio/voice/monster/nadeshiko/"+voices[index]);
    }

    public void deathVoice(){
        String[] voices = new String[]{
                "adv_dear_shro_014_andk_004.ogg",
                "adv_dear_ssmk_016_andk_003.ogg",
                "adv_dear_ttmr_014_andk_004.ogg"
        };
        Random random = new Random();
        int index = random.nextInt(voices.length);
        SoundHelper.playSound("gkmasModResource/audio/voice/monster/nadeshiko/"+voices[index]);
    }

    public void attackVoice(){
        String[] voices = new String[]{
                "adv_dear_kcna_015_andk_002.ogg",
                "adv_dear_kcna_016_andk_001.ogg",
        };
        Random random = new Random();
        int index = random.nextInt(voices.length);
        SoundHelper.playSound("gkmasModResource/audio/voice/monster/nadeshiko/"+voices[index]);
    }


}
