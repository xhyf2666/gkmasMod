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
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.relics.CBR_SakiCompleteMealRecipe;
import gkmasmod.downfall.relics.CBR_TogetherInBattleTowel;

import java.util.ArrayList;

public class BossConfig_hski1 extends AbstractBossDeckArchetype {

    public BossConfig_hski1() {
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
        addRelic(new CBR_Girya(3));
        addRelic(new CBR_BagOfMarbles());
        addRelic(new CBR_Akabeko());
        addRelic(new CBR_SakiCompleteMealRecipe());
        addRelic(new CBR_TogetherInBattleTowel());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList,new ENSteadyWill(),extraUpgrades);
                    addToList(cardsList, new ENIdolDeclaration(),extraUpgrades);
                    addToList(cardsList, new ENNeverLose(),extraUpgrades);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENEyePower(), extraUpgrades);
                    addToList(cardsList, new ENInnocence(),extraUpgrades);
                    addToList(cardsList, new ENBaseBehave());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENSleepLate(),extraUpgrades);
                    addToList(cardsList, new ENStartSignal());
                    addToList(cardsList, new ENCharmPerformance());
                    addToList(cardsList, new ENAchievement());
                    addToList(cardsList, new ENNeverLose(),true);
                    addToList(cardsList, new ENSleepy());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENExistence(), extraUpgrades);
                    addToList(cardsList, new ENEncoreCall(),extraUpgrades);
                    addToList(cardsList, new ENGoWithTheFlow(),extraUpgrades);
                    addToList(cardsList, new ENTryError(),extraUpgrades);
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}