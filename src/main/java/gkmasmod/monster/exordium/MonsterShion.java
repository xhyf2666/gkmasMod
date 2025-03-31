package gkmasmod.monster.exordium;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.combat.BloodShotEffect;
import com.megacrit.cardcrawl.vfx.combat.ViceCrushEffect;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.ChangeScene;
import gkmasmod.monster.LatterEffect;
import gkmasmod.monster.friend.FriendNunu;
import gkmasmod.powers.*;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.SoundHelper;

import java.util.Random;

public class MonsterShion extends CustomMonster {
    public static final String ID = "MonsterShion";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:MonsterShion");

    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public static final String[] DIALOG = monsterStrings.DIALOG;

    private static int MAX_HEALTH = 350000;

    private int bloodHitCount;

    private boolean isFirstMove = true;

    private int moveCount = 2, buffCount = 0;

    private int moneyAmt;

    public int attackTime = 0;

    private boolean isOutTriggered = false;

    public MonsterShion() {
        this(000.0F, 0.0F);
    }

    public MonsterShion(float x, float y) {
        super(NAME, ID, MAX_HEALTH, -8.0F, 0.0F, 360.0F, 240.0F, null, x, y);
        this.img = new Texture("gkmasModResource/img/monsters/Shion/MonsterShion.png");
        this.type = EnemyType.BOSS;
        if (AbstractDungeon.ascensionLevel >= 10) {
            setHp(470);
        } else {
            setHp(400);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 10));
            this.damage.add(new DamageInfo(this, 25));
            this.bloodHitCount = 2;
        } else {
            this.damage.add(new DamageInfo(this, 8));
            this.damage.add(new DamageInfo(this, 22));
            this.bloodHitCount = 2;
        }
    }

    public void usePreBattleAction() {
        GkmasMod.renderScene = false;
        AbstractGameEffect effect =  new ChangeScene(ImageMaster.loadImage("gkmasModResource/img/bg/bg4.png"));
        AbstractDungeon.effectList.add(new LatterEffect(() -> {
            AbstractDungeon.effectsQueue.add(effect);
        }));
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        CardCrawlGame.music.playTempBgmInstantly("gkmasModResource/audio/bgm/bgm_produce_2nd_audition_001.ogg",true);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ShionMethod(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ShionWord(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new FantasyKiller(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new CrisisTime(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ShionShadow(this)));
        helloVoice();
    }

    public void takeTurn() {
        int i;
        switch (this.nextMove) {
            case 0:
                attackVoice();
                for (i = 0; i < this.bloodHitCount+this.attackTime; i++)
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                            .get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
                break;
            case 1:
                attackVoice();
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ViceCrushEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.4F));
                for(i=0;i<this.attackTime+1;i++)
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                            .get(1), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break;
            case 2:
                debuffVoice();
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new NotGoodTune(AbstractDungeon.player, 2), 2));
                break;

        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        switch (moveCount){
            case 0:
                setMove((byte)0, Intent.ATTACK, (this.damage.get(0)).base, this.bloodHitCount+this.attackTime, true);
                break;
            case 1:
                if(this.attackTime==0)
                    setMove((byte)1, Intent.ATTACK, (this.damage.get(1)).base);
                else
                    setMove((byte)1, Intent.ATTACK, (this.damage.get(1)).base, 1+this.attackTime, true);
                break;
            case 2:
                setMove((byte)2, Intent.DEBUFF);
                break;
        }
        moveCount=(moveCount+1)%3;
    }

    public void updateMove(){
        int tmp = (moveCount+2)%3;
        switch (tmp){
            case 0:
                setMove((byte)0, Intent.ATTACK, (this.damage.get(0)).base, this.bloodHitCount+this.attackTime, true);
                break;
            case 1:
                if(this.attackTime==0)
                    setMove((byte)1, Intent.ATTACK, (this.damage.get(1)).base);
                else
                    setMove((byte)1, Intent.ATTACK, (this.damage.get(1)).base, 1+this.attackTime, true);
                break;
            case 2:
                setMove((byte)2, Intent.DEBUFF);
                break;
        }
        createIntent();
    }

    public void die() {
        if (!(AbstractDungeon.getCurrRoom()).cannotLose) {
            super.die();
            deathVoice();
            onBossVictoryLogic();
            AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 5.0F, String.format(DIALOG[2],AbstractDungeon.player.title), false));
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
                "adv_dear_amao_013_sson_003.ogg",
                "adv_dear_amao_013_sson_016.ogg",
                "adv_dear_hrnm_013_sson_002.ogg",
                "adv_dear_hrnm_015_sson_001.ogg",
                "adv_dear_kllj_012_sson_019.ogg",
                "adv_dear_ssmk_013_sson_002.ogg",
        };
        if(AbstractDungeon.player instanceof IdolCharacter){
            if(((IdolCharacter) AbstractDungeon.player).getIdolName() == IdolData.jsna)
                voices = new String[]{
                        "adv_dear_jsna_015_sson_005.ogg"
                };
            if(((IdolCharacter) AbstractDungeon.player).getIdolName() == IdolData.hrnm){
                voices = new String[]{
                        "adv_dear_hrnm_014_sson_016.ogg"
                };
                AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 5.0F, DIALOG[3], false));
            }
        }
        Random random = new Random();
        int index = random.nextInt(voices.length);
        SoundHelper.playSound("gkmasModResource/audio/voice/monster/shion/"+voices[index]);
    }

    public void deathVoice(){
        String[] voices = new String[]{
                "adv_dear_amao_016_sson_001.ogg",
                "adv_dear_amao_016_sson_002.ogg",
                "adv_dear_amao_016_sson_003.ogg",
                "adv_dear_amao_016_sson_005.ogg",
                "adv_dear_amao_016_sson_009.ogg",
                "adv_dear_ssmk_019_sson_003.ogg"
        };
        if(AbstractDungeon.player instanceof IdolCharacter){
            if(((IdolCharacter) AbstractDungeon.player).getIdolName() == IdolData.hski)
                voices = new String[]{
                        "adv_dear_ttmr_013_sson_020.ogg"
                };
            if(((IdolCharacter) AbstractDungeon.player).getIdolName() == IdolData.fktn)
                voices = new String[]{
                        "adv_dear_ttmr_013_sson_010.ogg"
                };
            if(((IdolCharacter) AbstractDungeon.player).getIdolName() == IdolData.ttmr)
                voices = new String[]{
                        "adv_dear_ttmr_013_sson_010.ogg",
                        "adv_dear_ttmr_013_sson_020.ogg"
                };
        }
        Random random = new Random();
        int index = random.nextInt(voices.length);
        SoundHelper.playSound("gkmasModResource/audio/voice/monster/shion/"+voices[index]);
    }

    public void attackVoice(){
        String[] voices = new String[]{
                "adv_dear_amao_013_sson_006.ogg",
                "adv_dear_amao_013_sson_013.ogg",
        };
        Random random = new Random();
        int index = random.nextInt(voices.length);
        SoundHelper.playSound("gkmasModResource/audio/voice/monster/shion/"+voices[index]);
    }

    public void debuffVoice() {
        String[] voices = new String[]{
                "adv_dear_amao_014_sson_014.ogg",
                "adv_dear_ssmk_012_sson_013.ogg",
        };
        Random random = new Random();
        int index = random.nextInt(voices.length);
        SoundHelper.playSound("gkmasModResource/audio/voice/monster/shion/" + voices[index]);

    }
}
