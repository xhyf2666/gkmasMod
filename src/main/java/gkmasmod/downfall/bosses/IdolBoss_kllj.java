package gkmasmod.downfall.bosses;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;

public class IdolBoss_kllj extends AbstractIdolBoss {
    private static final String theName = "kllj";
    public static final String ID = String.format("IdolBoss_%s",theName);
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public IdolBoss_kllj() {
        super(theName,ID);
        this.name = NAME;
        this.archetypes = new AbstractBossDeckArchetype[]{
                new BossConfig_kllj1(),
                new BossConfig_kllj2(),
                new BossConfig_kllj3()
        };
    }
}
