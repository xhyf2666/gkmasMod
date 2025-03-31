package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardAtBottomOfDeckAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import gkmasmod.cards.hmsz.WantToRun;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.growEffect.AttackTimeGrow;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.utils.GrowHelper;
import gkmasmod.utils.PlayerHelper;

public class WakeUpTimeAction extends AbstractGameAction {
    private AbstractCreature p;

    public WakeUpTimeAction(AbstractCreature p) {
        this.p = p;
    }

    public void update() {
        int energy=0;
        if(this.p instanceof AbstractPlayer)
            energy = EnergyPanel.totalCount;
        else if(this.p instanceof AbstractCharBoss)
            energy = AbstractCharBoss.boss.energyPanel.totalCount;
        int amount1 = energy/1;

        if(this.p instanceof AbstractPlayer){
            if (AbstractDungeon.player.hasRelic("Chemical X")) {
                amount1 += 1;
                AbstractDungeon.player.getRelic("Chemical X").flash();
            }
        }
        else if(this.p instanceof AbstractCharBoss){
            if (AbstractCharBoss.boss.hasRelic("Chemical X")) {
                amount1 += 1;
                AbstractCharBoss.boss.getRelic("Chemical X").flash();
            }
        }

        if(this.p instanceof AbstractPlayer)
            AbstractDungeon.player.energy.use(EnergyPanel.totalCount);
        else if(this.p instanceof AbstractCharBoss)
            AbstractCharBoss.boss.energy.use(AbstractCharBoss.boss.energyPanel.totalCount);

        AbstractCard c = new WantToRun();
        if(amount1>0){
            GrowHelper.grow(c, AttackTimeGrow.growID, amount1);
        }
        c.unhover();
        c.fadingOut = false;
        AbstractDungeon.player.drawPile.addToBottom(c);
        UnlockTracker.unlockCard(c.cardID);

        this.isDone = true;
    }

}
