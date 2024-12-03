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
import gkmasmod.downfall.cards.logic.*;
import gkmasmod.downfall.relics.CBR_DreamLifeLog;
import gkmasmod.downfall.relics.CBR_MemoryBot;

import java.util.ArrayList;

public class BossConfig_kllj1 extends AbstractBossDeckArchetype {

    public BossConfig_kllj1() {
        super("hski_config3","hski", "focus");
        maxHPModifier += 0;
        actNum = 3;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BarricadePower(p)));
    }

    public void initialize() {
        addRelic(new CBR_PeacePipe());
        addRelic(new CBR_Orichalcum());
        addRelic(new CBR_Lantern());
        addRelic(new CBR_MemoryBot());
        addRelic(new CBR_DreamLifeLog());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList,new ENBeforeThePerformance(),extraUpgrades);
                    addToList(cardsList, new ENUnstoppableThoughts(),extraUpgrades);
                    addToList(cardsList, new ENYoungDream(),extraUpgrades);
                    addToList(cardsList, new ENHeartbeat(),extraUpgrades);
                    addToList(cardsList, new ENBasePerform());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENBaseVision(), extraUpgrades);
                    addToList(cardsList, new ENStarDust(),extraUpgrades);
                    addToList(cardsList, new ENEnergyIsFull());
                    addToList(cardsList, new ENIamBigStar(),extraUpgrades);
                    addToList(cardsList, new ENWakeUp(),extraUpgrades);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENHappyCurse(), extraUpgrades);
                    addToList(cardsList, new ENHappyTime(),extraUpgrades);
                    addToList(cardsList, new ENFutureTrajectory());
                    addToList(cardsList, new ENBasePerform());
                    addToList(cardsList, new ENBasePerform());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENFlowerBouquet(),extraUpgrades);
                    addToList(cardsList, new ENLikeEveryone(),true);
                    addToList(cardsList, new ENBaseVision(),extraUpgrades);
                    addToList(cardsList, new ENBaseVision(),extraUpgrades);
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