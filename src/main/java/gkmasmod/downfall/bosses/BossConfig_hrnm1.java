package gkmasmod.downfall.bosses;

import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.downfall.cards.anomaly.ENLetMeBecomeIdol;
import gkmasmod.downfall.cards.anomaly.ENSurpriseMiss;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.*;
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.charbosses.relics.CBR_Sozu;
import gkmasmod.downfall.relics.*;
import gkmasmod.powers.*;

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
        addRelic(new CBR_YouFindMe());
        addRelic(new CBR_ProducerPhone());
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NegativeNotPower(p, 2), 2));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 3), 3));
        if(AbstractDungeon.ascensionLevel >= 10)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new InnocencePower(p, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EyePowerPower(p, 2), 2));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FullPowerValue(p, 3), 3));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SaySomethingToYouPower(p)));
        if(AbstractDungeon.ascensionLevel >= 15) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FullPowerValue(p, 10), 10));
        }
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENSurpriseMiss(),extraUpgrades);
                    addToList(cardsList, new ENSenseOfDistance(),extraUpgrades);
                    addToList(cardsList, new ENLeap(),extraUpgrades);
                    addToList(cardsList, new ENBaseAppeal());
                    addToList(cardsList, new ENTriggerRelic());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENCumulusCloudsAndYou(),true);
                    if(AbstractDungeon.ascensionLevel >= 15) {
                        addToList(cardsList, new ENLetMeBecomeIdol(2),true);
                    }
                    else{
                        addToList(cardsList, new ENLetMeBecomeIdol(1),true);
                    }
                    addToList(cardsList, new ENBaseAppeal());
                    addToList(cardsList, new ENTriggerRelic());
                    addToList(cardsList, new ENTriggerRelic());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENBasePose(),extraUpgrades);
                    if(AbstractDungeon.ascensionLevel >= 15) {
                        addToList(cardsList, new ENLetMeBecomeIdol(2),true);
                    }
                    else{
                        addToList(cardsList, new ENLetMeBecomeIdol(1),true);
                    }
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