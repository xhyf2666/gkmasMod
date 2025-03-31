package gkmasmod.downfall.charbosses.actions.util;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.CBR_Calipers;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BlurPower;

public class CharBossMonsterGroup extends MonsterGroup {


    public CharBossMonsterGroup(AbstractMonster[] input) {
        super(input);
    }

    @Override
    public void applyPreTurnLogic() {
        for (AbstractMonster m : this.monsters) {
            if (m instanceof AbstractCharBoss) {
                AbstractCharBoss cB = (AbstractCharBoss) m;
                if (!m.isDying && !m.isEscaping) {
                    for (AbstractPower p : m.powers) {
                    }
                    if (!m.hasPower("Barricade") && !m.hasPower(BlurPower.POWER_ID)) {
                        if (cB.hasRelic(CBR_Calipers.ID)) {
                            m.loseBlock(15);
                        } else {
                            m.loseBlock();
                        }
                    }
                    m.applyStartOfTurnPowers();

                }
            } else {
                m.applyStartOfTurnPowers();
            }
        }

    }
}
