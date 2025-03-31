package gkmasmod.monster.exordium;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.combat.ViceCrushEffect;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.ChangeScene;
import gkmasmod.monster.LatterEffect;
import gkmasmod.powers.*;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.SoundHelper;
import gkmasmod.utils.ThreeSizeHelper;

import java.util.Random;

public class MonsterGekka extends CustomMonster {
    public static final String ID = "MonsterGekka";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:MonsterGekka");

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

    public MonsterGekka() {
        this(000.0F, 0.0F);
    }

    public MonsterGekka(float x, float y) {
        super(NAME, ID, MAX_HEALTH, -8.0F, 0.0F, 360.0F, 240.0F, null, x, y);
        this.img = new Texture("gkmasModResource/img/monsters/Gekka/MonsterGekka.png");
        this.type = EnemyType.BOSS;
        if (AbstractDungeon.ascensionLevel >= 10) {
            setHp(520);
        } else {
            setHp(470);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 11));
            this.damage.add(new DamageInfo(this, 34));
            this.bloodHitCount = 3;
        } else {
            this.damage.add(new DamageInfo(this, 9));
            this.damage.add(new DamageInfo(this, 25));
            this.bloodHitCount = 3;
        }
    }

    public void usePreBattleAction() {
        GkmasMod.renderScene = false;
        AbstractGameEffect effect =  new ChangeScene(ImageMaster.loadImage("gkmasModResource/img/bg/bg5.png"));
        AbstractDungeon.effectList.add(new LatterEffect(() -> {
            AbstractDungeon.effectsQueue.add(effect);
        }));
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        CardCrawlGame.music.playTempBgmInstantly("gkmasModResource/audio/bgm/bgm_adv_inst_all_011.ogg",true);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new TopIdolPower(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new GekkaOldWound(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new GekkaInterest(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StageDeviceFlow(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new GekkaWill(this,0)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new InnocencePower(this,1),1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RebirthPower(this,200*ThreeSizeHelper.getHealthRate(3)),200*ThreeSizeHelper.getHealthRate(3)));
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
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 3), 3));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this,this,10* ThreeSizeHelper.getHealthRate(3)));
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
                setMove((byte)2, Intent.DEFEND_BUFF);
                break;
        }
        moveCount=(moveCount+1)%3;
    }

    public void die() {
        if (!(AbstractDungeon.getCurrRoom()).cannotLose) {
            super.die();
            deathVoice();
            onBossVictoryLogic();
            AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 5.0F, String.format(DIALOG[1],AbstractDungeon.player.title), false));
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
                "adv_dear_amao_018_sgka_029.ogg",
                "adv_dear_hrnm_018_sgka_035.ogg",
                "adv_dear_hrnm_018_sgka_009.ogg",
        };
        Random random = new Random();
        int index = random.nextInt(voices.length);
        SoundHelper.playSound("gkmasModResource/audio/voice/monster/gekka/"+voices[index]);
    }

    public void deathVoice(){
        String[] voices = new String[]{
                "adv_dear_amao_019_sgka_003.ogg",
                "adv_dear_amao_019_sgka_010.ogg",
                "adv_dear_hrnm_019_sgka_003.ogg",
        };
        Random random = new Random();
        int index = random.nextInt(voices.length);
        SoundHelper.playSound("gkmasModResource/audio/voice/monster/gekka/"+voices[index]);
    }

    public void attackVoice(){
        String[] voices = new String[]{
                "adv_dear_amao_018_sgka_009.ogg",
                "adv_dear_amao_018_sgka_015.ogg",
                "adv_dear_hrnm_018_sgka_005.ogg",
                "adv_dear_hrnm_018_sgka_024.ogg"
        };
        Random random = new Random();
        int index = random.nextInt(voices.length);
        SoundHelper.playSound("gkmasModResource/audio/voice/monster/gekka/"+voices[index]);
    }

}
