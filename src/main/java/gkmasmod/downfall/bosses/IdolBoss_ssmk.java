package gkmasmod.downfall.bosses;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;

public class IdolBoss_ssmk extends AbstractIdolBoss {
    private static final String theName = "ssmk";
    public static final String ID = String.format("IdolBoss_%s",theName);
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public IdolBoss_ssmk() {
        super(theName,ID);
        this.name = NAME;
        this.archetypes = new AbstractBossDeckArchetype[]{
                new BossConfig_ssmk1(),
                new BossConfig_ssmk2(),
                new BossConfig_ssmk3()
        };
    }
}
