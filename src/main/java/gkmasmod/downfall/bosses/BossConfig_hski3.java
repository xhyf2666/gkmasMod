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
import gkmasmod.downfall.relics.CBR_AnimateEquipment;
import gkmasmod.downfall.relics.CBR_RoaringLion;

import java.util.ArrayList;

public class BossConfig_hski3 extends AbstractBossDeckArchetype {

    public BossConfig_hski3() {
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
        addRelic(new CBR_Girya(4));
        addRelic(new CBR_RedSkull());
        addRelic(new CBR_FusionHammer());
        addRelic(new CBR_AnimateEquipment());
        addRelic(new CBR_RoaringLion());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList,new ENSteadyWill(),extraUpgrades);
                    addToList(cardsList, new ENBaseExpression(),extraUpgrades);
                    addToList(cardsList, new ENBaseExpression(),extraUpgrades);
                    addToList(cardsList, new ENEyePower(),extraUpgrades);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENInnocence(), extraUpgrades);
                    addToList(cardsList, new ENSleepLate(),extraUpgrades);
                    addToList(cardsList, new ENTopEntertainment());
                    addToList(cardsList, new ENTrueLateBloomer());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENCharmGaze(),extraUpgrades);
                    addToList(cardsList, new ENNationalIdol(),extraUpgrades);
                    addToList(cardsList, new ENVeryEasy(),true);
                    addToList(cardsList, new ENDisposition());
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new ENImprovise(), extraUpgrades);
                    addToList(cardsList, new ENLeap(),extraUpgrades);
                    addToList(cardsList, new ENBaseAppeal());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENExistence(), extraUpgrades);
                    addToList(cardsList, new ENImprovise());
                    addToList(cardsList, new ENTryError());
                    addToList(cardsList, new ENBaseAppeal());
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}