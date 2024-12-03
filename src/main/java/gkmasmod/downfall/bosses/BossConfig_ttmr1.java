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
import gkmasmod.downfall.cards.free.ENIdolDeclaration;
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.relics.*;

import java.util.ArrayList;

public class BossConfig_ttmr1 extends AbstractBossDeckArchetype {

    public BossConfig_ttmr1() {
        super("hski_config2","hski", "goodImpression");
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
        addRelic(new CBR_Pear());
        addRelic(new CBR_Pear());
        addRelic(new CBR_Pear());
        addRelic(new CBR_Pear());
        addRelic(new CBR_Pear());
        addRelic(new CBR_Pear());
        addRelic(new CBR_Lantern());
        addRelic(new CBR_Shuriken());
        addRelic(new CBR_ProtectiveEarphones());
        addRelic(new CBR_MyFirstSheetMusic());
        addRelic(new CBR_EssentialStainlessSteelBottle());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList,new ENSteadyWill(),extraUpgrades);
                    addToList(cardsList, new ENBaseExpression(),extraUpgrades);
                    addToList(cardsList, new ENEachPath(),extraUpgrades);
                    addToList(cardsList, new ENBaseAppeal());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENSpotlight());
                    addToList(cardsList, new ENCharmPerformance(),true);
                    addToList(cardsList, new ENTrueDeepBreath(),true);
                    addToList(cardsList, new ENBaseBehave());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENListenToMyTrueHeart(),extraUpgrades);
                    addToList(cardsList, new ENIdolDeclaration(),extraUpgrades);
                    addToList(cardsList, new ENLoneWolf());
                    addToList(cardsList, new ENBaseAppeal());
                    addToList(cardsList, new ENBaseAppeal());
                    addToList(cardsList, new ENBaseAppeal());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENExistence(),extraUpgrades);
                    addToList(cardsList, new ENEncoreCall(),true);
                    addToList(cardsList, new ENPushingTooHardAgain(),extraUpgrades);
                    addToList(cardsList, new ENBaseAppeal());
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}