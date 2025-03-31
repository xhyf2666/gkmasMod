package gkmasmod.downfall.bosses;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;

public class IdolBoss_shro extends AbstractIdolBoss {
    private static final String theName = "shro";
    public static final String ID = String.format("IdolBoss_%s",theName);
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public IdolBoss_shro() {
        super(theName,ID);
        this.name = NAME;
        this.archetypes = new AbstractBossDeckArchetype[]{
                new BossConfig_shro1(),
                new BossConfig_shro2(),
                new BossConfig_shro3()
        };
    }
}
