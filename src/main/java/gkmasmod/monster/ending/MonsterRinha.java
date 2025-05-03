package gkmasmod.monster.ending;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.InvinciblePower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
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
import gkmasmod.cards.special.Kiss;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.ChangeScene;
import gkmasmod.monster.LatterEffect;
import gkmasmod.potion.OolongTea;
import gkmasmod.powers.*;
import gkmasmod.relics.PocketBook;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.CommonEnum;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.PlayerHelper;
import gkmasmod.utils.SoundHelper;

import java.io.IOException;
import java.util.Random;

public class MonsterRinha extends CustomMonster {
    public static final String ID = "MonsterRinha";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:MonsterRinha");

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

    private int moveCount = 1, buffCount = 0;

    private boolean isOutTriggered = false;

    private int invincibleAmt;

    public int stage = 1;

    private int secondMove = 0;

    private boolean multiDamage = false;
    private int last_buff = 5;

    public MonsterRinha() {
        this(000.0F, 0.0F);
    }

    public MonsterRinha(float x, float y) {
        super(NAME, ID, MAX_HEALTH, -8.0F, 0.0F, 360.0F, 480.0F, null, x, y);
        this.img = new Texture("gkmasModResource/img/monsters/Rinha/MonsterRinha1.png");
        this.type = EnemyType.BOSS;
        if (AbstractDungeon.ascensionLevel >= 10) {
            setHp(1000);
        } else {
            setHp(700);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 20));
            this.damage.add(new DamageInfo(this, 6));
            this.damage.add(new DamageInfo(this, 10));
            this.bloodHitCount = 3;
        } else {
            this.damage.add(new DamageInfo(this, 15));
            this.damage.add(new DamageInfo(this, 4));
            this.damage.add(new DamageInfo(this, 10));
            this.bloodHitCount = 2;
        }
    }

    public void usePreBattleAction() {
        AbstractGameEffect effect =  new ChangeScene(ImageMaster.loadImage("gkmasModResource/img/bg/bg8.png"));
        AbstractDungeon.effectList.add(new LatterEffect(() -> {
            AbstractDungeon.effectsQueue.add(effect);
        }));
        (AbstractDungeon.getCurrRoom()).cannotLose = true;
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        CardCrawlGame.music.playTempBgmInstantly("gkmasModResource/audio/song/hmsz_002.ogg",true);
//        helloVoice();
        addToBot(new ApplyPowerAction(this, this, new FateCommunityPower2(this)));

        if(AbstractDungeon.ascensionLevel>10){
            addToBot(new ApplyPowerAction(this, this, new ThunderControlPower(this, 1), 1));
            addToBot(new ApplyPowerAction(this, this, new OurSunPower(this, 3), 3));
        }
        else {
            addToBot(new ApplyPowerAction(this, this, new ThunderControlPower(this, 1), 1));
            addToBot(new ApplyPowerAction(this, this, new OurSunPower(this, 2), 2));
        }
        addToBot(new ApplyPowerAction(this,this,new RinhaEndPower(this)));
//        if(AbstractDungeon.player instanceof IdolCharacter){
//            IdolCharacter idol = (IdolCharacter) AbstractDungeon.player;
//            if(idol.idolData.idolName.equals(IdolData.fktn)){
//                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new GoodImpression(AbstractDungeon.player, -5), -5));
//            }
//        }
    }

    public void takeTurn() {
        int additionalAmount, i;

        switch (this.nextMove) {
            case 1:
//                attackVoice();
                if (Settings.FAST_MODE) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new BloodShotEffect(this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.bloodHitCount), 0.25F));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new BloodShotEffect(this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.bloodHitCount), 0.6F));
                }
                for (i = 0; i < this.bloodHitCount; i++)
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                            .get(1), AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
                break;
            case 2:
//                attackVoice();
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ViceCrushEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.4F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                        .get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Kiss(), 1, true, false, false, Settings.WIDTH * 0.2F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Wound(), 1));
                break;
            case 4:
