package gkmasmod.downfall.charbosses.relics;

import gkmasmod.downfall.charbosses.orbs.EnemyDark;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.SymbioticVirus;

public class CBR_SymbioticVirus extends AbstractCharbossRelic {
    public static final String ID = "SymbioticVirus";

    public CBR_SymbioticVirus() {
        super(new SymbioticVirus());

    }


    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atPreBattle() {
        this.owner.channelOrb(new EnemyDark());
    }

    public AbstractRelic makeCopy() {
        return new CBR_SymbioticVirus();
    }
}
