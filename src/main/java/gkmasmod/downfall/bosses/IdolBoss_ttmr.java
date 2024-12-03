package gkmasmod.downfall.bosses;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;

public class IdolBoss_ttmr extends AbstractIdolBoss {
    private static final String theName = "ttmr";
    public static final String ID = String.format("IdolBoss_%s",theName);
    public IdolBoss_ttmr() {
        super(theName,ID);
        this.archetypes = new AbstractBossDeckArchetype[]{
                new BossConfig_ttmr1(),
                new BossConfig_ttmr2(),
                new BossConfig_ttmr3()
        };
    }
}
