package gkmasmod.downfall.charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PrayerWheel;

public class CBR_PrayerWheel extends AbstractCharbossRelic {
    public static final String ID = "PrayerWheel";
    private int numCards;

    public CBR_PrayerWheel() {
        super(new PrayerWheel());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_PrayerWheel();
    }
}
