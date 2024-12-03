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
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.relics.*;

import java.util.ArrayList;

public class BossConfig_hume2 extends AbstractBossDeckArchetype {

    public BossConfig_hume2() {
        super("hski_config2","hski", "goodImpression");
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
        addRelic(new CBR_OrnamentalFan());
        addRelic(new CBR_BalanceLogicAndSense());
        addRelic(new CBR_PhilosopherStone());
        addRelic(new CBR_RollingSourceOfEnergy());
        addRelic(new CBR_ChristmasLion());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENBeforeThePerformance(),extraUpgrades);
                    addToList(cardsList, new ENSteadyWill(),extraUpgrades);
                    addToList(cardsList, new ENTopWisdom(),extraUpgrades);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENUnstoppableThoughts(), extraUpgrades);
                    addToList(cardsList, new ENInnocence(),extraUpgrades);
                    addToList(cardsList, new ENEyePower());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENIamBigStar(),extraUpgrades);
                    addToList(cardsList, new ENWarmUp(),extraUpgrades);
                    addToList(cardsList, new ENSisterHelp());
                    addToList(cardsList, new ENShareSomethingWithYou(),true);
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new ENJustOneMore(),extraUpgrades);
                    addToList(cardsList, new ENStartSignal(),extraUpgrades);
                    addToList(cardsList, new ENCharmGaze(),true);
                    addToList(cardsList, new ENBaseExpression(),true);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENEncoreCall(), extraUpgrades);
                    addToList(cardsList, new ENPushingTooHardAgain(), extraUpgrades);
                    addToList(cardsList, new ENOverwhelmingNumbers(),extraUpgrades);
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