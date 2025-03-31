package gkmasmod.downfall.charbosses.relics;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Duality;

public class CBR_Duality extends AbstractCharbossRelic {
    public static final String ID = "Duality";

    public CBR_Duality() {
        super(new Duality());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.flash();
            AbstractCharBoss p = this.owner;
            this.addToBot(new RelicAboveCreatureAction(p, this));
            this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, 1), 1));
            this.addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, 1), 1));
        }


    }


    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Duality();
    }
}