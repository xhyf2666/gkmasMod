package gkmasmod.actions;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.powers.GoodImpression;
import gkmasmod.utils.PlayerHelper;

import java.util.Iterator;

public class GoodImpressionDamageAction extends AbstractGameAction {
    private AbstractMonster m;

    private AbstractPlayer p;


    private int goodImpressionAdd;

    private float rate;

    private AbstractCard card;

    public GoodImpressionDamageAction(float rate,int goodImpressionAdd,AbstractPlayer p, AbstractMonster m) {
        this.m = m;
        this.p = p;
        this.goodImpressionAdd = goodImpressionAdd;
        this.rate = rate;
        this.card = null;
    }

    public GoodImpressionDamageAction(float rate,int goodImpressionAdd,AbstractPlayer p, AbstractMonster m, AbstractCard card) {
        this.m = m;
        this.p = p;
        this.goodImpressionAdd = goodImpressionAdd;
        this.rate = rate;
        this.card = card;
    }



    public void update() {
        int count = PlayerHelper.getPowerAmount(p, GoodImpression.POWER_ID);
        int damage = (int)(this.rate*(count+goodImpressionAdd));
        damage = calculateDamage(damage,m);
        addToBot(new ApplyPowerAction(p, p, new GoodImpression(p,this.goodImpressionAdd), this.goodImpressionAdd));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p,damage , DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

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
