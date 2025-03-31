package gkmasmod.downfall.charbosses.relics;

import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Strawberry;

public class CBR_Strawberry extends AbstractCharbossRelic {
    public static final String ID = "Strawberry";

    public CBR_Strawberry() {
        super(new Strawberry());
    }

    @Override
    public void onEquip() {
        this.owner.increaseMaxHp(7, true);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + '7' + LocalizedStrings.PERIOD;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Strawberry();
    }
}