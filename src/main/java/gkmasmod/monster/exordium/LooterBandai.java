package gkmasmod.monster.exordium;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
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
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.SplitPower;
import com.megacrit.cardcrawl.powers.ThieveryPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;


public class LooterBandai extends AbstractMonster {
    public static final String ID = "LooterBandai";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Looter");

    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public static final String[] DIALOG = monsterStrings.DIALOG;

    private static final int HP_MIN = 44;

    private static final int HP_MAX = 48;

    private static final int A_2_HP_MIN = 46;

    private static final int A_2_HP_MAX = 50;

    private int swipeDmg;

    private int lungeDmg;

    private int escapeDef = 6;

    private int goldAmt;

    private static final byte MUG = 1;

    private static final byte SMOKE_BOMB = 2;

    private static final byte ESCAPE = 3;

    private static final byte LUNGE = 4;

    private static final String SLASH_MSG1 = DIALOG[0];

    private static final String DEATH_MSG1 = DIALOG[1];

    private static final String SMOKE_BOMB_MSG = DIALOG[2];

    private static final String RUN_MSG = DIALOG[3];

    private int slashCount = 0;

    private int stolenGold = 0;

    public LooterBandai(float x, float y) {
        super(NAME, "LooterBandai", 48, 0.0F, 0.0F, 200.0F, 220.0F, null, x, y);
        this.dialogX = -30.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 17) {
            this.goldAmt = 20;
        } else {
            this.goldAmt = 15;
        }
        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(46, 50);
        } else {
            setHp(44, 48);
        }
        if (AbstractDungeon.ascensionLevel >= 2) {
            this.swipeDmg = 11;
            this.lungeDmg = 14;
        } else {
            this.swipeDmg = 10;
            this.lungeDmg = 12;
        }
        this.damage.add(new DamageInfo(this, this.swipeDmg));
        this.damage.add(new DamageInfo(this, this.lungeDmg));
        loadAnimation("gkmasModResource/img/monsters/LooterBandai/skeleton.atlas", "gkmasModResource/img/monsters/LooterBandai/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction(this, this, new ThieveryPower(this, this.goldAmt)));
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                if (this.slashCount == 0 && AbstractDungeon.aiRng.randomBoolean(0.6F))
                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TalkAction(this, SLASH_MSG1, 0.3F, 2.0F));
                playSfx();
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                    public void update() {
                        LooterBandai.this.stolenGold = LooterBandai.this.stolenGold + Math.min(LooterBandai.this.goldAmt, AbstractDungeon.player.gold);
                        this.isDone = true;
                    }
                });
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                        .get(0), this.goldAmt));
                this.slashCount++;
                if (this.slashCount == 2) {
                    if (AbstractDungeon.aiRng.randomBoolean(0.5F)) {
                        setMove((byte)2, AbstractMonster.Intent.DEFEND);
                        break;
                    }
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, MOVES[0], (byte)4, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage
                            .get(1)).base));
                    break;
                }
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, MOVES[1], (byte)1, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage
                        .get(0)).base));
                break;
            case 4:
                playSfx();
                this.slashCount++;
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                    public void update() {
                        LooterBandai.this.stolenGold = LooterBandai.this.stolenGold + Math.min(LooterBandai.this.goldAmt, AbstractDungeon.player.gold);
                        this.isDone = true;
                    }
                });
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction(AbstractDungeon.player, this.damage
                        .get(1), this.goldAmt));
                setMove((byte)2, AbstractMonster.Intent.DEFEND);
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TalkAction(this, SMOKE_BOMB_MSG, 0.75F, 2.5F));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new GainBlockAction(this, this, this.escapeDef));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new SetMoveAction(this, (byte)3, AbstractMonster.Intent.ESCAPE));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TalkAction(this, RUN_MSG, 0.3F, 2.5F));
                (AbstractDungeon.getCurrRoom()).mugged = true;
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new VFXAction(new SmokeBombEffect(this.hb.cX, this.hb.cY)));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new EscapeAction(this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new SetMoveAction(this, (byte)3, AbstractMonster.Intent.ESCAPE));
                break;
        }
    }

    private void playSfx() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new SFXAction("VO_LOOTER_1A"));
        } else if (roll == 1) {
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new SFXAction("VO_LOOTER_1B"));
        } else {
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new SFXAction("VO_LOOTER_1C"));
        }
    }

    private void playDeathSfx() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_LOOTER_2A");
        } else if (roll == 1) {
            CardCrawlGame.sound.play("VO_LOOTER_2B");
        } else {
            CardCrawlGame.sound.play("VO_LOOTER_2C");
        }
    }

    public void die() {
        playDeathSfx();
        this.state.setTimeScale(0.1F);
        useShakeAnimation(5.0F);
        if (MathUtils.randomBoolean(0.3F)) {
            AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX, this.hb.cY + this.dialogY, 2.0F, DEATH_MSG1, false));
            if (!Settings.FAST_MODE)
                this.deathTimer += 1.5F;
        }
        if (this.stolenGold > 0)
            AbstractDungeon.getCurrRoom().addStolenGoldToRewards(this.stolenGold);
        super.die();
    }

    protected void getMove(int num) {
        setMove((byte)1, AbstractMonster.Intent.ATTACK, (this.damage.get(0)).base);
    }
}
