package gkmasmod.monster.exordium;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
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
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.SplitPower;

public class SpikeSlimeTemari_S extends AbstractMonster {
    public static final String ID = "SpikeSlimeTemari_S";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SpikeSlime_S");

    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public static final String[] DIALOG = monsterStrings.DIALOG;

    public static final int HP_MIN = 10;

    public static final int HP_MAX = 14;

    public static final int A_2_HP_MIN = 11;

    public static final int A_2_HP_MAX = 15;

    public static final int TACKLE_DAMAGE = 5;

    public static final int A_2_TACKLE_DAMAGE = 6;

    private static final byte TACKLE = 1;

    public SpikeSlimeTemari_S(){
        this(0,0,0);
    }

    public SpikeSlimeTemari_S(float x, float y, int poisonAmount) {
        super(NAME, "SpikeSlime_S", 14, 0.0F, -24.0F, 130.0F, 100.0F, null, x, y);
        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(11, 15);
        } else {
            setHp(10, 14);
        }
        if (AbstractDungeon.ascensionLevel >= 2) {
            this.damage.add(new DamageInfo(this, 6));
        } else {
            this.damage.add(new DamageInfo(this, 5));
        }
        if (poisonAmount >= 1)
            this.powers.add(new PoisonPower(this, this, poisonAmount));
        this.img = new Texture("gkmasModResource/img/monsters/other/SpikeSlimeTemari_S.png");
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new AnimateFastAttackAction(this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction(AbstractDungeon.player, this.damage
                        .get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new RollMoveAction(this));
                break;
        }
    }

    protected void getMove(int num) {
        setMove((byte)1, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
    }
}
