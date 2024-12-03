package gkmasmod.downfall.relics;

import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.MawBank;

public class CBR_MawBank extends AbstractCharbossRelic {
    public static final String ID = "MembershipCard";

    public CBR_MawBank() {
        super(new MawBank());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + '\f' + this.DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_MawBank();
    }
}
