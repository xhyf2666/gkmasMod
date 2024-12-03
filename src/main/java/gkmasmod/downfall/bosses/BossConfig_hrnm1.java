package gkmasmod.downfall.bosses;

import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.*;
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.relics.*;

import java.util.ArrayList;

public class BossConfig_hrnm1 extends AbstractBossDeckArchetype {

    public BossConfig_hrnm1() {
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
        addRelic(new CBR_RegularMakeupPouch());
        addRelic(new CBR_TreatForYou());
        addRelic(new CBR_LifeSizeLadyLip());
        addRelic(new CBR_SummerToShareWithYou());
        addRelic(new CBR_ProducerPhone());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENSteadyWill(),extraUpgrades);
                    addToList(cardsList, new ENSenseOfDistance(),extraUpgrades);
                    addToList(cardsList, new ENSaySomethingToYou(),extraUpgrades);
                    addToList(cardsList, new ENSupportiveFeelings());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENWarmCare());
                    addToList(cardsList, new ENStartSignal(),true);
                    addToList(cardsList, new ENCumulusCloudsAndYou(),true);
                    addToList(cardsList, new ENBaseAppeal());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENIdolDeclaration(),extraUpgrades);
                    addToList(cardsList, new ENLeap(),extraUpgrades);
                    addToList(cardsList, new ENWantToGoThere(),extraUpgrades);
                    addToList(cardsList, new ENLightGait());
                    addToList(cardsList, new ENTriggerRelic());
                    addToList(cardsList, new ENTriggerRelic());
                    addToList(cardsList, new ENBaseAppeal());

                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENBasePose(),extraUpgrades);
                    addToList(cardsList, new ENTalkTime(),extraUpgrades);
                    addToList(cardsList, new ENImprovise());
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