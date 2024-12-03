package gkmasmod.downfall.bosses;

import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.CBR_Anchor;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.ENBasePerform;
import gkmasmod.downfall.cards.logic.*;
import gkmasmod.downfall.relics.*;

import java.util.ArrayList;

public class BossConfig_kcna1 extends AbstractBossDeckArchetype {

    public int slowGrowth = 0;

    public BossConfig_kcna1() {
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
        addRelic(new CBR_MembershipCard());
        addRelic(new CBR_RegalPillow());
        addRelic(new CBR_Anchor());
        addRelic(new CBR_SecretTrainingCardigan());
        addRelic(new CBR_CheerfulHandkerchief());
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
                    addToList(cardsList, new ENWholeheartedly(),extraUpgrades);
                    addToList(cardsList, new ENBasePerform());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENFlowering(), extraUpgrades);
                    addToList(cardsList, new ENSunbathing(),extraUpgrades);
                    addToList(cardsList, new ENHardStretching(),extraUpgrades);
                    addToList(cardsList, new ENOverflowMemory());
                    addToList(cardsList, new ENBasePerform());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENEnergyIsFull(), extraUpgrades);
                    addToList(cardsList, new ENYeah(),extraUpgrades);
                    addToList(cardsList, new ENHardStretching(),extraUpgrades);
                    addToList(cardsList, new ENContact());
                    addToList(cardsList, new ENDebutStageForTheLady());
                    addToList(cardsList, new ENBasePerform());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENBasePerform(),extraUpgrades);
                    addToList(cardsList, new ENBasePerform());
                    addToList(cardsList, new ENSlowGrowth(slowGrowth),extraUpgrades);
                    addToList(cardsList, new ENBasePerform());
                    if(slowGrowth <4)
                        slowGrowth++;
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}