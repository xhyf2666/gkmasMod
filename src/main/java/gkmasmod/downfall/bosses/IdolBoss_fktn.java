package gkmasmod.downfall.bosses;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;

public class IdolBoss_fktn extends AbstractIdolBoss {
    private static final String theName = "fktn";
    public static final String ID = String.format("IdolBoss_%s",theName);
    public IdolBoss_fktn() {
        super(theName,ID);
        this.archetypes = new AbstractBossDeckArchetype[]{
                new BossConfig_fktn1(),
                new BossConfig_fktn2(),
                new BossConfig_fktn3()
        };
    }
}
