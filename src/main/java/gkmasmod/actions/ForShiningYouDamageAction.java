package gkmasmod.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

public class ForShiningYouDamageAction extends AbstractGameAction {
    private DamageInfo info;

    public ForShiningYouDamageAction(DamageInfo info, AttackEffect effect, AbstractCard card) {
        this.info = info;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
    }

    @Override
    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            int damage = this.info.base;
            damage = calculateDamage(damage, (AbstractMonster)this.target);
            addToBot(new DamageAction(this.target, new DamageInfo(this.info.owner, damage, this.info.type), this.attackEffect));
        }
        this.isDone = true;
    }

    public int calculateDamage(int baseDamage, AbstractMonster m){
        AbstractPlayer player = AbstractDungeon.player;
        if (m != null) {
            float tmp = (float)baseDamage;
            Iterator var9 = player.relics.iterator();

            AbstractPower p;
            for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageGive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            tmp = player.stance.atDamageGive(tmp, DamageInfo.DamageType.NORMAL);
            for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }
            for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }
            for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalReceive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            if (tmp < 0.0F) {
                tmp = 0.0F;
            }

            return MathUtils.floor(tmp);
        }
        return baseDamage;
    }
}
