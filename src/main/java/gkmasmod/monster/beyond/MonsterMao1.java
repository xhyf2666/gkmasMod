package gkmasmod.monster.beyond;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import gkmasmod.cards.free.Sleepy;
import gkmasmod.powers.*;

public class MonsterMao1 extends CustomMonster {
    public static final String ID = "MonsterMao1";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:MonsterMao1");

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

    public MonsterMao1() {
        this(000.0F, 0.0F);
    }

    public MonsterMao1(float x, float y) {
        super(NAME, ID, MAX_HEALTH, -8.0F, 0.0F, 200.0F, 480.0F, null, x, y);
        this.img = new Texture("gkmasModResource/img/monsters/Idol/MonsterMao1.png");
        this.type = EnemyType.ELITE;
        if (AbstractDungeon.ascensionLevel >= 10) {
            setHp(100);
        } else {
            setHp(90);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 8));
            this.bloodHitCount = 2;
        } else {
            this.damage.add(new DamageInfo(this, 6));
            this.bloodHitCount = 2;
        }
    }

    public void usePreBattleAction() {
        if (AbstractDungeon.ascensionLevel >= 5) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this,2)));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MaoMagicPower(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MaoEncallPower(this,2),2));
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        String song = "gkmasModResource/audio/song/snow_special.ogg";
        if (Gdx.files.internal(song).exists())
            CardCrawlGame.music.playTempBgmInstantly(song, true);
    }

    public void takeTurn() {
        int i;
        switch (this.nextMove) {
            case 0:
                for (i = 0; i < this.bloodHitCount+this.attackTime; i++)
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                            .get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
                addToBot(new MakeTempCardInDrawPileAction(new Burn(),1,true,true));
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        switch (moveCount){
            case 0:
                setMove((byte)0, Intent.ATTACK, (this.damage.get(0)).base, this.bloodHitCount+this.attackTime, true);
                break;
        }
        moveCount=(moveCount+1)%1;
    }

    public void updateMove(){
        setMove((byte)0, Intent.ATTACK, (this.damage.get(0)).base, this.bloodHitCount+this.attackTime, true);
        createIntent();
    }

}
