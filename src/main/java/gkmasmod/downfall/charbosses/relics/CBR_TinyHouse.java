package gkmasmod.downfall.charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.TinyHouse;

public class CBR_TinyHouse extends AbstractCharbossRelic {
    public static final String ID = "TinyHouse";

    public CBR_TinyHouse() {
        super(new TinyHouse());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + '2' + this.DESCRIPTIONS[1] + '\005' + this.DESCRIPTIONS[2];
    }

    @Override
    public void onEquip() {
        this.owner.increaseMaxHp(5, true);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_TinyHouse();
    }
}