//                rebirthVoice();
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;
            case 5:
//                halfHealthVoice();
                additionalAmount = 0;
                if (hasPower("Strength") && (getPower("Strength")).amount < 0)
                    additionalAmount = -(getPower("Strength")).amount;
                if(additionalAmount>0)
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, additionalAmount), additionalAmount));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BorderFlashEffect(new Color(0.8F, 0.5F, 1.0F, 1.0F))));
                switch (this.buffCount) {
                    case 0:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new OurSunPower(this,1),1));
                        break;
                    case 1:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BalancePower(this)));
                        break;
                    case 2:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThunderControlPower(this, 1), 1));
                        break;
                    default:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 5), 5));
                        break;
                }
                this.buffCount++;
                break;
            case 6:
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 100));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, 30), 30));
                break;
            case 7:
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Kiss(), 1, true, false, false, Settings.WIDTH * 0.2F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Kiss(), 1));
                break;
            case 8:
                AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_AWAKENEDONE_1"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new IntenseZoomEffect(this.hb.cX, this.hb.cY, true), 0.05F, true));
                if(stage==2)
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "REBIRTH"));
                else
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "REBIRTH2"));
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if(this.secondMove < 2){
            this.secondMove++;
        }
        if(stage==1){
            switch (this.moveCount % 4) {
                case 0:
                    if (AbstractDungeon.aiRng.randomBoolean()) {
                        setMove((byte)1, Intent.ATTACK, (this.damage.get(1)).base, this.bloodHitCount, true);
                        this.multiDamage = true;
                        break;
                    }
                    setMove((byte)2, Intent.ATTACK, (this.damage.get(0)).base);
                    break;
                case 1:
                    if(last_buff==0){
                        if(AbstractDungeon.aiRng.randomBoolean()) {
                            setMove((byte) 5, Intent.BUFF);
                            last_buff = 5;
                        }
                        else{
                            setMove((byte) 6, Intent.BUFF);
                            last_buff = 6;
                        }
                        break;
                    }
                    if(last_buff==5){
                        setMove((byte) 6, Intent.BUFF);
                    }
                    else{
                        setMove((byte) 5, Intent.BUFF);
                    }
                    last_buff = 0;
                    break;

                case 2:
                    if (this.multiDamage) {
                        setMove((byte)2, Intent.ATTACK, (this.damage.get(0)).base);
                        break;
                    }
                    setMove((byte)1, Intent.ATTACK, (this.damage.get(1)).base, this.bloodHitCount, true);
                    this.multiDamage = false;
                    break;
                case 3:
                    if(last_buff==5){
                        setMove((byte) 6, Intent.BUFF);
                        last_buff = 0;
                        break;
                    }
                    setMove((byte) 5, Intent.BUFF);
                    last_buff = 0;
                    break;
            }
        }

        if(stage==2){
            switch (this.moveCount % 3) {
                case 0:
                    setMove((byte)1, Intent.ATTACK, (this.damage.get(1)).base, this.bloodHitCount, true);
                    break;
                case 1:
                    setMove((byte) 5, Intent.BUFF);
                    break;
                case 2:
                    setMove((byte)2, Intent.ATTACK, (this.damage.get(0)).base);
                    break;
            }
        }

        if(stage==3){
            switch (this.moveCount % 2) {
                case 0:
                    setMove((byte)1, Intent.ATTACK, (this.damage.get(1)).base, this.bloodHitCount, true);
                    break;
                case 1:
                    setMove((byte)2, Intent.ATTACK, (this.damage.get(0)).base);
                    break;
            }
        }

        this.moveCount++;
    }

    public void die() {
        if (!(AbstractDungeon.getCurrRoom()).cannotLose) {
            super.die();
            onBossVictoryLogic();
            onFinalBossVictoryLogic();
            AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 5.0F, DIALOG[3], false));
            CardCrawlGame.stopClock = true;
        }
    }

    public void changeState(String key) {
        switch (key) {
            case "REBIRTH":
                this.maxHealth = 1300;
                if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                    PocketBook book = (PocketBook)AbstractDungeon.player.getRelic(PocketBook.ID);
                    this.maxHealth = (int)(this.maxHealth*book.healthRate);
                }
                this.img = new Texture("gkmasModResource/img/monsters/Rinha/MonsterRinha2.png");
                this.halfDead = false;
                this.buffCount = 3;
                stage = 2;
                this.isOutTriggered = false;
                if (Settings.isEndless && AbstractDungeon.player.hasBlight("ToughEnemies")) {
                    float mod = AbstractDungeon.player.getBlight("ToughEnemies").effectFloat();
                    this.maxHealth = (int)(this.maxHealth * mod);
                }
                if (ModHelper.isModEnabled("MonsterHunter"))
                    this.currentHealth = (int)(this.currentHealth * 1.5F);
                AbstractGameEffect effect = new ChangeScene(ImageMaster.loadImage("gkmasModResource/img/bg/bg7.png"));
                AbstractDungeon.effectList.add(new LatterEffect(() -> {
                    AbstractDungeon.effectsQueue.add(effect);
                }));
                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, this.maxHealth));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new IdolExamPower(this, (int) (this.maxHealth*0.4)), (int) (this.maxHealth*0.4)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new TrueLateBloomerPower(this, 2).setMagic2(1), 2));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BornImitatorPower(this, 1), 1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new GreatGoodTune(this, 2), 2));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RinhaCarePower(this)));
                this.moveCount = 0;
                this.nextMove = 0;
                this.bloodHitCount = 3;
                break;
            case "REBIRTH2":
                this.maxHealth = 99999999;
                this.halfDead = false;
                this.buffCount = 3;
                stage = 3;
                this.isOutTriggered = false;
                AbstractGameEffect effect2 = new ChangeScene(ImageMaster.loadImage("gkmasModResource/img/bg/bg6.png"));
                AbstractDungeon.effectList.add(new LatterEffect(() -> {
                    AbstractDungeon.effectsQueue.add(effect2);
                }));
                this.img = new Texture("gkmasModResource/img/monsters/Rinha/MonsterRinha3.png");
                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, this.maxHealth));
                AbstractDungeon.actionManager.addToBottom(new CanLoseAction());
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this,this,IdolExamPower.POWER_ID));
                this.moveCount = 0;
                this.nextMove = 0;
                this.bloodHitCount = 3;
                AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 4.0F, DIALOG[4], false));

                if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                    ((PocketBook)AbstractDungeon.player.getRelic(PocketBook.ID)).startFinalCircle();
                    ((PocketBook)AbstractDungeon.player.getRelic(PocketBook.ID)).rinhaLast = 1;
                }
                CardCrawlGame.music.dispose();
                CardCrawlGame.music.unsilenceBGM();
                AbstractDungeon.scene.fadeOutAmbiance();
                if(AbstractDungeon.player instanceof MisuzuCharacter){
                    CardCrawlGame.music.playTempBgmInstantly("gkmasModResource/audio/song/hmsz_002.ogg",true);
                }
                else{
                    String song = String.format("gkmasModResource/audio/song/%s_00%s.ogg",SkinSelectScreen.Inst.idolName,
                            IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getSong(SkinSelectScreen.Inst.skinIndex));
                    if(!Gdx.files.internal(song).exists()) {
                        song = String.format("gkmasModResource/audio/song/%s_00%s.ogg",SkinSelectScreen.Inst.idolName,2);

                    }
                    CardCrawlGame.music.playTempBgmInstantly(song, true);
                }
                break;
        }
    }


    public void damage(DamageInfo info) {
        super.damage(info);

        if (stage<3&&this.currentHealth <= 0 && !this.halfDead) {
            if ((AbstractDungeon.getCurrRoom()).cannotLose == true)
                this.halfDead = true;
            for (AbstractPower p : this.powers)
                p.onDeath();
            for (AbstractRelic r : AbstractDungeon.player.relics)
                r.onMonsterDeath(this);
            addToTop(new ClearCardQueueAction());

            for(AbstractPower power: this.powers){
                if(power.type== AbstractPower.PowerType.DEBUFF){
                    addToBot(new RemoveSpecificPowerAction(this, this, power));
                }
            }

//            deathVoice();
            stage++;
            isOutTriggered = true;
            if(stage==3){
                AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 5.0F, DIALOG[1], false));
            }
            else{
                AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 5.0F, DIALOG[0], false));
            }
            setMove((byte) 8, Intent.UNKNOWN);
            createIntent();
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)8, Intent.UNKNOWN));

            applyPowers();
        }
    }

