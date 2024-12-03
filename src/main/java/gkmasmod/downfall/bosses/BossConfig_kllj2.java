package gkmasmod.downfall.bosses;

import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.cards.curses.EnNormality;
import gkmasmod.downfall.charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.*;
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.relics.CBR_SparklingInTheBottle;

import java.util.ArrayList;

public class BossConfig_kllj2 extends AbstractBossDeckArchetype {

    public BossConfig_kllj2() {
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
        addRelic(new CBR_Boot());
        addRelic(new CBR_VelvetChoker());
        addRelic(new CBR_IceCream());
        addRelic(new CBR_PenNib());
        addRelic(new CBR_SparklingInTheBottle());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList,new ENSteadyWill(),extraUpgrades);
                    addToList(cardsList, new ENInnocence(),extraUpgrades);
                    addToList(cardsList, new ENGrowthProcess(),extraUpgrades);
                    addToList(cardsList, new ENBaseAppeal());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENTopEntertainment(), extraUpgrades);
                    addToList(cardsList, new ENEndlessApplause());
                    addToList(cardsList, new ENStartSignal());
                    addToList(cardsList, new EnNormality());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENJustOneMore(),extraUpgrades);
                    addToList(cardsList, new ENStartSignal(),extraUpgrades);
                    addToList(cardsList, new ENCharmPerformance(),true);
                    addToList(cardsList, new ENBaseAppeal());
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new ENFirstRamune(),extraUpgrades);
                    addToList(cardsList, new ENAlternatives());
                    addToList(cardsList, new ENPopPhrase());
                    addToList(cardsList, new EnNormality());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENEncoreCall(),extraUpgrades);
                    addToList(cardsList, new ENWarmCare());
                    addToList(cardsList, new ENPopPhrase());
                    addToList(cardsList, new ENTryError());
                    addToList(cardsList, new ENBaseAppeal());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENBasePose(), extraUpgrades);
                    addToList(cardsList, new ENHighFive(),extraUpgrades);
                    addToList(cardsList, new ENBaseAppeal());
                    addToList(cardsList, new ENPopPhrase());
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