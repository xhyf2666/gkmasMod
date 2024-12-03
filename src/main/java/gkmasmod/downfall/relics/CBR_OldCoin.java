package gkmasmod.downfall.relics;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.OldCoin;
import gkmasmod.downfall.bosses.AbstractIdolBoss;

public class CBR_OldCoin extends AbstractCharbossRelic {
    public static final String ID = "OldCoin";

    public CBR_OldCoin() {
        super(new OldCoin());
    }

    @Override
    public void onEquip() {
        if(AbstractCharBoss.boss instanceof AbstractIdolBoss){
            AbstractIdolBoss idol = (AbstractIdolBoss) AbstractCharBoss.boss;
            idol.addGold(300);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_OldCoin();
    }
}
