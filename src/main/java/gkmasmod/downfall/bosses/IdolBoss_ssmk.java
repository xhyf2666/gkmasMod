package gkmasmod.downfall.bosses;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;

public class IdolBoss_ssmk extends AbstractIdolBoss {
    private static final String theName = "ssmk";
    public static final String ID = String.format("IdolBoss_%s",theName);
    public IdolBoss_ssmk() {
        super(theName,ID);
        this.archetypes = new AbstractBossDeckArchetype[]{
                new BossConfig_ssmk1(),
                new BossConfig_ssmk2(),
                new BossConfig_ssmk3()
        };
    }
}
