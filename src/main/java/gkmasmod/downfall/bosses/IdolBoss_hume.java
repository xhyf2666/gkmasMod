package gkmasmod.downfall.bosses;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;

public class IdolBoss_hume extends AbstractIdolBoss {
    private static final String theName = "hume";
    public static final String ID = String.format("IdolBoss_%s",theName);
    public IdolBoss_hume() {
        super(theName,ID);
        this.archetypes = new AbstractBossDeckArchetype[]{
                new BossConfig_hume1(),
                new BossConfig_hume2(),
                new BossConfig_hume3()
        };
    }
}
