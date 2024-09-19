package gkmasmod.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Iterator;

public class ModfifyDamageAllEnemyAction extends AbstractGameAction {
    private int damage;
    private AbstractCard card;

    public ModfifyDamageAllEnemyAction(int damage, AttackEffect effect, AbstractCard card) {
        this.damage = damage;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.card = card;
    }

    @Override
    public void update() {
        damage = calculateDamage(damage);
        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL, this.attackEffect));

        this.isDone = true;
    }

    public int calculateDamage(int baseDamage){
        AbstractPlayer player = AbstractDungeon.player;
        float tmp = (float)baseDamage;
        Iterator var9 = player.relics.iterator();

        while(var9.hasNext()) {
            AbstractRelic r = (AbstractRelic)var9.next();
            tmp = r.atDamageModify(tmp, this.card);
        }
        AbstractPower p;
        for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageGive(tmp, DamageInfo.DamageType.NORMAL, this.card)) {
            p = (AbstractPower)var9.next();
        }

        tmp = player.stance.atDamageGive(tmp, DamageInfo.DamageType.NORMAL, this.card);

        for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL, this.card)) {
            p = (AbstractPower)var9.next();
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        return MathUtils.floor(tmp);
    }
}
