package gkmasmod.downfall.bosses;

import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.ENBaseAppeal;
import gkmasmod.downfall.cards.logic.*;
import gkmasmod.downfall.relics.*;

import java.util.ArrayList;

public class BossConfig_hrnm3 extends AbstractBossDeckArchetype {

    public BossConfig_hrnm3() {
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
        addRelic(new CBR_OddlySmoothStone());
        addRelic(new CBR_ProducerPhone());
        addRelic(new CBR_FusionHammer());
        addRelic(new CBR_ItsPerfect());
        addRelic(new CBR_ClapClapFan());
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
                    addToList(cardsList, new ENIamBigStar(),extraUpgrades);
                    addToList(cardsList, new ENWakuWaku());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENIsENotA(),extraUpgrades);
                    addToList(cardsList, new ENFlowerBouquet());
                    addToList(cardsList, new ENRefreshingBreak(),extraUpgrades);
                    addToList(cardsList, new ENYeah(),extraUpgrades);
                    addToList(cardsList, new ENStarDust(),extraUpgrades);
                    addToList(cardsList, new ENEnjoySummer(),extraUpgrades);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENPromiseThatTime(),extraUpgrades);
                    addToList(cardsList, new ENSeeYouTomorrow(),true);
                    addToList(cardsList, new ENWakeUp(),true);
                    addToList(cardsList, new ENBaseAppeal());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENFlowerBouquet(),extraUpgrades);
                    addToList(cardsList, new ENRestart(),extraUpgrades);
                    addToList(cardsList, new ENSweetWink());
                    addToList(cardsList, new ENPromiseThatTime(),extraUpgrades);
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}