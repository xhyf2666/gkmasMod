package gkmasmod.downfall.bosses;

import gkmasmod.cards.free.GradualDisappearance;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.*;
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.relics.*;
import gkmasmod.powers.DanceWithYouPower;

import java.util.ArrayList;

public class BossConfig_ssmk1 extends AbstractBossDeckArchetype {

    public BossConfig_ssmk1() {
        super("hski_config2","hski", "goodImpression");
        maxHPModifier += 0;
        actNum = 3;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BarricadePower(p)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DanceWithYouPower(p,4),4));
        if(AbstractDungeon.ascensionLevel >= 15)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DanceWithYouPower(p,4),4));
    }

    public void initialize() {
        addRelic(new CBR_Kunai());
        addRelic(new CBR_Ginger());
        addRelic(new CBR_RedSkull());
        addRelic(new CBR_AfterSchoolDoodles());
        addRelic(new CBR_ArcadeLoot());
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
                    addToList(cardsList, new ENSleepLate(),extraUpgrades);
                    addToList(cardsList, new ENSteadyWill(),extraUpgrades);
                    addToList(cardsList, new ENTopEntertainment(),extraUpgrades);
                    addToList(cardsList, new ENInnocence(),extraUpgrades);
                    addToList(cardsList, new ENBaseAppeal());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENGradualDisappearance(),extraUpgrades);
                    addToList(cardsList, new ENStartSignal());
                    addToList(cardsList, new ENWarmUp());
                    addToList(cardsList, new ENLeap());
                    addToList(cardsList, new ENBaseExpression());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENCharmPerformance(),extraUpgrades);
                    addToList(cardsList, new ENNationalIdol(),extraUpgrades);
                    addToList(cardsList, new ENOneMoreStep(),true);
                    addToList(cardsList, new ENSleepy());
                    addToList(cardsList, new ENBaseAppeal());
                    addToList(cardsList, new ENBaseAppeal());

                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENEncoreCall(),extraUpgrades);
                    addToList(cardsList, new ENBasePose(),extraUpgrades);
                    addToList(cardsList, new ENTryError());
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}