package gkmasmod.actions.common;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import gkmasmod.patches.AbstractMonsterPatch;

public class AllGreedAction extends AbstractGameAction {
    private int increaseGold;

    public int[] damage;

    private int baseDamage;

    private boolean firstFrame = true, utilizeBaseDamage = false;

    private boolean flag = false;

    public AllGreedAction(int baseDamage, int goldAmount) {
        this.baseDamage = baseDamage;
        this.increaseGold = goldAmount;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = 0.1F;
    }

    public void update() {
        if((AbstractDungeon.getCurrRoom()).monsters.monsters.size()<1){
            this.isDone = true;
            return;
        }
        AbstractMonster m = (AbstractDungeon.getCurrRoom()).monsters.monsters.get(0);
        if (this.firstFrame) {
            boolean playedMusic = false;
            int temp = (AbstractDungeon.getCurrRoom()).monsters.monsters.size();
            this.damage = DamageInfo.createDamageMatrix(this.baseDamage);
            for (int i = 0; i < temp; i++) {
                m = (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i);
                if (!(m).isDying && (m).currentHealth > 0 && !(m).isEscaping && !AbstractMonsterPatch.friendlyField.friendly.get(m))
                    if (playedMusic) {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, this.attackEffect, true));
                    } else {
                        playedMusic = true;
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, this.attackEffect));
                    }
            }
            this.firstFrame = false;
        }
        tickDuration();
        if (this.isDone) {
            for (AbstractPower p : AbstractDungeon.player.powers)
                p.onDamageAllEnemies(this.damage);
            int temp = (AbstractDungeon.getCurrRoom()).monsters.monsters.size();
            for (int i = 0; i < temp; i++) {
                m = (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i);
                if (!m.isDeadOrEscaped() && !AbstractMonsterPatch.friendlyField.friendly.get(m)) {
                    if (this.attackEffect == AbstractGameAction.AttackEffect.POISON) {
                        m.tint.color.set(Color.CHARTREUSE);
                        m.tint.changeColor(Color.WHITE.cpy());
                    } else if (this.attackEffect == AbstractGameAction.AttackEffect.FIRE) {
                        m.tint.color.set(Color.RED);
                        m.tint.changeColor(Color.WHITE.cpy());
                    }
                    m.damage(new DamageInfo(AbstractDungeon.player, this.damage[i], DamageInfo.DamageType.NORMAL));
                    if ((m.isDying || m.currentHealth <= 0) && !m.halfDead && !m.hasPower("Minion")) {
                        flag = true;
                    }
                }
            }
            if(flag){
                AbstractDungeon.player.gainGold(this.increaseGold);
                for (int i = 0; i < this.increaseGold; i++)
                    AbstractDungeon.effectList.add(new GainPennyEffect(AbstractDungeon.player, m.hb.cX, m.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, true));
            }
            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
            if (!Settings.FAST_MODE)
                addToTop(new WaitAction(0.1F));
        }
    }
}
