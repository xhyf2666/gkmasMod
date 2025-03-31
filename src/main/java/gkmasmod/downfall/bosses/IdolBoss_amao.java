package gkmasmod.downfall.bosses;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;

public class IdolBoss_amao extends AbstractIdolBoss {
    private static final String theName = "amao";
    public static final String ID = String.format("IdolBoss_%s",theName);

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);

    public static final String NAME = monsterStrings.NAME;

    public IdolBoss_amao() {
        super(theName,ID);
        this.name = NAME;
        this.archetypes = new AbstractBossDeckArchetype[]{
                new BossConfig_amao1(),
                new BossConfig_amao2(),
                new BossConfig_amao3()
        };
    }
}
