package gkmasmod.downfall.bosses;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;

public class IdolBoss_jsna extends AbstractIdolBoss {
    private static final String theName = "jsna";
    public static final String ID = String.format("IdolBoss_%s",theName);

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);

    public static final String NAME = monsterStrings.NAME;

    public IdolBoss_jsna() {
        super(theName,ID);
        this.name = NAME;
        this.archetypes = new AbstractBossDeckArchetype[]{
                new BossConfig_jsna1(),
                new BossConfig_jsna2(),
                new BossConfig_jsna3()
        };
    }
}
