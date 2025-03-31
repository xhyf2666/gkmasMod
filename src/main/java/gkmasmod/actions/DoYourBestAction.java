package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.relics.AbsoluteNewSelf;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.utils.PlayerHelper;

public class DoYourBestAction extends AbstractGameAction {
    private AbstractCreature p;
    private AbstractCreature m;
    private int damage;
    private boolean flag;

    public DoYourBestAction(AbstractCreature p, AbstractCreature m, int damage,boolean flag) {
        this.p = p;
        this.m = m;
        this.damage = damage;
        this.flag = flag;
    }

    public void update() {
        int energy=0;
        if(this.p instanceof AbstractPlayer)
            energy = EnergyPanel.totalCount;
        else if(this.p instanceof AbstractCharBoss)
            energy = AbstractCharBoss.boss.energyPanel.totalCount;
        int amount1 = (int) (1.0F*energy);
        int amount2 = (int) (PlayerHelper.getPowerAmount(p,FullPowerValue.POWER_ID) *1.0F /3);

        if(this.p instanceof AbstractPlayer){
            if (AbstractDungeon.player.hasRelic("Chemical X")) {
                amount1 += 2;
                AbstractDungeon.player.getRelic("Chemical X").flash();
            }
        }
        else if(this.p instanceof AbstractCharBoss){
            if (AbstractCharBoss.boss.hasRelic("Chemical X")) {
                amount1 += 2;
                AbstractCharBoss.boss.getRelic("Chemical X").flash();
            }
        }

        int effect = amount1 + amount2;
        if (effect > 0) {
            for(int i = 0; i < effect; ++i) {
                this.addToBot(new DamageAction(this.m, new DamageInfo(this.p, this.damage, DamageInfo.DamageType.NORMAL), AttackEffect.BLUNT_LIGHT));
            }
            if(this.p instanceof AbstractPlayer)
                AbstractDungeon.player.energy.use(EnergyPanel.totalCount);
            else if(this.p instanceof AbstractCharBoss)
                AbstractCharBoss.boss.energy.use(AbstractCharBoss.boss.energyPanel.totalCount);
        }

        if (flag&&this.p.hasPower(FullPowerValue.POWER_ID)) {
            addToBot(new RemoveSpecificPowerAction(p, p, FullPowerValue.POWER_ID));
        }

        this.isDone = true;
    }

}
