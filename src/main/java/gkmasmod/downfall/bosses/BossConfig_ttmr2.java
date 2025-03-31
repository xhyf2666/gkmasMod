package gkmasmod.downfall.bosses;

import gkmasmod.downfall.cards.free.ENEatFruit;
import gkmasmod.downfall.charbosses.actions.common.EnemyGainEnergyAction;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.ENCallMeAnyTime;
import gkmasmod.downfall.cards.logic.*;
import gkmasmod.downfall.relics.CBR_ProducerPhone;
import gkmasmod.downfall.relics.CBR_ThisIsMe;
import gkmasmod.powers.*;

import java.util.ArrayList;

public class BossConfig_ttmr2 extends AbstractBossDeckArchetype {

    public BossConfig_ttmr2() {
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
        addRelic(new CBR_Strawberry());
        addRelic(new CBR_Strawberry());
        addRelic(new CBR_Strawberry());
        addRelic(new CBR_Strawberry());
        addRelic(new CBR_Strawberry());
        addRelic(new CBR_Strawberry());
        addRelic(new CBR_MarkOfPain());
        addRelic(new CBR_BronzeScales());
        addRelic(new CBR_ThisIsMe());
        addRelic(new CBR_ProducerPhone());
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NegativeNotPower(p, 2), 2));
        if(AbstractDungeon.ascensionLevel >= 15){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new GoodImpression(p,20),20));
        }
        else
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new GoodImpression(p,10),10));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RainbowDreamerPower(p,2),2));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IsENotAPower(p,7),7));
        AbstractDungeon.actionManager.addToBottom(new EnemyGainEnergyAction(1));
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENEatFruit(),true);
                    addToList(cardsList, new ENForShiningYou(), true);
                    addToList(cardsList, new ENTangledFeelings(),extraUpgrades);
                    addToList(cardsList, new ENHappyCurse(),extraUpgrades);
                    addToList(cardsList, new ENCallMeAnyTime(),true);
                    addToList(cardsList, new ENFirstPlace(),extraUpgrades);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENEatFruit(),true);
                    addToList(cardsList, new ENIamBigStar(),extraUpgrades);
                    addToList(cardsList, new ENNotebookDetermination());
                    addToList(cardsList, new ENWakeUp(),extraUpgrades);
                    addToList(cardsList, new ENBaseVision());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENEatFruit(),true);
                    addToList(cardsList, new ENGirlHeart(),extraUpgrades);
                    addToList(cardsList, new ENHappyTime(),true);
                    addToList(cardsList, new ENBaseVision());
                    addToList(cardsList, new ENKawaiiGesture());
                    addToList(cardsList, new ENSweetWink(),extraUpgrades);
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}