//    public void helloVoice(){
//        String[] voices = new String[]{
//                "hmsz_001.ogg",
//                "1_0010_02_hmsz_008.ogg",
//                "2_0010_02_hmsz_001.ogg",
//                "3_0011_02_hmsz_001.ogg",
//                "3_0012_02_hmsz_001.ogg",
//                "3_0012_02_hmsz_002.ogg",
//                "ttmr_007_hmsz_011.ogg",
//                "002_main_03_hmsz_010.ogg",
//                "01-02_07_hmsz_001.ogg"
//        };
//        if(AbstractDungeon.player instanceof IdolCharacter){
//            if(((IdolCharacter) AbstractDungeon.player).getIdolName() == IdolData.ttmr)
//                voices = new String[]{
//                        "3_0001_03_hmsz_001.ogg",
//                        "3_0028_02_hmsz_001.ogg",
//                };
//        }
//        Random random = new Random();
//        int index = random.nextInt(voices.length);
//        SoundHelper.playSound("gkmasModResource/audio/voice/"+voices[index]);
//    }
//
//    public void deathVoice(){
//        String[] voices = new String[]{
//                "2_0010_01_hmsz_008.ogg",
//                "ttmr_009_hmsz_021.ogg",
//                "ttmr_010_hmsz_017.ogg"
//        };
//        if(AbstractDungeon.player instanceof IdolCharacter){
//            if(((IdolCharacter) AbstractDungeon.player).getIdolName() == IdolData.ttmr)
//                voices = new String[]{
//                        "ttmr_009_hmsz_008.ogg"
//                };
//        }
//        Random random = new Random();
//        int index = random.nextInt(voices.length);
//        SoundHelper.playSound("gkmasModResource/audio/voice/"+voices[index]);
//    }
//
//    public void attackVoice(){
//        String[] voices = new String[]{
//                "3_0028_02_hmsz_003.ogg",
//                "002_main_05_hmsz_009.ogg",
//                "002_main_05_hmsz_010.ogg"
//        };
//        if(AbstractDungeon.player instanceof IdolCharacter){
//            if(((IdolCharacter) AbstractDungeon.player).getIdolName() == IdolData.ttmr)
//                voices = new String[]{
//                        "3_0028_02_hmsz_003.ogg",
//                        "3_0028_02_hmsz_006.ogg",
//                };
//        }
//
//        Random random = new Random();
//        int index = random.nextInt(voices.length);
//        SoundHelper.playSound("gkmasModResource/audio/voice/"+voices[index]);
//    }
//
//    public void halfHealthVoice() {
//        String[] voices = new String[]{
//                "2_0010_02_hmsz_001.ogg",
//                "3_0012_03_hmsz_010.ogg",
//                "01_02_07_hmsz_003.ogg"
//        };
//        Random random = new Random();
//        int index = random.nextInt(voices.length);
//        SoundHelper.playSound("gkmasModResource/audio/voice/"+voices[index]);
//    }
//
//    public void rebirthVoice() {
//        String[] voices = new String[]{
//                "ttmr_008_hmsz_019.ogg"
//        };
//        Random random = new Random();
//        int index = random.nextInt(voices.length);
//        SoundHelper.playSound("gkmasModResource/audio/voice/"+voices[index]);
//    }

}
