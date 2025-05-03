package gkmasmod.monster.exordium;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.unique.CannotLoseAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.SpikeSlime_M;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.SplitPower;

public class SpikeSlimeTemari_M extends AbstractMonster {
    public static final String ID = "SpikeSlimeTemari_M";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SpikeSlime_M");

    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public static final String[] DIALOG = monsterStrings.DIALOG;

    public static final int HP_MIN = 28;

    public static final int HP_MAX = 32;

    public static final int A_2_HP_MIN = 29;

    public static final int A_2_HP_MAX = 34;

    public static final int TACKLE_DAMAGE = 8;

    public static final int WOUND_COUNT = 1;

    public static final int A_2_TACKLE_DAMAGE = 10;

    public static final int FRAIL_TURNS = 1;

    private static final byte FLAME_TACKLE = 1;

    private static final byte FRAIL_LICK = 4;

    private static final String FRAIL_NAME = MOVES[0];

    public SpikeSlimeTemari_M() {
        this(0, 0, 0, 32);
        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(29, 34);
        } else {
            setHp(28, 32);
        }
    }

    public SpikeSlimeTemari_M(float x,float y) {
        this(x, y, 0, 32);
        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(29, 34);
        } else {
            setHp(28, 32);
        }
    }

    public SpikeSlimeTemari_M(float x, float y, int poisonAmount, int newHealth) {
        super(NAME, "SpikeSlime_M", newHealth, 0.0F, -25.0F, 170.0F, 130.0F, null, x, y, true);
        if (AbstractDungeon.ascensionLevel >= 2) {
            this.damage.add(new DamageInfo(this, 10));
        } else {
            this.damage.add(new DamageInfo(this, 8));
        }
        if(poisonAmount>0){
            if (AbstractDungeon.ascensionLevel >= 7) {
                setHp(29, 34);
            } else {
                setHp(28, 32);
            }
        }
        this.img = new Texture("gkmasModResource/img/monsters/other/SpikeSlimeTemari_M.png");
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 4:
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 1, true), 1));
                break;
            case 1:
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction(AbstractDungeon.player, this.damage
                        .get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new MakeTempCardInDiscardAction(new Slimed(), 1));
                break;
        }
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if (AbstractDungeon.ascensionLevel >= 17) {
            if (num < 30) {
                if (lastTwoMoves((byte)1)) {
                    setMove(FRAIL_NAME, (byte)4, AbstractMonster.Intent.DEBUFF);
                } else {
                    setMove((byte)1, AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base);
                }
            } else if (lastMove((byte)4)) {
                setMove((byte)1, AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base);
            } else {
                setMove(FRAIL_NAME, (byte)4, AbstractMonster.Intent.DEBUFF);
            }
        } else if (num < 30) {
            if (lastTwoMoves((byte)1)) {
                setMove(FRAIL_NAME, (byte)4, AbstractMonster.Intent.DEBUFF);
            } else {
                setMove((byte)1, AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base);
            }
        } else if (lastTwoMoves((byte)4)) {
            setMove((byte)1, AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base);
        } else {
            setMove(FRAIL_NAME, (byte)4, AbstractMonster.Intent.DEBUFF);
        }
    }

    public void die() {
        super.die();
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead() &&
                AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoomBoss) {
            onBossVictoryLogic();
        }
    }
}

