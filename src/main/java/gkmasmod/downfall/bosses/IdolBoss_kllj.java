package gkmasmod.downfall.bosses;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;

public class IdolBoss_kllj extends AbstractIdolBoss {
    private static final String theName = "kllj";
    public static final String ID = String.format("IdolBoss_%s",theName);
    public IdolBoss_kllj() {
        super(theName,ID);
        this.archetypes = new AbstractBossDeckArchetype[]{
                new BossConfig_kllj1(),
                new BossConfig_kllj2(),
                new BossConfig_kllj3()
        };
    }
}
