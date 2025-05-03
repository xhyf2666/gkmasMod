package gkmasmod.downfall.bosses;

import gkmasmod.downfall.cards.anomaly.ENBaseMental;
import gkmasmod.downfall.cards.anomaly.ENClimax;
import gkmasmod.downfall.cards.anomaly.ENDetermination;
import gkmasmod.downfall.charbosses.actions.unique.EnemyChangeStanceAction;
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
import gkmasmod.downfall.charbosses.stances.ENConcentrationStance;
import gkmasmod.downfall.charbosses.stances.ENPreservationStance;
import gkmasmod.downfall.relics.CBR_BeginnerGuideForEveryone;
import gkmasmod.downfall.relics.CBR_SelectedPassion;
import gkmasmod.downfall.relics.CBR_SidewalkResearchNotes;
import gkmasmod.downfall.relics.CBR_TheMarkOfPractice;
import gkmasmod.powers.EurekaPlusPower;
import gkmasmod.powers.EurekaPower;

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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EurekaPower(p)));
        if(AbstractDungeon.ascensionLevel >= 15)
            AbstractDungeon.actionManager.addToBottom(new EnemyChangeStanceAction(ENConcentrationStance.STANCE_ID2));
    }

    public void initialize() {
        addRelic(new CBR_HornCleat());
        addRelic(new CBR_Lantern());
        addRelic(new CBR_SelectedPassion());
        addRelic(new CBR_SidewalkResearchNotes());
        addRelic(new CBR_TheMarkOfPractice());
        addRelic(new CBR_BeginnerGuideForEveryone());
        if(AbstractDungeon.ascensionLevel >= 10)
            addRelic(new CBR_Sozu());

    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENBeforeThePerformance(),extraUpgrades);
                    addToList(cardsList, new ENEvenIfDreamNotRealize(),true);
                    addToList(cardsList, new ENUnstoppableThoughts(),extraUpgrades);
                    addToList(cardsList, new ENYeah(),true);
                    addToList(cardsList, new ENBaseAwareness(), true);
//                    addToList(cardsList, new ENDreamToAchieve());
                    addToList(cardsList, new ENBaseMental(),true);
                    addToList(cardsList, new ENBaseMental(),true);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENBaseAwareness(), true);
                    addToList(cardsList, new ENTopWisdom(),extraUpgrades);
                    addToList(cardsList, new ENIdolDeclaration());
                    addToList(cardsList, new ENDetermination(),true);
                    addToList(cardsList, new ENYeah(),true);
                    addToList(cardsList, new ENThankYou());
                    addToList(cardsList, new ENBaseMental(),true);
                    addToList(cardsList, new ENBaseMental(),true);
                    turn++;
                    break;
                case 2:
//                    addToList(cardsList, new ENSeriousHobby(), extraUpgrades);
                    addToList(cardsList, new ENLovesTheStruggle(),extraUpgrades);
                    addToList(cardsList, new ENSkipWater());
                    addToList(cardsList, new ENTriggerRelic());
                    addToList(cardsList, new ENTriggerRelic());
                    addToList(cardsList, new ENYeah(),true);
                    addToList(cardsList, new ENBaseMental(),true);
                    addToList(cardsList, new ENBaseMental(),true);
                    addToList(cardsList, new ENBasePerform(),true);
                    addToList(cardsList, new ENBasePerform(),true);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                case 1:
                    addToList(cardsList, new ENBaseMental());
                    addToList(cardsList, new ENSkipWater());
                    addToList(cardsList, new ENYeah(),true);
                    addToList(cardsList, new ENChangeMood(),extraUpgrades);
                    addToList(cardsList, new ENBaseMental(),true);
                    addToList(cardsList, new ENBasePerform(),true);
                    addToList(cardsList, new ENBasePose());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENClimax());
                    addToList(cardsList, new ENSkipWater());
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