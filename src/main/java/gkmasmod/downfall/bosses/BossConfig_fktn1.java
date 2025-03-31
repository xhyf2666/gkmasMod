package gkmasmod.downfall.bosses;

import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.CBR_HappyFlower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.ENBasePerform;
import gkmasmod.downfall.cards.logic.*;
import gkmasmod.downfall.charbosses.relics.CBR_Sozu;
import gkmasmod.downfall.relics.*;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.KawaiiPower;

import java.util.ArrayList;

public class BossConfig_fktn1 extends AbstractBossDeckArchetype {

    public BossConfig_fktn1() {
        super("hski_config3","hski", "focus");
        maxHPModifier += 0;
        actNum = 3;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BarricadePower(p)));
        if(AbstractDungeon.ascensionLevel >= 15){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new KawaiiPower(p,3),3));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new GoodImpression(p,10),10));
        }
        else
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new KawaiiPower(p,2),2));
    }

    public void initialize() {
        addRelic(new CBR_HappyFlower());
        addRelic(new CBR_MawBank());
        addRelic(new CBR_HandmadeMedal());
        addRelic(new CBR_FavoriteSneakers());
        addRelic(new CBR_PigDreamPiggyBank());
        addRelic(new CBR_OnlyMyFirstStar());
        if(AbstractDungeon.ascensionLevel >= 10)
            addRelic(new CBR_Sozu());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENBeforeThePerformance(),extraUpgrades);
                    addToList(cardsList, new ENUnstoppableThoughts(),extraUpgrades);
                    addToList(cardsList, new ENWorldFirstCute(),extraUpgrades);
                    addToList(cardsList, new ENSmile(),true);
                    addToList(cardsList, new ENHeartbeat(),extraUpgrades);
                    addToList(cardsList, new ENBasePerform());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENSmile(),true);
                    addToList(cardsList, new ENNoDistractions(), extraUpgrades);
                    addToList(cardsList, new ENSSDSecret(),extraUpgrades);
                    addToList(cardsList, new ENRainbowDreamer());
                    addToList(cardsList, new ENBasePerform(),extraUpgrades);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENSmile(),true);
                    addToList(cardsList, new ENColorfulCute(), extraUpgrades);
                    addToList(cardsList, new ENWakeUp(),extraUpgrades);
                    addToList(cardsList, new ENBaseVision());
                    addToList(cardsList, new ENBasePerform());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENFlowerBouquet(),extraUpgrades);
                    addToList(cardsList, new ENLikeEveryone());
                    addToList(cardsList, new ENBaseVision(),extraUpgrades);
                    addToList(cardsList, new ENKawaiiGesture());
                    addToList(cardsList, new ENSweetWink(),extraUpgrades);
                    addToList(cardsList, new ENSmile(),extraUpgrades);
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}