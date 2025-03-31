package gkmasmod.downfall.bosses;

import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.downfall.cards.anomaly.ENClimax;
import gkmasmod.downfall.cards.anomaly.ENStarPickingConcentration;
import gkmasmod.downfall.cards.free.ENBaseAppeal;
import gkmasmod.downfall.cards.free.ENNecessaryContrast;
import gkmasmod.downfall.charbosses.actions.unique.EnemyChangeStanceAction;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.ENBasePose;
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.charbosses.stances.ENPreservationStance;
import gkmasmod.downfall.relics.*;
import gkmasmod.powers.*;

import java.util.ArrayList;

public class BossConfig_shro3 extends AbstractBossDeckArchetype {

    public BossConfig_shro3() {
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
        addRelic(new CBR_Sozu());
        addRelic(new CBR_Shuriken());
        addRelic(new CBR_RedSkull());
        addRelic(new CBR_NaughtyPuppet());
        addRelic(new CBR_LizardTail());
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EurekaPlusPower(p)));
        if(AbstractDungeon.ascensionLevel >= 15)
            AbstractDungeon.actionManager.addToBottom(new EnemyChangeStanceAction(ENPreservationStance.STANCE_ID2));
        else
            AbstractDungeon.actionManager.addToBottom(new EnemyChangeStanceAction(ENPreservationStance.STANCE_ID));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NegativeNotPower(p, 3), 3));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 4), 4));
        if(AbstractDungeon.ascensionLevel >= 10)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new InnocencePower(p, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EyePowerPower(p, 2), 2));
        if(AbstractDungeon.ascensionLevel >= 15)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TopEntertainmentPlusPower(p, 1), 1));
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENMakeExactSignboard(),extraUpgrades);
                    addToList(cardsList, new ENNecessaryContrast(),true);
                    addToList(cardsList, new ENClimax(),extraUpgrades);
                    addToList(cardsList, new ENWonderfulPerformance(),extraUpgrades);
                    addToList(cardsList, new ENDisposition(),extraUpgrades);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENStarPickingConcentration(),extraUpgrades);
                    addToList(cardsList, new ENCharmGaze());
                    addToList(cardsList, new ENNationalIdol(), extraUpgrades);
                    addToList(cardsList, new ENLeap(),extraUpgrades);
                    addToList(cardsList, new ENBaseAppeal(),extraUpgrades);
                    turn=0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENExistence());
                    addToList(cardsList, new ENEncoreCall());
                    addToList(cardsList, new ENAwakening(),extraUpgrades);
                    addToList(cardsList, new ENBasePose(),extraUpgrades);
                    addToList(cardsList, new ENBaseAppeal(),extraUpgrades);
                    addToList(cardsList, new ENBaseAppeal(),extraUpgrades);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENExistence());
                    addToList(cardsList, new ENEncoreCall());
                    addToList(cardsList, new ENAwakening(),extraUpgrades);
                    addToList(cardsList, new ENBaseBehave(),extraUpgrades);
                    addToList(cardsList, new ENBaseAppeal(),extraUpgrades);
                    addToList(cardsList, new ENBaseAppeal(),extraUpgrades);
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