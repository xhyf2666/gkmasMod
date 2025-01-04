package gkmasmod.actions;


import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.utils.PlayerHelper;
import gkmasmod.vfx.effect.RinhaAttackEffect;
import gkmasmod.vfx.effect.RinhaFallEffect;

import java.util.Random;

public class RinhaAttackAction extends AbstractGameAction {
    private AbstractCreature p;
    private int baseDamage = 5;
    public RinhaAttackAction(AbstractCreature p, int baseDamage) {
        this.p = p;
        this.baseDamage = baseDamage;
    }

    public void update() {

        this.p.drawY+=200.0F*Settings.yScale;

        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
            if(!m.isDeadOrEscaped()&&!AbstractMonsterPatch.friendlyField.friendly.get(m)){
                AbstractDungeon.actionManager.addToBottom(new ModifyDamageAction(m,new DamageInfo(this.p,baseDamage, DamageInfo.DamageType.NORMAL), AttackEffect.NONE));
                int type = 0;
                //生成0-2的随机数
                type = new Random().nextInt(2);
                AbstractDungeon.effectList.add(new RinhaAttackEffect(type,this.p.hb.cX,this.p.hb.cY,m.hb.cX, m.hb.cY));
            }
        }
        AbstractDungeon.effectList.add(new RinhaFallEffect(p,this.p.drawY-200.0F*Settings.yScale));
        this.isDone = true;


    }

}
