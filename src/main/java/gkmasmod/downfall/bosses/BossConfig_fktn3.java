package gkmasmod.downfall.bosses;

import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.CBR_Ectoplasm;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.ENBasePerform;
import gkmasmod.downfall.cards.free.ENFullOfPower;
import gkmasmod.downfall.cards.free.ENSkipWater;
import gkmasmod.downfall.cards.logic.*;
import gkmasmod.downfall.relics.*;

import java.util.ArrayList;

public class BossConfig_fktn3 extends AbstractBossDeckArchetype {

    public BossConfig_fktn3() {
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
        addRelic(new CBR_FreeLoveMax());
        addRelic(new CBR_FavoriteSneakers());
        addRelic(new CBR_PigDreamPiggyBank());
        addRelic(new CBR_CracklingSparkler());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENHappyChristmas(),true);
                    addToList(cardsList,new ENBeforeThePerformance(),extraUpgrades);
                    addToList(cardsList, new ENUnstoppableThoughts(),extraUpgrades);
                    addToList(cardsList, new ENSummerEveningSparklers(),true);
                    addToList(cardsList, new ENWorldFirstCute(),true);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENIsENotA(), extraUpgrades);
                    addToList(cardsList, new ENHardStretching(), extraUpgrades);
                    addToList(cardsList, new ENFlowerBouquet(),extraUpgrades);
                    addToList(cardsList, new ENSmile200());
                    addToList(cardsList, new ENFullOfPower(),extraUpgrades);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENWakeUp(), extraUpgrades);
                    addToList(cardsList, new ENHardStretching(), extraUpgrades);
                    addToList(cardsList, new ENSkipWater(), extraUpgrades);
                    addToList(cardsList, new ENRestart(),extraUpgrades);
                    addToList(cardsList, new ENPromiseThatTime());
                    addToList(cardsList, new ENLikeEveryone());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENYeah(),extraUpgrades);
                    addToList(cardsList, new ENHardStretching(), extraUpgrades);
                    addToList(cardsList, new ENRestart());
                    addToList(cardsList, new ENPromiseThatTime(),extraUpgrades);
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