package gkmasmod.downfall.bosses;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;

public class IdolBoss_kcna extends AbstractIdolBoss {
    private static final String theName = "kcna";
    public static final String ID = String.format("IdolBoss_%s",theName);
    public IdolBoss_kcna() {
        super(theName,ID);
        this.archetypes = new AbstractBossDeckArchetype[]{
                new BossConfig_kcna1(),
                new BossConfig_kcna2(),
                new BossConfig_kcna3()
        };
    }
}
