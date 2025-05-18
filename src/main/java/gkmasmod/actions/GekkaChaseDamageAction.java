package gkmasmod.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.powers.GekkaChase;

import java.util.Iterator;

public class GekkaChaseDamageAction extends AbstractGameAction {
    private int damage;
    private AbstractCreature owner;
    private boolean flag;

    public GekkaChaseDamageAction(AbstractCreature owner,int damage, boolean flag) {
        this.owner = owner;
        this.damage = damage;
        this.flag = flag;
    }

    @Override
    public void update() {
        damage = calculateDamage3(damage, AbstractDungeon.player);
        addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(owner, damage, DamageInfo.DamageType.NORMAL),AttackEffect.SLASH_DIAGONAL));
        this.isDone = true;
    }

    private int calculateDamage3(int baseDamage,AbstractCreature m) {
        if (m != null) {
            float tmp = (float)baseDamage;
            Iterator var9;

            AbstractPower p;
            for(var9 = this.owner.powers.iterator(); var9.hasNext(); tmp = p.atDamageGive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = this.owner.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalReceive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            if (tmp < 0.0F) {
                tmp = 0.0F;
            }

            if(flag){
                if(tmp>=AbstractDungeon.player.currentBlock+AbstractDungeon.player.currentHealth){
                    tmp = AbstractDungeon.player.currentBlock+AbstractDungeon.player.currentHealth-1;
                    for(AbstractMonster mo:AbstractDungeon.getMonsters().monsters){
                        if(!mo.isDeadOrEscaped()&&mo.hasPower(GekkaChase.POWER_ID)){
                            ((GekkaChase)mo.getPower(GekkaChase.POWER_ID)).flag = false;
                            addToBot(new TalkAction(mo, CardCrawlGame.languagePack.getMonsterStrings("gkmasMod:MonsterGekka").DIALOG[2],3.0F,2.0F));
                        }
                    }
                }
            }

            return MathUtils.floor(tmp);
        }
        return baseDamage;
    }
}
