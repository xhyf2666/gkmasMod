package gkmasmod.downfall.bosses;

import com.megacrit.cardcrawl.powers.MetallicizePower;
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
import gkmasmod.powers.AngelAndDemonPlusPower;
import gkmasmod.powers.NotAfraidAnymorePower;
import gkmasmod.powers.WakuWakuPower;

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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WakuWakuPower(p,2),2));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AngelAndDemonPlusPower(p)));
        if(AbstractDungeon.ascensionLevel >= 15)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MetallicizePower(p,10),10));
    }

    public void initialize() {
        addRelic(new CBR_Strawberry());
        addRelic(new CBR_Ginger());
        addRelic(new CBR_OddlySmoothStone());
        addRelic(new CBR_ShibaInuPochette());
        addRelic(new CBR_RollingSourceOfEnergy());
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
                    addToList(cardsList, new ENUnstoppableThoughts(),extraUpgrades);
                    addToList(cardsList, new ENFlowerBouquet(),extraUpgrades);
                    addToList(cardsList, new ENOverflowMemory(),extraUpgrades);
                    addToList(cardsList, new ENFlowering());
                    addToList(cardsList, new ENBasePose(),extraUpgrades);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENImaginaryTraining(), extraUpgrades);
                    addToList(cardsList, new ENUntappedPotential(),true);
                    addToList(cardsList, new ENDefeatBigSister());
                    addToList(cardsList, new ENOverflowMemory(), extraUpgrades);
                    addToList(cardsList, new ENBasePose(),extraUpgrades);
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
                    addToList(cardsList, new ENOverflowMemory(), extraUpgrades);
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