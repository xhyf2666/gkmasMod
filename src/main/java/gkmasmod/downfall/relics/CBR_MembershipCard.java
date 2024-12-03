package gkmasmod.downfall.relics;

import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.MembershipCard;

public class CBR_MembershipCard extends AbstractCharbossRelic {
    public static final String ID = "MembershipCard";

    public CBR_MembershipCard() {
        super(new MembershipCard());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_MembershipCard();
    }
}
