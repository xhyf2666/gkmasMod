package gkmasmod.monster.ending;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.combat.BloodShotEffect;
import com.megacrit.cardcrawl.vfx.combat.HeartBuffEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import com.megacrit.cardcrawl.vfx.combat.ViceCrushEffect;
import gkmasmod.cards.free.Sleepy;
import gkmasmod.cards.logic.WakeUp;
import gkmasmod.cards.sense.WantToGoThere;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.PlayerColorEnum;
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
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;

public class MisuzuBoss extends CustomMonster {
    public static final String ID = "Misuzu";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:Misuzu");

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

    private int sleepCount = 0;

    private boolean isOutTriggered = false;

    private int invincibleAmt;

    private int stage = 0;

    private int secondMove = 0;

    private boolean multiDamage = false;
    private int last_buff = 5;

    public MisuzuBoss() {
        this(000.0F, 0.0F);
    }

    public MisuzuBoss(float x, float y) {
        super(NAME, ID, MAX_HEALTH, -8.0F, 0.0F, 360.0F, 240.0F, null, x, y);
        this.img = new Texture("gkmasModResource/img/monsters/Misuzu/Misuzu_sleep.png");
        this.type = AbstractMonster.EnemyType.BOSS;
        if (AbstractDungeon.ascensionLevel >= 19) {
            setHp(2200);
        } else {
            setHp(1700);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 40));
            this.damage.add(new DamageInfo(this, 6));
            this.damage.add(new DamageInfo(this, 60));
            this.bloodHitCount = 4;
        } else {
            this.damage.add(new DamageInfo(this, 25));
            this.damage.add(new DamageInfo(this, 6));
            this.bloodHitCount = 3;
        }
        this.sleepCount = 0;
    }

    public void usePreBattleAction() {
        AbstractGameEffect effect =  new ChangeScene(ImageMaster.loadImage("gkmasModResource/img/bg/bg1.png"));
        AbstractDungeon.effectList.add(new LatterEffect(() -> {
            AbstractDungeon.effectsQueue.add(effect);
        }));
        (AbstractDungeon.getCurrRoom()).cannotLose = true;
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        CardCrawlGame.music.playTempBgmInstantly("gkmasModResource/audio/bgm/hmsz_001.ogg",true);

        invincibleAmt = 600;
        int MetallicizeAmt = 50;
        if (AbstractDungeon.ascensionLevel >= 19) {
            invincibleAmt -= 100;
            MetallicizeAmt += 30;
        }
        else if(AbstractDungeon.ascensionLevel >= 10) {
            MetallicizeAmt += 20;
        }
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, MetallicizeAmt));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new InvinciblePower(this, invincibleAmt), invincibleAmt));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, MetallicizeAmt), MetallicizeAmt));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MisuzuNature(this,100,300)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SleepPower(this)));
        helloVoice();
    }

    public void takeTurn() {
        int additionalAmount, i;

        if(stage==1&&this.secondMove == 2){
            this.secondMove = 3;
            CardCrawlGame.music.dispose();
            CardCrawlGame.music.playTempBgmInstantly(
                    String.format("gkmasModResource/audio/bgm/inst_%s.ogg",
                            IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getBgm(SkinSelectScreen.Inst.skinIndex)),
                    true);
        }

        switch (this.nextMove) {
            case 1:
                attackVoice();
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
                attackVoice();
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ViceCrushEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.4F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                        .get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break;
            case 3:
                sleepVoice();
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Sleepy(), 1, true, false, false, Settings.WIDTH * 0.2F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Sleepy(), 1));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Sleepy(), 1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 5), 5));
                break;
            case 4:
                rebirthVoice();
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;
            case 5:
                halfHealthVoice();
                additionalAmount = 0;
                if (hasPower("Strength") && (getPower("Strength")).amount < 0)
                    additionalAmount = -(getPower("Strength")).amount;
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BorderFlashEffect(new Color(0.8F, 0.5F, 1.0F, 1.0F))));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, additionalAmount + 2), additionalAmount + 2));
                switch (this.buffCount) {
                    case 0:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SleepyAttackPower(this)));
                        break;
                    case 1:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 2), 2));
                        break;
                    default:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 5), 5));
                        break;
                }
                this.buffCount++;
                break;
            case 6:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new AutoFullPowerValue(this,1),1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 2), 2));
                break;
            case 7:
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Sleepy(), 1, true, false, false, Settings.WIDTH * 0.2F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Sleepy(), 1));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Sleepy(), 1));
                break;
            case 8:
                AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_AWAKENEDONE_1"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new IntenseZoomEffect(this.hb.cX, this.hb.cY, true), 0.05F, true));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "REBIRTH"));
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if(stage==0&&!this.isOutTriggered&&this.sleepCount <=0){
            this.sleepCount++;
            setMove((byte)3, Intent.SLEEP);
            return;
        }
        if (stage==0&&this.sleepCount == 1&&!this.isOutTriggered) {
            this.isOutTriggered = true;
            this.img = new Texture("gkmasModResource/img/monsters/Misuzu/Misuzu.png");
            if(this.hasPower(MetallicizePower.POWER_ID)){
                addToBot(new RemoveSpecificPowerAction(this,this,MetallicizePower.POWER_ID));
            }
            stage = 1;
        }
        if(this.secondMove < 2){
            this.secondMove++;
        }
        if(stage==1){
            switch (this.moveCount % 4) {
                case 0:
                    if (AbstractDungeon.aiRng.randomBoolean()) {
                        setMove((byte)1, AbstractMonster.Intent.ATTACK, (this.damage.get(1)).base, this.bloodHitCount, true);
                        this.multiDamage = true;
                        break;
                    }
                    setMove((byte)2, AbstractMonster.Intent.ATTACK, (this.damage.get(0)).base);
                    break;
                case 1:
                    if(last_buff==0){
                        if(AbstractDungeon.aiRng.randomBoolean()) {
                            setMove((byte) 5, AbstractMonster.Intent.BUFF);
                            last_buff = 5;
                            break;
                        }
                        setMove((byte) 6, AbstractMonster.Intent.BUFF);
                        last_buff = 6;
                        break;
                    }
                    if(last_buff==5){
                        setMove((byte) 6, AbstractMonster.Intent.BUFF);
                        last_buff = 0;
                        break;
                    }
                    setMove((byte) 5, AbstractMonster.Intent.BUFF);
                    last_buff = 0;
                    break;
                case 2:
                    if (this.multiDamage) {
                        setMove((byte)2, AbstractMonster.Intent.ATTACK, (this.damage.get(0)).base);
                        break;
                    }
                    setMove((byte)1, AbstractMonster.Intent.ATTACK, (this.damage.get(1)).base, this.bloodHitCount, true);
                    this.multiDamage = false;
                    break;
                case 3:
                    if(last_buff==5){
                        setMove((byte) 6, AbstractMonster.Intent.BUFF);
                        last_buff = 0;
                        break;
                    }
                    setMove((byte) 5, AbstractMonster.Intent.BUFF);
                    last_buff = 0;
                    break;
            }
        }

        if(stage==2){
            switch (this.moveCount % 2) {
                case 0:
                    setMove((byte)1, AbstractMonster.Intent.ATTACK, (this.damage.get(2)).base, this.bloodHitCount, true);
                    break;
                case 1:
                    setMove((byte) 5, AbstractMonster.Intent.BUFF);
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
            AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 5.0F, DIALOG[1], false));
            CardCrawlGame.stopClock = true;
            GkmasMod.beat_hmsz++;
            SpireConfig config = null;
            try {
                config = new SpireConfig("GkmasMod", "config");
                // 读取配置
                config.setFloat("cardRate",PlayerHelper.getCardRate());
                config.setInt("beat_hmsz",GkmasMod.beat_hmsz);
                config.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void changeState(String key) {
        switch (key) {
            case "REBIRTH":
                this.maxHealth = 140000;
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
//                this.effect2 = new ChangeSceneEffectLeft(ImageMaster.loadImage("img/boss/bg/AnonSidebg00948.png"));
//
//                AbstractDungeon.effectList.add(new LatterEffect(() -> {
//                    AbstractDungeon.effectsQueue.add(this.effect2);
//                }));
                AbstractGameEffect effect = new ChangeScene(ImageMaster.loadImage("gkmasModResource/img/bg/bg2.png"));
                AbstractDungeon.effectList.add(new LatterEffect(() -> {
                    AbstractDungeon.effectsQueue.add(effect);
                }));
                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, this.maxHealth));
//                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new InvinciblePower(this, 9000), 9000));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SleepPower2(this)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new AutoFullPowerValue(this, 10), 10));
                AbstractDungeon.actionManager.addToBottom(new CanLoseAction());
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 11), 11));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SleepyAttackPower(this)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new FullPower(this)));
                this.moveCount = 0;
                this.nextMove = 0;
                this.bloodHitCount = 3;
                if(AbstractDungeon.player instanceof IdolCharacter&&AbstractDungeon.player.hasRelic(PocketBook.ID)){
                    ((PocketBook)AbstractDungeon.player.getRelic(PocketBook.ID)).startFinalCircle();
                }
                break;
        }
    }


    public void damage(DamageInfo info) {
        int previousHealth = this.currentHealth;

        super.damage(info);
        if (stage==0&&!this.isOutTriggered && this.currentHealth != previousHealth) {
            setMove((byte) 5, Intent.UNKNOWN);
            if(this.hasPower(MetallicizePower.POWER_ID)){
                addToBot(new ApplyPowerAction(this,this,new StrengthPower(this,10),10));
                addToBot(new RemoveSpecificPowerAction(this,this,MetallicizePower.POWER_ID));
            }
            createIntent();
            this.isOutTriggered = true;
            stage = 1;
            wakeUpVoice();
            this.img = new Texture("gkmasModResource/img/monsters/Misuzu/Misuzu.png");
        }

        if(stage==2&&!this.isOutTriggered && this.currentHealth <= 110000) {
            CardCrawlGame.music.dispose();
            CardCrawlGame.music.playTempBgmInstantly(
                    String.format("gkmasModResource/audio/song/%s_00%s.ogg",SkinSelectScreen.Inst.idolName,
                            IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getSong(SkinSelectScreen.Inst.skinIndex)),
                    true);
            this.img = new Texture("gkmasModResource/img/monsters/Misuzu/Misuzu.png");

            if(AbstractDungeon.player instanceof IdolCharacter){
                AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 2.0F, DIALOG[2], false));

                AbstractCard card=null;
                CommonEnum.IdolStyle style = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getStyle(SkinSelectScreen.Inst.skinIndex);
                if (style == CommonEnum.IdolStyle.GOOD_IMPRESSION) {
                    card = new WakeUp();
                    card.upgrade();
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, 1));
                }
                else if(style == CommonEnum.IdolStyle.YARUKI){
                    AbstractDungeon.player.obtainPotion(new OolongTea());
                    AbstractDungeon.player.obtainPotion(new OolongTea());
                }
                else{
                    card = new WantToGoThere();
                    card.upgrade();
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, 1));
                }
            }

            isOutTriggered = true;
        }

        if (stage!=2&&this.currentHealth <= 0 && !this.halfDead) {
            if ((AbstractDungeon.getCurrRoom()).cannotLose == true)
                this.halfDead = true;
            for (AbstractPower p : this.powers)
                p.onDeath();
            for (AbstractRelic r : AbstractDungeon.player.relics)
                r.onMonsterDeath(this);
            addToTop(new ClearCardQueueAction());
            this.powers.clear();
            deathVoice();
            stage = 1;
            isOutTriggered = true;
            CardCrawlGame.music.dispose();
            CardCrawlGame.music.playTempBgmInstantly("gkmasModResource/audio/bgm/gasha_002.ogg",true);
            AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 5.0F, DIALOG[0], false));
            setMove((byte) 8, AbstractMonster.Intent.UNKNOWN);
            createIntent();
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)8, AbstractMonster.Intent.UNKNOWN));

            applyPowers();
        }
    }

    public void helloVoice(){
        String[] voices = new String[]{
                "hmsz_001.ogg",
                "1_0010_02_hmsz_008.ogg",
                "2_0010_02_hmsz_001.ogg",
                "3_0011_02_hmsz_001.ogg",
                "3_0012_02_hmsz_001.ogg",
                "3_0012_02_hmsz_002.ogg",
                "ttmr_007_hmsz_011.ogg",
                "002_main_03_hmsz_010.ogg",
                "01-02_07_hmsz_001.ogg"
        };
        if(AbstractDungeon.player instanceof IdolCharacter){
            if(((IdolCharacter) AbstractDungeon.player).getIdolName() == IdolData.ttmr)
                voices = new String[]{
                        "3_0001_03_hmsz_001.ogg",
                        "3_0028_02_hmsz_001.ogg",
                };
        }
        Random random = new Random();
        int index = random.nextInt(voices.length);
        SoundHelper.playSound("gkmasModResource/audio/voice/"+voices[index]);
    }

    public void sleepVoice(){
        String[] voices = new String[]{
                "3_0011_01_hmsz_003.ogg",
                "3_0015_02_hmsz_001.ogg",
                "dear_ttmr_007_hmsz_017.ogg",
                "3_0015_03_hmsz.ogg"
        };
        Random random = new Random();
        int index = random.nextInt(voices.length);
        SoundHelper.playSound("gkmasModResource/audio/voice/"+voices[index]);
    }

    public void wakeUpVoice(){
        String[] voices = new String[]{
                "2_0010_01_hmsz_001.ogg",
                "3_0012_02_hmsz_004.ogg",
                "3_0016_02_hmsz_007.ogg"
        };
        Random random = new Random();
        int index = random.nextInt(voices.length);
        SoundHelper.playSound("gkmasModResource/audio/voice/"+voices[index]);
    }

    public void deathVoice(){
        String[] voices = new String[]{
                "2_0010_01_hmsz_008.ogg",
                "ttmr_009_hmsz_021.ogg",
                "ttmr_010_hmsz_017.ogg"
        };
        if(AbstractDungeon.player instanceof IdolCharacter){
            if(((IdolCharacter) AbstractDungeon.player).getIdolName() == IdolData.ttmr)
                voices = new String[]{
                        "ttmr_009_hmsz_008.ogg"
                };
        }
        Random random = new Random();
        int index = random.nextInt(voices.length);
        SoundHelper.playSound("gkmasModResource/audio/voice/"+voices[index]);
    }

    public void attackVoice(){
        String[] voices = new String[]{
                "3_0028_02_hmsz_003.ogg",
                "002_main_05_hmsz_009.ogg",
                "002_main_05_hmsz_010.ogg"
        };
        if(AbstractDungeon.player instanceof IdolCharacter){
            if(((IdolCharacter) AbstractDungeon.player).getIdolName() == IdolData.ttmr)
                voices = new String[]{
                        "3_0028_02_hmsz_003.ogg",
                        "3_0028_02_hmsz_006.ogg",
                };
        }

        Random random = new Random();
        int index = random.nextInt(voices.length);
        SoundHelper.playSound("gkmasModResource/audio/voice/"+voices[index]);
    }


    public void halfHealthVoice() {
        String[] voices = new String[]{
                "2_0010_02_hmsz_001.ogg",
                "3_0012_03_hmsz_010.ogg",
                "01_02_07_hmsz_003.ogg"
        };
        Random random = new Random();
        int index = random.nextInt(voices.length);
        SoundHelper.playSound("gkmasModResource/audio/voice/"+voices[index]);
    }

    public void rebirthVoice() {
        String[] voices = new String[]{
                "ttmr_008_hmsz_019.ogg"
        };
        Random random = new Random();
        int index = random.nextInt(voices.length);
        SoundHelper.playSound("gkmasModResource/audio/voice/"+voices[index]);
    }

}
