package gkmasmod.downfall.bosses;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;

public class IdolBoss_shro extends AbstractIdolBoss {
    private static final String theName = "shro";
    public static final String ID = String.format("IdolBoss_%s",theName);
    public IdolBoss_shro() {
        super(theName,ID);
        this.archetypes = new AbstractBossDeckArchetype[]{
                new BossConfig_shro1(),
                new BossConfig_shro2(),
                new BossConfig_shro3()
        };
    }
}
