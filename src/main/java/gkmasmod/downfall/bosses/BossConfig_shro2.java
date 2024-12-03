package gkmasmod.downfall.bosses;

import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.ENNecessaryContrast;
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.relics.CBR_TowardsAnUnseenWorld;

import java.util.ArrayList;

public class BossConfig_shro2 extends AbstractBossDeckArchetype {

    public BossConfig_shro2() {
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
        addRelic(new CBR_StoneCalendar());
        addRelic(new CBR_Kunai());
        addRelic(new CBR_VelvetChoker());
        addRelic(new CBR_RedSkull());
        addRelic(new CBR_TowardsAnUnseenWorld());
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
                    addToList(cardsList, new ENNecessaryContrast(),extraUpgrades);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENEyePower(), extraUpgrades);
                    addToList(cardsList, new ENAchievement());
                    addToList(cardsList, new ENLeap());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENJustOneMore(),extraUpgrades);
                    addToList(cardsList, new ENStartSignal(),extraUpgrades);
                    addToList(cardsList, new ENCharmPerformance(),true);
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new ENSwayingOnTheBus(),extraUpgrades);
                    addToList(cardsList, new ENPopPhrase(),extraUpgrades);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENEncoreCall(),extraUpgrades);
                    addToList(cardsList, new ENWarmCare());
                    addToList(cardsList, new ENLeap(),true);
                    addToList(cardsList, new ENTryError());
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}