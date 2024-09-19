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
import gkmasmod.powers.GoodTune;
import gkmasmod.utils.PlayerHelper;

import java.util.Iterator;

public class GoodTuneDamageAction extends AbstractGameAction {
    private AbstractMonster m;

    private AbstractPlayer p;

    private int goodTuneAdd;

    private float rate;

    private float yarukiRate;

    private boolean damageAll;

    private AbstractCard card;

    public GoodTuneDamageAction(float rate, int goodTuneAdd, AbstractPlayer p, AbstractMonster m, AbstractCard card) {
        this(rate, goodTuneAdd, p, m,card, false);
    }

    public GoodTuneDamageAction(float rate, int goodTuneAdd, AbstractPlayer p, AbstractMonster m, AbstractCard card, boolean damageAll) {
        this(rate, goodTuneAdd, p, m,card,damageAll,1.0f);
    }

    public GoodTuneDamageAction(float rate, int goodTuneAdd, AbstractPlayer p, AbstractMonster m, AbstractCard card, boolean damageAll, float yarukiRate) {
        this.m = m;
        this.p = p;
        this.goodTuneAdd = goodTuneAdd;
        this.rate = rate;
        this.damageAll = damageAll;
        this.card = card;
        this.yarukiRate = yarukiRate;
    }

    public void update() {
        if(this.goodTuneAdd > 0)
            p.addPower(new GoodTune(p, goodTuneAdd));
        int damage = (int)(this.rate* PlayerHelper.getPowerAmount(p, GoodTune.POWER_ID));
        damage = calculateDamage(damage);
        if(damageAll){
            addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(damage, true), DamageInfo.DamageType.NORMAL, AttackEffect.SLASH_HORIZONTAL));
        }
        else{
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage , DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_HORIZONTAL));
        }

        this.isDone = true;
    }

    public int calculateDamage(int baseDamage){
        AbstractPlayer player = AbstractDungeon.player;
        if (m != null) {
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

            for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL, this.card)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL, this.card)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalReceive(tmp, DamageInfo.DamageType.NORMAL, this.card)) {
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
