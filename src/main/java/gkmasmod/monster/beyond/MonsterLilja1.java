package gkmasmod.monster.beyond;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import gkmasmod.cards.free.Sleepy;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.powers.*;

public class MonsterLilja1 extends CustomMonster {
    public static final String ID = "MonsterLilja1";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:MonsterLilja1");

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

    public MonsterLilja1() {
        this(000.0F, 0.0F);
    }

    public MonsterLilja1(float x, float y) {
        super(NAME, ID, MAX_HEALTH, -8.0F, 0.0F, 200.0F, 480.0F, null, x, y);
        this.img = new Texture("gkmasModResource/img/monsters/Idol/MonsterLilja1.png");
        this.type = EnemyType.ELITE;
        if (AbstractDungeon.ascensionLevel >= 10) {
            setHp(60);
        } else {
            setHp(50);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 10));
            this.bloodHitCount = 1;
        } else {
            this.damage.add(new DamageInfo(this, 8));
            this.bloodHitCount = 1;
        }
    }

    public void usePreBattleAction() {
        if (AbstractDungeon.ascensionLevel >= 5) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new GoodImpression(this,8),8));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new NotAfraidAnymorePower(this,1)));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new GoodImpression(this,6),6));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new NotAfraidAnymorePower(this,1)));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BarricadePower(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new LiljaLightPower(this,2),2));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new LiljaEncallPower(this)));
        AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 3.0F, DIALOG[0], false));
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        String song = "gkmasModResource/audio/song/sakura_special.ogg";
        if (Gdx.files.internal(song).exists())
            CardCrawlGame.music.playTempBgmInstantly(song, true);
    }

    public void takeTurn() {
        int i;
        switch (this.nextMove) {
            case 0:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                        .get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                break;
            case 1:
                for (AbstractMonster mo:AbstractDungeon.getCurrRoom().monsters.monsters){
                    if(!mo.isDeadOrEscaped()&& !AbstractMonsterPatch.friendlyField.friendly.get(mo)){
                        addToBot(new HealAction(mo,mo,10));
                    }
                }
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        switch (moveCount){
            case 0:
                setMove((byte)0, Intent.ATTACK, (this.damage.get(0)).base);
                break;
            case 1:
                setMove((byte)1, Intent.BUFF);
                break;
        }
        moveCount=(moveCount+1)%2;
    }

    public void die() {
        super.die();
        AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 2.0F, DIALOG[1], false));
    }

}
