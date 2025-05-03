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
import com.megacrit.cardcrawl.cards.green.GrandFinale;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.cards.free.Sleepy;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.powers.*;

public class MonsterChina1 extends CustomMonster {
    public static final String ID = "MonsterChina1";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:MonsterChina1");

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

    public MonsterChina1() {
        this(000.0F, 0.0F);
    }

    public MonsterChina1(float x, float y) {
        super(NAME, ID, MAX_HEALTH, -8.0F, 0.0F, 200.0F, 480.0F, null, x, y);
        this.img = new Texture("gkmasModResource/img/monsters/Idol/MonsterChina1.png");
        this.type = EnemyType.ELITE;
        if (AbstractDungeon.ascensionLevel >= 10) {
            setHp(80);
        } else {
            setHp(70);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 8));
            this.bloodHitCount = 1;
        } else {
            this.damage.add(new DamageInfo(this, 6));
            this.bloodHitCount = 1;
        }
    }

    public void usePreBattleAction() {
        if (AbstractDungeon.ascensionLevel >= 5) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ChinaGreatPower(this,1),1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ChinaSongPower(this,5)));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ChinaGreatPower(this,1),1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ChinaSongPower(this,3)));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BarricadePower(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ChinaEncallPower(this)));
        AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX - 50, this.hb.cY + this.dialogY + 50, 2.0F, String.format(DIALOG[0],AbstractDungeon.player.title), false));
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        String song = "gkmasModResource/audio/song/chocolate_special.ogg";
        if (Gdx.files.internal(song).exists())
            CardCrawlGame.music.playTempBgmInstantly(song, true);
    }

    public void takeTurn() {
        int i;
        switch (this.nextMove) {
            case 0:
                addToBot(new DamageAction(AbstractDungeon.player, this.damage
                        .get(0), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                break;
            case 1:
                for (AbstractMonster mo:AbstractDungeon.getCurrRoom().monsters.monsters){
                    if(!mo.isDeadOrEscaped()&& !AbstractMonsterPatch.friendlyField.friendly.get(mo)){
                        addToBot(new ApplyPowerAction(mo,mo,new StrengthPower(mo,2),2));
                        addToBot(new ApplyPowerAction(mo,mo,new DexterityPower(mo,1),1));
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
