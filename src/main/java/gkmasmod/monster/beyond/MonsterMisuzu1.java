package gkmasmod.monster.beyond;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import gkmasmod.cards.free.Sleepy;
import gkmasmod.powers.*;

public class MonsterMisuzu1 extends CustomMonster {
    public static final String ID = "MonsterMisuzu1";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:MonsterMisuzu1");

    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public static final String[] DIALOG = monsterStrings.DIALOG;

    private static int MAX_HEALTH = 350000;

    private int bloodHitCount;

    private boolean isFirstMove = true;

    private int moveCount = 1, buffCount = 0;

    private int moneyAmt;

    private boolean isOutTriggered = false;

    public int attackTime = 0;

    public MonsterMisuzu1() {
        this(000.0F, 0.0F);
    }

    public MonsterMisuzu1(float x, float y) {
        super(NAME, ID, MAX_HEALTH, -8.0F, 0.0F, 200.0F, 240.0F, null, x, y);
        this.img = new Texture("gkmasModResource/img/monsters/Idol/MonsterMisuzu1.png");
        this.type = EnemyType.BOSS;
        if (AbstractDungeon.ascensionLevel >= 10) {
            setHp(200);
        } else {
            setHp(150);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 8));
            this.damage.add(new DamageInfo(this, 15));
            this.bloodHitCount = 2;
        } else {
            this.damage.add(new DamageInfo(this, 6));
            this.damage.add(new DamageInfo(this, 10));
            this.bloodHitCount = 2;
        }
    }

    public void usePreBattleAction() {
        if (AbstractDungeon.ascensionLevel >= 5) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MisuzuDreamPower(this,2),2));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MisuzuNightPower(this,2)));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MisuzuDreamPower(this,1),1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MisuzuNightPower(this,3)));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MisuzuSleepPower(this,1),1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MisuzuEncallPower(this)));
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
                addToBot(new MakeTempCardInDrawPileAction(new Sleepy(),2,true,true));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new NotGoodTune(AbstractDungeon.player,3),3));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new GreatGoodTune(AbstractDungeon.player,2),2));
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
                setMove((byte)1, Intent.DEBUFF);
                break;
        }
        moveCount=(moveCount+1)%2;
    }

    public void updateMove(){
        int tmp = (moveCount+1)%2;
        switch (tmp){
            case 0:
                setMove((byte)0, Intent.ATTACK, (this.damage.get(0)).base, this.bloodHitCount+this.attackTime, true);
                break;
            case 1:
                setMove((byte)1, Intent.DEBUFF);
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
