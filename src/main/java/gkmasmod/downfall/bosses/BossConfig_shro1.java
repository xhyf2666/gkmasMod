package gkmasmod.downfall.bosses;

import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.*;
import gkmasmod.downfall.cards.logic.*;
import gkmasmod.downfall.relics.CBR_BeginnerGuideForEveryone;
import gkmasmod.downfall.relics.CBR_SidewalkResearchNotes;

import java.util.ArrayList;

public class BossConfig_shro1 extends AbstractBossDeckArchetype {

    public BossConfig_shro1() {
        super("hski_config3","hski", "focus");
        maxHPModifier += 0;
        actNum = 3;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BarricadePower(p)));
    }

    public void initialize() {
        addRelic(new CBR_HornCleat());
        addRelic(new CBR_MercuryHourglass());
        addRelic(new CBR_Lantern());
        addRelic(new CBR_SidewalkResearchNotes());
        addRelic(new CBR_BeginnerGuideForEveryone());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList,new ENBeforeThePerformance(),extraUpgrades);
                    addToList(cardsList, new ENEvenIfDreamNotRealize(),extraUpgrades);
                    addToList(cardsList, new ENUnstoppableThoughts(),extraUpgrades);
                    addToList(cardsList, new ENDreamToAchieve());
                    addToList(cardsList, new ENBasePerform());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENBaseAwareness(), extraUpgrades);
                    addToList(cardsList, new ENTopWisdom(),extraUpgrades);
                    addToList(cardsList, new ENThankYou());
                    addToList(cardsList, new ENBasePerform());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENSeriousHobby(), extraUpgrades);
                    addToList(cardsList, new ENLovesTheStruggle(),extraUpgrades);
                    addToList(cardsList, new ENSkipWater());
                    addToList(cardsList, new ENTriggerRelic());
                    addToList(cardsList, new ENTriggerRelic());
                    addToList(cardsList, new ENBasePerform());
                    addToList(cardsList, new ENBasePerform());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENBasePerform());
                    addToList(cardsList, new ENSkipWater());
                    addToList(cardsList, new ENBasePose());
                    addToList(cardsList, new ENChangeMood(),extraUpgrades);
                    addToList(cardsList, new ENBasePerform());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENBasePerform());
                    addToList(cardsList, new ENSkipWater());
                    addToList(cardsList, new ENBasePose());
                    addToList(cardsList, new ENThankYou(),extraUpgrades);
                    addToList(cardsList, new ENBasePerform());
                    turn=0;
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}