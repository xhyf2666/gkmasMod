package gkmasmod.monster.beyond;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import gkmasmod.cards.free.Sleepy;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.powers.*;

public class MonsterRinami1 extends CustomMonster {
    public static final String ID = "MonsterRinami1";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:MonsterRinami1");

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

    public MonsterRinami1() {
        this(000.0F, 0.0F);
    }

    public MonsterRinami1(float x, float y) {
        super(NAME, ID, MAX_HEALTH, -8.0F, 0.0F, 200.0F, 480.0F, null, x, y);
        this.img = new Texture("gkmasModResource/img/monsters/Idol/MonsterRinami1.png");
        this.type = EnemyType.ELITE;
        if (AbstractDungeon.ascensionLevel >= 10) {
            setHp(110);
        } else {
            setHp(100);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 13));
            this.bloodHitCount = 1;
        } else {
            this.damage.add(new DamageInfo(this, 10));
            this.bloodHitCount = 1;
        }
    }

    public void usePreBattleAction() {
        if (AbstractDungeon.ascensionLevel >= 5) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RinamiRobotPower(this,1),1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RinamiStoryPower(this,3)));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RinamiRobotPower(this,1),1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RinamiStoryPower(this,2)));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RinamiSecretPower(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RinamiEncallPower(this,50),50));
    }

    public void takeTurn() {
        int i;
        switch (this.nextMove) {
            case 0:
                addToBot(new DamageAction(AbstractDungeon.player, this.damage
                        .get(0), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
//                addToBot(new MakeTempCardInDrawPileAction(new VoidCard(),1,true,true));
                break;
            case 1:
                for (AbstractMonster mo:AbstractDungeon.getCurrRoom().monsters.monsters){
                    if(!mo.isDeadOrEscaped()&& !AbstractMonsterPatch.friendlyField.friendly.get(mo)){
                        addToBot(new HealAction(mo,mo, (int) (mo.maxHealth*0.2f)));
                        addToBot(new ApplyPowerAction(mo,mo,new StrengthPower(mo,2),2));
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

}
