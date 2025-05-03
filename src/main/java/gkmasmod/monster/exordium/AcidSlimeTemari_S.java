package gkmasmod.monster.exordium;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class AcidSlimeTemari_S extends AbstractMonster {
    public static final String ID = "AcidSlimeTemari_S";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("AcidSlime_S");

    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public static final String[] DIALOG = monsterStrings.DIALOG;

    public static final int HP_MIN = 8;

    public static final int HP_MAX = 12;

    public static final int A_2_HP_MIN = 9;

    public static final int A_2_HP_MAX = 13;

    public static final int TACKLE_DAMAGE = 3;

    public static final int WEAK_TURNS = 1;

    public static final int A_2_TACKLE_DAMAGE = 4;

    private static final byte TACKLE = 1;

    private static final byte DEBUFF = 2;


    public AcidSlimeTemari_S(){
        this(0,0,0);
    }

    public AcidSlimeTemari_S(float x, float y, int poisonAmount) {
        super(NAME, "AcidSlime_S", 12, 0.0F, -4.0F, 130.0F, 100.0F, null, x, y);
        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(9, 13);
        } else {
            setHp(8, 12);
        }
        if (AbstractDungeon.ascensionLevel >= 2) {
            this.damage.add(new DamageInfo(this, 4));
        } else {
            this.damage.add(new DamageInfo(this, 3));
        }
        if (poisonAmount >= 1)
            this.powers.add(new PoisonPower(this, this, poisonAmount));
        this.img = new Texture("gkmasModResource/img/monsters/other/AcidSlimeTemari_S.png");
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction(AbstractDungeon.player, this.damage
                        .get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                setMove((byte)2, AbstractMonster.Intent.DEBUFF);
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true), 1));
                setMove((byte)1, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
                break;
        }
    }

    protected void getMove(int num) {
        if (AbstractDungeon.ascensionLevel >= 17) {
            if (lastTwoMoves((byte)1)) {
                setMove((byte)1, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
            } else {
                setMove((byte)2, AbstractMonster.Intent.DEBUFF);
            }
        } else if (AbstractDungeon.aiRng.randomBoolean()) {
            setMove((byte)1, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
        } else {
            setMove((byte)2, AbstractMonster.Intent.DEBUFF);
        }
    }
}
