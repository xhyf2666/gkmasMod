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
import gkmasmod.downfall.cards.logic.*;
import gkmasmod.downfall.relics.CBR_UndefeatedPoi;
import gkmasmod.downfall.relics.CBR_WinningDetermination;

import java.util.ArrayList;

public class BossConfig_hski2 extends AbstractBossDeckArchetype {

    public BossConfig_hski2() {
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
        addRelic(new CBR_CoffeeDripper());
        addRelic(new CBR_Torii());
        addRelic(new CBR_TungstenRod());
        addRelic(new CBR_WinningDetermination());
        addRelic(new CBR_UndefeatedPoi());
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
                    addToList(cardsList, new ENForShiningYou(),extraUpgrades);
                    addToList(cardsList, new ENIamBigStar(),extraUpgrades);
                    addToList(cardsList, new ENMotherAI(),extraUpgrades);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENIdolDeclaration());
                    addToList(cardsList, new ENFantasyCharm(), extraUpgrades);
                    addToList(cardsList, new ENHeartbeat(),extraUpgrades);
                    addToList(cardsList, new ENEnergyIsFull());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENBaseVision(),extraUpgrades);
                    addToList(cardsList, new ENHappyCurse(),extraUpgrades);
                    addToList(cardsList, new ENStarDust());
                    addToList(cardsList, new ENBaseVision());
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new ENKawaiiGesture(),extraUpgrades);
                    addToList(cardsList, new ENPow(),extraUpgrades);
                    addToList(cardsList, new ENFirstFuture());
                    addToList(cardsList, new ENIsENotA());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENBaseVision(),extraUpgrades);
                    addToList(cardsList, new ENBaseVision());
                    addToList(cardsList, new ENSweetWink());
                    addToList(cardsList, new ENSmile());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENBaseVision(), extraUpgrades);
                    addToList(cardsList, new ENBaseVision(),extraUpgrades);
                    addToList(cardsList, new ENGirlHeart(),extraUpgrades);
                    addToList(cardsList, new ENFlowerBouquet(),extraUpgrades);
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