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
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.combat.ViceCrushEffect;
import gkmasmod.actions.GrowAction;
import gkmasmod.growEffect.BlockGrow;
import gkmasmod.growEffect.DamageGrow;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.ChangeScene;
import gkmasmod.monster.LatterEffect;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.powers.*;

public class MonsterUme1 extends CustomMonster {
    public static final String ID = "MonsterUme1";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:MonsterUme1");

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

    public MonsterUme1() {
        this(000.0F, 0.0F);
    }

    public MonsterUme1(float x, float y) {
        super(NAME, ID, MAX_HEALTH, -8.0F, 0.0F, 200.0F, 240.0F, null, x, y);
        this.img = new Texture("gkmasModResource/img/monsters/Idol/MonsterUme1.png");
        this.type = EnemyType.BOSS;
        if (AbstractDungeon.ascensionLevel >= 10) {
            setHp(240);
        } else {
            setHp(180);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 6));
            this.damage.add(new DamageInfo(this, 14));
            this.bloodHitCount = 2;
        } else {
            this.damage.add(new DamageInfo(this, 5));
            this.damage.add(new DamageInfo(this, 10));
            this.bloodHitCount = 2;
        }
    }

    public void usePreBattleAction() {
        if (AbstractDungeon.ascensionLevel >= 5) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new UmeGreatGoodTunePower(this,3),3));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new UmeStancePower(this,6)));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new UmeGreatGoodTunePower(this,2),2));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new UmeStancePower(this,8)));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new UmeBecomeIdolPower(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new UmeEncallPower(this)));
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
                addToBot(new GrowAction(DamageGrow.growID, GrowAction.GrowType.all,-2));
                addToBot(new GrowAction(BlockGrow.growID, GrowAction.GrowType.all,-2));
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
        int tmp = (moveCount+2)%2;
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
