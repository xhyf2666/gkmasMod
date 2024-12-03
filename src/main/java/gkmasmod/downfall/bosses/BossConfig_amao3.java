package gkmasmod.downfall.bosses;

import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.logic.*;
import gkmasmod.downfall.relics.CBR_FashionMode;

import java.util.ArrayList;

public class BossConfig_amao3 extends AbstractBossDeckArchetype {

    public BossConfig_amao3() {
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
        addRelic(new CBR_BustedCrown());
        addRelic(new CBR_IceCream());
        addRelic(new CBR_OddlySmoothStone());
        addRelic(new CBR_FashionMode());
        addRelic(new CBR_IncenseBurner(3));
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENMoonlitRunway(),extraUpgrades);
                    addToList(cardsList, new ENForShiningYou(),extraUpgrades);
                    addToList(cardsList, new ENUnstoppableThoughts(),extraUpgrades);
                    addToList(cardsList, new ENIamBigStar(),extraUpgrades);
                    addToList(cardsList, new ENRainbowDreamer());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENHappyCurse(), extraUpgrades);
                    addToList(cardsList, new ENHeartbeat(), extraUpgrades);
                    addToList(cardsList, new ENSSDSecret(),extraUpgrades);
                    addToList(cardsList, new ENStarDust());
                    addToList(cardsList, new ENWakeUp(),extraUpgrades);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENMiracleMagic());
                    addToList(cardsList, new ENCreateYourStyle(), extraUpgrades);
                    addToList(cardsList, new ENHappyTime(),extraUpgrades);
                    addToList(cardsList, new ENBaseVision());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENGirlHeart(),extraUpgrades);
                    addToList(cardsList, new ENFlowerBouquet(),extraUpgrades);
                    addToList(cardsList, new ENBaseVision(),extraUpgrades);
                    addToList(cardsList, new ENSmile200(),true);
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}