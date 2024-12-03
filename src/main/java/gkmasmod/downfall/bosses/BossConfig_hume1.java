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
import gkmasmod.downfall.relics.CBR_RollingSourceOfEnergy;
import gkmasmod.downfall.relics.CBR_ShibaInuPochette;

import java.util.ArrayList;

public class BossConfig_hume1 extends AbstractBossDeckArchetype {

    public BossConfig_hume1() {
        super("hski_config1","hski", "goodTune");
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
        addRelic(new CBR_Strawberry());
        addRelic(new CBR_Ginger());
        addRelic(new CBR_OddlySmoothStone());
        addRelic(new CBR_ShibaInuPochette());
        addRelic(new CBR_RollingSourceOfEnergy());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList,new ENBeforeThePerformance(),extraUpgrades);
                    addToList(cardsList,new ENUnstoppableThoughts(),extraUpgrades);
                    addToList(cardsList,new ENWakuWaku(), extraUpgrades);
                    addToList(cardsList, new ENAngelAndDemon(),extraUpgrades);

                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENFlowerBouquet(),extraUpgrades);
                    addToList(cardsList, new ENOverflowMemory(),extraUpgrades);
                    addToList(cardsList, new ENFlowering());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENImaginaryTraining(), extraUpgrades);
                    addToList(cardsList, new ENUntappedPotential(),true);
                    addToList(cardsList, new ENDefeatBigSister());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENOverflowMemory(), extraUpgrades);
                    addToList(cardsList, new ENBasePose(),extraUpgrades);
                    addToList(cardsList, new ENThankYou(),extraUpgrades);
                    turn = 0;
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}