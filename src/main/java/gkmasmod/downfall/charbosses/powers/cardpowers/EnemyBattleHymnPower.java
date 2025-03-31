package gkmasmod.downfall.charbosses.powers.cardpowers;

import gkmasmod.downfall.charbosses.actions.common.EnemyMakeTempCardInHandAction;
import gkmasmod.downfall.charbosses.cards.purple.EnSmite;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnemyBattleHymnPower extends AbstractPower {
    public static final String POWER_ID = "BattleHymn";
    private static final PowerStrings powerStrings;

    public EnemyBattleHymnPower(AbstractCreature owner, int amt) {
        this.name = powerStrings.NAME;
        this.ID = "BattleHymn";
        this.owner = owner;
        this.amount = amt;
        this.updateDescription();
        this.loadRegion("hymn");
    }


    public void atStartOfTurnPostDraw() {

        this.addToBot(new EnemyMakeTempCardInHandAction(new EnSmite(), this.amount, false));

    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    public void updateDescription() {
        if (this.amount > 1) {
            this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
        } else {
            this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[2];
        }

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("BattleHymn");
    }
}
