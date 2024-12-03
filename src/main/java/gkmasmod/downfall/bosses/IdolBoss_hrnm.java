package gkmasmod.downfall.bosses;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;

public class IdolBoss_hrnm extends AbstractIdolBoss {
    private static final String theName = "hrnm";
    public static final String ID = String.format("IdolBoss_%s",theName);
    public IdolBoss_hrnm() {
        super(theName,ID);
        this.masterMaxOrbs = this.maxOrbs = 3;
        this.archetypes = new AbstractBossDeckArchetype[]{
                new BossConfig_hrnm2(),
                new BossConfig_hrnm1(),
                new BossConfig_hrnm3()
        };
    }
}
