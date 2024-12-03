package gkmasmod.downfall.bosses;

import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.ENBasePerform;
import gkmasmod.downfall.cards.free.ENDanceWithYou;
import gkmasmod.downfall.cards.logic.*;
import gkmasmod.downfall.relics.CBR_FrogFan;

import java.util.ArrayList;

public class BossConfig_ssmk2 extends AbstractBossDeckArchetype {

    public BossConfig_ssmk2() {
        super("hski_config3","hski", "focus");
        maxHPModifier += 200;
        actNum = 3;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BarricadePower(p)));
    }

    public void initialize() {
        addRelic(new CBR_Ectoplasm());
        addRelic(new CBR_OddlySmoothStone());
        addRelic(new CBR_Turnip());
        addRelic(new CBR_FossilizedHelix());
        addRelic(new CBR_FrogFan());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList,new ENBeforeThePerformance(),extraUpgrades);
                    addToList(cardsList, new ENUnstoppableThoughts(),extraUpgrades);
                    addToList(cardsList, new ENIamBigStar(),extraUpgrades);
                    addToList(cardsList, new ENAfternoonBreeze(),extraUpgrades);
                    addToList(cardsList, new ENBasePerform());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENBaseAwareness(), extraUpgrades);
                    addToList(cardsList, new ENFlowering(),extraUpgrades);
                    addToList(cardsList, new ENDanceWithYou());
                    addToList(cardsList, new ENBasePerform(),extraUpgrades);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENOverflowMemory(),true);
                    addToList(cardsList, new ENEnergyIsFull(), extraUpgrades);
                    addToList(cardsList, new ENHandwrittenLetter(),extraUpgrades);
                    addToList(cardsList, new ENThankYou());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENOverflowMemory(),true);
                    addToList(cardsList, new ENBasePerform());
                    addToList(cardsList, new ENBasePerform());
                    addToList(cardsList, new ENThankYou(),extraUpgrades);
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}