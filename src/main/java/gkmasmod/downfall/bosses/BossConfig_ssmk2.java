package gkmasmod.downfall.bosses;

import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.ENBasePerform;
import gkmasmod.downfall.cards.free.ENDanceWithYou;
import gkmasmod.downfall.cards.logic.*;
import gkmasmod.downfall.relics.CBR_FrogFan;
import gkmasmod.downfall.relics.CBR_TheWing;
import gkmasmod.powers.IsENotAPower;
import gkmasmod.powers.NegativeNotPower;
import gkmasmod.powers.TopWisdomPlusPower;
import gkmasmod.powers.WakuWakuPower;

import java.util.ArrayList;

public class BossConfig_ssmk2 extends AbstractBossDeckArchetype {

    public BossConfig_ssmk2() {
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
        addRelic(new CBR_Ectoplasm());
        addRelic(new CBR_OddlySmoothStone());
        addRelic(new CBR_Turnip());
        addRelic(new CBR_FossilizedHelix());
        addRelic(new CBR_FrogFan());
        addRelic(new CBR_TheWing());
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NegativeNotPower(p, 2), 2));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IsENotAPower(p,6),6));
        if(AbstractDungeon.ascensionLevel >= 10)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TopWisdomPlusPower(p, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WakuWakuPower(p, 1), 1));
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENUnstoppableThoughts(),extraUpgrades);
                    addToList(cardsList, new ENIamBigStar(),extraUpgrades);
                    addToList(cardsList, new ENAfternoonBreeze(),true);
                    addToList(cardsList, new ENFlyAgain(), true);
                    addToList(cardsList, new ENRestart(),true);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENDanceWithYou(),true);
                    addToList(cardsList, new ENOverflowMemory(),true);
                    addToList(cardsList, new ENEnergyIsFull(), extraUpgrades);
                    addToList(cardsList, new ENRestart(),true);
                    addToList(cardsList, new ENThankYou());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENOverflowMemory(),true);
                    addToList(cardsList, new ENRestart(),true);
                    addToList(cardsList, new ENThankYou(),extraUpgrades);
                    addToList(cardsList, new ENBasePerform());
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}