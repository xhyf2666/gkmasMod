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
import gkmasmod.powers.FantasyCharmPower;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.MotherAIPower;
import gkmasmod.powers.NegativeNotPower;

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
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NegativeNotPower(p, 2), 2));
        if(AbstractDungeon.ascensionLevel >= 15){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MotherAIPower(p)));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FantasyCharmPower(p, 2), 2));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new GoodImpression(p, 10), 10));
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENForShiningYou(),extraUpgrades);
                    addToList(cardsList, new ENIamBigStar(),true);
                    if(AbstractDungeon.ascensionLevel >= 15){
                        addToList(cardsList, new ENSSDSecret(),true);
                    }
                    else{
                        addToList(cardsList, new ENMotherAI(),true);
                    }
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENIdolDeclaration());
                    addToList(cardsList, new ENHeartbeat(),extraUpgrades);
                    addToList(cardsList, new ENEnergyIsFull());
                    addToList(cardsList, new ENStarDust());
                    addToList(cardsList, new ENBaseVision());
                    addToList(cardsList, new ENPow(),extraUpgrades);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENGirlHeart(),extraUpgrades);
                    addToList(cardsList, new ENFlowerBouquet(),extraUpgrades);
                    addToList(cardsList, new ENSweetWink());
                    addToList(cardsList, new ENBaseVision(), extraUpgrades);
                    addToList(cardsList, new ENBaseVision(), extraUpgrades);
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}