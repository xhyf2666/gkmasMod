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
import gkmasmod.downfall.relics.CBR_HeartFlutteringCup;
import gkmasmod.downfall.relics.CBR_OldCoin;

import java.util.ArrayList;

public class BossConfig_kcna2 extends AbstractBossDeckArchetype {

    public BossConfig_kcna2() {
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
        addRelic(new CBR_OldCoin());
        addRelic(new CBR_OldCoin());
        addRelic(new CBR_OldCoin());
        addRelic(new CBR_Sozu());
        addRelic(new CBR_ArtOfWar());
        addRelic(new CBR_HeartFlutteringCup());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENSteadyWill(),extraUpgrades);
                    addToList(cardsList, new ENInnocence(),extraUpgrades);
                    addToList(cardsList, new ENAngelAndDemon(),extraUpgrades);
                    addToList(cardsList, new ENBaseAppeal());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENTopEntertainment());
                    addToList(cardsList, new ENDisposition(), extraUpgrades);
                    addToList(cardsList, new ENStartSignal());
                    addToList(cardsList, new ENImprovise());

                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENWelcomeToTeaParty(),extraUpgrades);
                    addToList(cardsList, new ENEncoreCall(),extraUpgrades);
                    addToList(cardsList, new ENTryError());
                    addToList(cardsList, new ENTriggerRelic());
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new ENWantToGoThere(),true);
                    addToList(cardsList, new ENAchievement(),extraUpgrades);
                    addToList(cardsList, new ENEncoreCall(),extraUpgrades);
                    addToList(cardsList, new ENSpecialTreasure(),true);
                    addToList(cardsList, new ENTriggerRelic());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENEncoreCall(),extraUpgrades);
                    addToList(cardsList, new ENWarmCare());
                    addToList(cardsList, new ENBaseAppeal());
                    addToList(cardsList, new ENTryError());
                    addToList(cardsList, new ENSpecialTreasure(),true);
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}