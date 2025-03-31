package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ModifyDamageRandomEnemyAction extends AbstractGameAction {
    private DamageInfo info;
    private AbstractCard card;
    private AbstractCreature owner;

    public ModifyDamageRandomEnemyAction(DamageInfo info, AttackEffect effect) {
        this.info = info;
        this.owner = info.owner;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.card = null;
    }

    public ModifyDamageRandomEnemyAction(DamageInfo info, AttackEffect effect, AbstractCard card) {
        this.info = info;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.card = card;
    }

    @Override
    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            addToTop(new ModifyDamageAction(this.target, this.info, this.attackEffect,this.card));
        }
        this.isDone = true;
    }
}
