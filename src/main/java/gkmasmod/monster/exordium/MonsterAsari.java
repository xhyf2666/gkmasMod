package gkmasmod.monster.exordium;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.combat.ViceCrushEffect;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.ChangeScene;
import gkmasmod.monster.LatterEffect;
import gkmasmod.powers.*;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.SoundHelper;

import java.util.Random;

public class MonsterAsari extends CustomMonster {
    public static final String ID = "MonsterAsari";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:MonsterAsari");

    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public static final String[] DIALOG = monsterStrings.DIALOG;

    private static int MAX_HEALTH = 350000;

    private int bloodHitCount;

    private boolean isFirstMove = true;

    private int moveCount = 2, buffCount = 0;

    private int moneyAmt;

    private boolean isOutTriggered = false;

    public MonsterAsari() {
        this(000.0F, 0.0F);
    }

    public MonsterAsari(float x, float y) {
        super(NAME, ID, MAX_HEALTH, -8.0F, 0.0F, 360.0F, 240.0F, null, x, y);
        this.img = new Texture("gkmasModResource/img/monsters/Asari/MonsterAsari.png");
        this.type = EnemyType.BOSS;
        if (AbstractDungeon.ascensionLevel >= 10) {
            setHp(430);
        } else {
            setHp(400);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 8));
            this.damage.add(new DamageInfo(this, 12));
            this.bloodHitCount = 2;
        } else {
            this.damage.add(new DamageInfo(this, 6));
            this.damage.add(new DamageInfo(this, 10));
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
        if(AbstractDungeon.player instanceof MisuzuCharacter){
            String song = String.format("gkmasModResource/audio/song/%s_00%s.ogg", IdolData.hmsz,9);
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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new NoPhoneInClassPower(this)));
        if (AbstractDungeon.ascensionLevel >= 5) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new LifeNotStop(this,30),30));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new AsariChocolatePower(this,2).setMagic(2),2));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new LifeNotStop(this,20),20));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new AsariChocolatePower(this,1),1));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new AsariBentoPower(this,1),1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new AsariCarePower(this)));
//        helloVoice();
    }

    public void takeTurn() {
        int i;
        switch (this.nextMove) {
            case 0:
//                attackVoice();
                for (i = 0; i < this.bloodHitCount; i++)
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                            .get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
                break;
            case 1:
//                attackVoice();
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ViceCrushEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.4F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                        .get(1), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break;
            case 2:
//                debuffVoice();
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(AbstractDungeon.player, 2), 2));
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
                setMove((byte)2, Intent.BUFF);
                break;
        }
        moveCount=(moveCount+1)%3;
    }

    public void updateMove(){
        int tmp = (moveCount+2)%3;
        switch (tmp){
            case 0:
                setMove((byte)0, Intent.ATTACK, (this.damage.get(0)).base, this.bloodHitCount, true);
                break;
            case 1:
                setMove((byte)1, Intent.ATTACK, (this.damage.get(1)).base);
                break;
            case 2:
                setMove((byte)2, Intent.BUFF);
                break;
        }
        createIntent();
    }

    public void die() {
        if (!(AbstractDungeon.getCurrRoom()).cannotLose) {
            super.die();
//            deathVoice();
            onBossVictoryLogic();
            AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 5.0F, DIALOG[1], false));
        }
    }

}
