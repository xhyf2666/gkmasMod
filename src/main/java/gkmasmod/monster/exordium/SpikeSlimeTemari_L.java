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

public class SpikeSlimeTemari_L extends AbstractMonster {

    public static final String ID = "SpikeSlimeTemari_L";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SpikeSlime_L");

    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public static final int HP_MIN = 64;

    public static final int HP_MAX = 70;

    public static final int A_2_HP_MIN = 67;

    public static final int A_2_HP_MAX = 73;

    public static final int TACKLE_DAMAGE = 16;

    public static final int A_2_TACKLE_DAMAGE = 18;

    public static final int FRAIL_TURNS = 2;

    public static final int WOUND_COUNT = 2;

    private static final byte FLAME_TACKLE = 1;

    private static final byte SPLIT = 3;

    private static final byte FRAIL_LICK = 4;

    private static final String FRAIL_NAME = MOVES[0];

    private static final String SPLIT_NAME = MOVES[1];

    private float saveX;

    private float saveY;

    private boolean splitTriggered;

    public SpikeSlimeTemari_L() {
        this(0, 0, 0, 70);
        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(67, 73);
        } else {
            setHp(64, 70);
        }
    }

    public SpikeSlimeTemari_L(float x, float y, int poisonAmount, int newHealth) {
        super(NAME, ID, newHealth, 0.0F, -30.0F, 300.0F, 180.0F, null, x, y, true);
        this.saveX = x;
        this.saveY = y;
        this.splitTriggered = false;
        if (AbstractDungeon.ascensionLevel >= 2) {
            this.damage.add(new DamageInfo(this, 18));
        } else {
            this.damage.add(new DamageInfo(this, 16));
        }
        this.powers.add(new SplitPower(this));
        if (poisonAmount >= 1)
            this.powers.add(new PoisonPower(this, this, poisonAmount));
        this.img = new Texture("gkmasModResource/img/monsters/other/SpikeSlimeTemari_L.png");
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 4:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                if (AbstractDungeon.ascensionLevel >= 17) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 3, true), 3));
                    break;
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 2));
                break;
            case 1:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                        .get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Slimed(), 2));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new CannotLoseAction());
                AbstractDungeon.actionManager.addToBottom(new AnimateShakeAction(this, 1.0F, 0.1F));
                AbstractDungeon.actionManager.addToBottom(new HideHealthBarAction(this));
                AbstractDungeon.actionManager.addToBottom(new SuicideAction(this, false));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(1.0F));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("SLIME_SPLIT"));
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new SpikeSlimeTemari_M(this.saveX - 134.0F, this.saveY +

                        MathUtils.random(-4.0F, 4.0F), 0, this.currentHealth), false));
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new SpikeSlimeTemari_M(this.saveX + 134.0F, this.saveY +

                        MathUtils.random(-4.0F, 4.0F), 0, this.currentHealth), false));
                AbstractDungeon.actionManager.addToBottom(new CanLoseAction());
                setMove(SPLIT_NAME, (byte)3, AbstractMonster.Intent.UNKNOWN);
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (!this.isDying && this.currentHealth <= this.maxHealth / 2.0F && this.nextMove != 3 && !this.splitTriggered) {
            setMove(SPLIT_NAME, (byte)3, AbstractMonster.Intent.UNKNOWN);
            createIntent();
            AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, TextAboveCreatureAction.TextType.INTERRUPTED));
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, SPLIT_NAME, (byte)3, AbstractMonster.Intent.UNKNOWN));
            this.splitTriggered = true;
        }
    }

    protected void getMove(int num) {
        if (AbstractDungeon.ascensionLevel >= 17) {
            if (num < 30) {
                if (lastTwoMoves((byte)1)) {
                    setMove(FRAIL_NAME, (byte)4, AbstractMonster.Intent.DEBUFF);
                } else {
                    setMove((byte)1, AbstractMonster.Intent.ATTACK_DEBUFF, (this.damage.get(0)).base);
                }
            } else if (lastMove((byte)4)) {
                setMove((byte)1, AbstractMonster.Intent.ATTACK_DEBUFF, (this.damage.get(0)).base);
            } else {
                setMove(FRAIL_NAME, (byte)4, AbstractMonster.Intent.DEBUFF);
            }
        } else if (num < 30) {
            if (lastTwoMoves((byte)1)) {
                setMove(FRAIL_NAME, (byte)4, AbstractMonster.Intent.DEBUFF);
            } else {
                setMove((byte)1, AbstractMonster.Intent.ATTACK_DEBUFF, (this.damage.get(0)).base);
            }
        } else if (lastTwoMoves((byte)4)) {
            setMove((byte)1, AbstractMonster.Intent.ATTACK_DEBUFF, (this.damage.get(0)).base);
        } else {
            setMove(FRAIL_NAME, (byte)4, AbstractMonster.Intent.DEBUFF);
        }
    }

    public void die() {
        super.die();
        for (AbstractGameAction a : AbstractDungeon.actionManager.actions) {
            if (a instanceof SpawnMonsterAction)
                return;
        }
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead() &&
                AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoomBoss) {
            onBossVictoryLogic();
        }
    }
}
