package gkmasmod.downfall.bosses;

import gkmasmod.downfall.cards.free.*;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.relics.*;
import gkmasmod.powers.ListenToMyTrueHeartPower;

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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ListenToMyTrueHeartPower(p, 4), 4));
    }

    public void initialize() {
        addRelic(new CBR_Pear());
        addRelic(new CBR_Pear());
        addRelic(new CBR_Pear());
        addRelic(new CBR_Pear());
        addRelic(new CBR_Pear());
        addRelic(new CBR_Pear());
        addRelic(new CBR_Lantern());
        addRelic(new CBR_ProtectiveEarphones());
        addRelic(new CBR_MyFirstSheetMusic());
        addRelic(new CBR_EssentialStainlessSteelBottle());
        addRelic(new CBR_SongToTheSun());
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
                    addToList(cardsList, new ENEatFruit(),true);
                    addToList(cardsList, new ENSteadyWill(),extraUpgrades);
                    addToList(cardsList, new ENSayGoodbyeToDislikeMyself(),true);
                    addToList(cardsList, new ENGoWithTheFlow(),true);
                    addToList(cardsList, new ENEachPath(),true);
                    addToList(cardsList, new ENBaseAppeal(),extraUpgrades);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENEatFruit(),true);
                    addToList(cardsList, new ENSpotlight());
                    addToList(cardsList, new ENCharmPerformance(),true);
                    addToList(cardsList, new ENListenToMyTrueHeart(),true);
                    addToList(cardsList, new ENGoWithTheFlow(),true);
                    addToList(cardsList, new ENTriggerRelic());
                    addToList(cardsList, new ENIdolDeclaration(),extraUpgrades);
                    addToList(cardsList, new ENTrueDeepBreath(),true);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENEatFruit(),true);
                    addToList(cardsList, new ENExistence(),extraUpgrades);
                    addToList(cardsList, new ENEncoreCall(),true);
                    addToList(cardsList, new ENGoWithTheFlow(),true);
                    addToList(cardsList, new ENPushingTooHardAgain(),extraUpgrades);
                    addToList(cardsList, new ENTriggerRelic());
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