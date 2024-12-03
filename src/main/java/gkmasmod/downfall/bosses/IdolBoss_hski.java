package gkmasmod.downfall.bosses;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;

public class IdolBoss_hski extends AbstractIdolBoss {
    private static final String theName = "hski";
    public static final String ID = String.format("IdolBoss_%s",theName);
    public IdolBoss_hski() {
        super(theName,ID);
        this.archetypes = new AbstractBossDeckArchetype[]{
                new BossConfig_hski1(),
                new BossConfig_hski2(),
                new BossConfig_hski3()
        };
    }
}
