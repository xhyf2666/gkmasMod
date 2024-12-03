package gkmasmod.downfall.bosses;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;

public class IdolBoss_amao extends AbstractIdolBoss {
    private static final String theName = "amao";
    public static final String ID = String.format("IdolBoss_%s",theName);
    public IdolBoss_amao() {
        super(theName,ID);
        this.archetypes = new AbstractBossDeckArchetype[]{
                new BossConfig_amao1(),
                new BossConfig_amao2(),
                new BossConfig_amao3()
        };
    }
}
