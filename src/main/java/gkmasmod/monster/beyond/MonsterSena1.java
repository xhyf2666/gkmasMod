package gkmasmod.monster.beyond;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.combat.ViceCrushEffect;
import gkmasmod.cards.special.WorkFighter;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.ChangeScene;
import gkmasmod.monster.LatterEffect;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.powers.*;

public class MonsterSena1 extends CustomMonster {
    public static final String ID = "MonsterSena1";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:MonsterSena1");

    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public static final String[] DIALOG = monsterStrings.DIALOG;

    private static int MAX_HEALTH = 350000;

    private int bloodHitCount;

    private boolean isFirstMove = true;

    private int moveCount = 0, buffCount = 0;

    private int moneyAmt;

    private boolean isOutTriggered = false;

    public int attackTime = 0;

    public MonsterSena1() {
        this(000.0F, 0.0F);
    }

    public MonsterSena1(float x, float y) {
        super(NAME, ID, MAX_HEALTH, -8.0F, 0.0F, 200.0F, 240.0F, null, x, y);
        this.img = new Texture("gkmasModResource/img/monsters/Idol/MonsterSena1.png");
        this.type = EnemyType.BOSS;
        if (AbstractDungeon.ascensionLevel >= 10) {
            setHp(220);
        } else {
            setHp(170);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 7));
            this.damage.add(new DamageInfo(this, 14));
            this.bloodHitCount = 2;
        } else {
            this.damage.add(new DamageInfo(this, 5));
            this.damage.add(new DamageInfo(this, 10));
            this.bloodHitCount = 2;
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
        String song = "gkmasModResource/audio/bgm/bgm_adv_inst_begrazia_001.ogg";
        if (Gdx.files.internal(song).exists())
            CardCrawlGame.music.playTempBgmInstantly(song, true);
        if (AbstractDungeon.ascensionLevel >= 5) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SenaMoreActionPower(this,2),2));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SenaTopPower(this,8),8));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SenaTeachPower(this,10),10));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SenaMoreActionPower(this,1),1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SenaTopPower(this,5),5));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SenaEncallPower(this)));
    }

    public void takeTurn() {
        int i;
        switch (this.nextMove) {
            case 0:
                for (i = 0; i < this.bloodHitCount+this.attackTime; i++)
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                            .get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
                break;
            case 1:
                for (AbstractMonster mo:AbstractDungeon.getCurrRoom().monsters.monsters){
                    if(!mo.isDeadOrEscaped()&& !AbstractMonsterPatch.friendlyField.friendly.get(mo)){
                        addToBot(new HealAction(mo,mo,600));
                        addToBot(new ApplyPowerAction(mo,mo,new GoodTune(mo,2),2));
                    }
                }
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ViceCrushEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.4F));
                for(i=0;i<this.attackTime+1;i++)
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                            .get(1), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
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
                setMove((byte)1, Intent.DEFEND_BUFF);
                break;
            case 2:
                if(this.attackTime==0)
                    setMove((byte)2, Intent.ATTACK, (this.damage.get(1)).base);
                else
                    setMove((byte)2, Intent.ATTACK, (this.damage.get(1)).base, 1+this.attackTime, true);
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
                setMove((byte)1, Intent.DEFEND_BUFF);
                break;
            case 2:
                if(this.attackTime==0)
                    setMove((byte)2, Intent.ATTACK, (this.damage.get(1)).base);
                else
                    setMove((byte)2, Intent.ATTACK, (this.damage.get(1)).base, 1+this.attackTime, true);
                break;
        }
        createIntent();
    }

    public void die() {
        super.die();
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            onBossVictoryLogic();
            AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 2.0F, DIALOG[1], false));
        }
        else{
            AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 2.0F, DIALOG[0], false));
        }
    }

}
