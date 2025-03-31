package gkmasmod.downfall.bosses;

import gkmasmod.downfall.cards.anomaly.*;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.downfall.charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.*;
import gkmasmod.downfall.cards.logic.*;
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.relics.*;
import gkmasmod.powers.*;

import java.util.ArrayList;
import java.util.HashMap;

public class BossConfig_hume2 extends AbstractBossDeckArchetype {

    public BossConfig_hume2() {
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
        addRelic(new CBR_OrnamentalFan());
        addRelic(new CBR_BalanceLogicAndSense());
        addRelic(new CBR_PhilosopherStone());
        addRelic(new CBR_RollingSourceOfEnergy());
        addRelic(new CBR_ChristmasLion());
        addRelic(new CBR_AchieveDreamAwakening());
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NegativeNotPower(p, 2), 2));
        if(AbstractDungeon.ascensionLevel >= 5)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TopWisdomPlusPower(p, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EyePowerPower(p, 2), 2));
        if(AbstractDungeon.ascensionLevel >= 10)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TrueLateBloomerPower(p, 2).setMagic2(2), 2));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ShareSomethingWithYouPower(p, 2), 2));
        if(AbstractDungeon.ascensionLevel >= 15){
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
                    addToList(cardsList, new ENTrainingResult(),extraUpgrades);
                    addToList(cardsList, new ENGradualDisappearance(),true);
                    addToList(cardsList, new ENBecomeIdol());
                    addToList(cardsList, new ENShineBright(), true);
                    addToList(cardsList, new ENNewStage(),true);
                    addToList(cardsList, new ENJustAppeal(),true);
                    addToList(cardsList, new ENPotentialAbility());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENIamBigStar(),extraUpgrades);
                    addToList(cardsList, new ENNewStage(),extraUpgrades);
                    addToList(cardsList, new ENPotentialAbility());
                    addToList(cardsList, new ENSisterHelp());
//                    addToList(cardsList, new ENBaseMental(),true);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENNewStage(),extraUpgrades);
                    addToList(cardsList, new ENPotentialAbility());
//                    addToList(cardsList, new ENBaseMental(),true);
                    addToList(cardsList, new ENTriggerRelic());
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