package gkmasmod.actions;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.powers.GoodImpression;
import gkmasmod.utils.PlayerHelper;

import java.util.Iterator;

public class ItsPerfectAction extends AbstractGameAction{
    private int magic1;
    private int magic2;
    private int HP_LOST;
    private AbstractCard card;
    private AbstractCreature owner;

    public ItsPerfectAction(AbstractCreature owner,int magic1, int magic2, int HP_LOST) {
        this.duration = 0.0F;
        this.owner = owner;
        this.actionType = ActionType.WAIT;
        this.magic1 = magic1;
        this.magic2 = magic2;
        this.HP_LOST = HP_LOST;
        this.card = null;
    }

    public void update() {
        int count = this.owner.currentBlock;
        this.owner.currentBlock = 0;
        int goodImpression = (int) (1.0f* count * this.magic1/100);
        addToBot(new ApplyPowerAction(this.owner, this.owner, new GoodImpression(this.owner, goodImpression), goodImpression));
        int damage = (PlayerHelper.getPowerAmount(this.owner, GoodImpression.POWER_ID) +goodImpression)*this.magic2/100;
        if(this.owner instanceof AbstractCharBoss){
            this.target = AbstractDungeon.player;
            damage = calculateDamage2(damage, this.target);
        }
        else{
            this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            damage = calculateDamage(damage, this.target);

        }
        addToBot(new DamageAction(this.target, new DamageInfo(this.owner, damage, DamageInfo.DamageType.NORMAL), AttackEffect.POISON));
        addToBot(new LoseHPAction(this.owner, this.owner, this.HP_LOST));
        this.isDone = true;
    }

    public int calculateDamage(int baseDamage, AbstractCreature m){
        AbstractPlayer player = AbstractDungeon.player;
        if (m != null) {
            float tmp = (float)baseDamage;
            Iterator var9 = player.relics.iterator();

            if(this.card!=null){
                while(var9.hasNext()) {
                    AbstractRelic r = (AbstractRelic)var9.next();
                    tmp = r.atDamageModify(tmp, this.card);
                }
            }

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

    private int calculateDamage2(int baseDamage,AbstractCreature m) {
        AbstractCharBoss charBoss = (AbstractCharBoss) this.owner;
        if (m != null) {
            float tmp = (float)baseDamage;
            Iterator var9 = charBoss.relics.iterator();

            if(this.card!=null){
                while(var9.hasNext()) {
                    AbstractRelic r = (AbstractRelic)var9.next();
                    tmp = r.atDamageModify(tmp, this.card);
                }
            }

            AbstractPower p;
            for(var9 = charBoss.powers.iterator(); var9.hasNext(); tmp = p.atDamageGive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            tmp = charBoss.stance.atDamageGive(tmp, DamageInfo.DamageType.NORMAL);

            for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = charBoss.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL)) {
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
