package gkmasmod.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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

    public ItsPerfectAction(int magic1, int magic2, int HP_LOST) {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.magic1 = magic1;
        this.magic2 = magic2;
        this.HP_LOST = HP_LOST;
        this.card = null;
    }

    public void update() {
        int count = AbstractDungeon.player.currentBlock;
        AbstractDungeon.player.currentBlock = 0;
        int goodImpression = (int) (1.0f* count * this.magic1/100);
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new GoodImpression(AbstractDungeon.player, goodImpression), goodImpression));
        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        int damage = (PlayerHelper.getPowerAmount(AbstractDungeon.player, GoodImpression.POWER_ID) +goodImpression)*this.magic2/100;
        damage = calculateDamage(damage, (AbstractMonster)this.target);
        addToBot(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL), AttackEffect.POISON));
        addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, this.HP_LOST));
        this.isDone = true;
    }

    public int calculateDamage(int baseDamage, AbstractMonster m){
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
}
