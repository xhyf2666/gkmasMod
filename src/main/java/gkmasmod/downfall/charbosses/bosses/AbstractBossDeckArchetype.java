package gkmasmod.downfall.charbosses.bosses;


import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public abstract class AbstractBossDeckArchetype {

    public int maxHPModifier;
    public int actNum;

    private AbstractCharBoss currentBoss;


    public void addedPreBattle() {
        initializeBossPanel();
    }

    public abstract void initializeBonusRelic();

    public boolean looped = false;
    public int turn = 0;

    public ArrayList<AbstractCard> getThisTurnCards() {
        return new ArrayList<>();
    }

    public void addToList(ArrayList<AbstractCard> cardsList, AbstractBossCard card, boolean upgraded, int energyGeneratedIfPlayed) {
        card.energyGeneratedIfPlayed = energyGeneratedIfPlayed;
        addToList(cardsList, card, upgraded);
    }

    public void addToList(ArrayList<AbstractCard> c, AbstractCard q, boolean upgraded) {
        if (upgraded) q.upgrade();
        c.add(q);
    }

    public AbstractBossCard getCard(AbstractCard q, boolean upgraded) {
        if (upgraded) q.upgrade();
        return (AbstractBossCard) q;
    }

    public void initializeBossPanel() {
    }

    public void addToList(ArrayList<AbstractCard> c, AbstractCard q) {
        addToList(c, q, false);
    }

    private final String ID;

    public AbstractBossDeckArchetype(String id, String loggerClassName, String loggerArchetypeName) {

        this.ID = id;
        if (AbstractDungeon.actNum != 4)
            AbstractDungeon.lastCombatMetricKey = ID;


    }

    public void addRelic(AbstractCharbossRelic r) {
        r.instantObtain(AbstractCharBoss.boss);

    }

    public void initialize() {
    }


    public boolean defaultToggle = false;
}
