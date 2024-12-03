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
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.relics.CBR_AfterSchoolDoodles;
import gkmasmod.downfall.relics.CBR_ArcadeLoot;
import gkmasmod.downfall.relics.CBR_PinkUniformBracelet;

import java.util.ArrayList;

public class BossConfig_ssmk3 extends AbstractBossDeckArchetype {

    public BossConfig_ssmk3() {
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
        addRelic(new CBR_Lantern());
        addRelic(new CBR_CoffeeDripper());
        addRelic(new CBR_PinkUniformBracelet());
        addRelic(new CBR_AfterSchoolDoodles());
        addRelic(new CBR_ArcadeLoot());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList,new ENIdolDeclaration(),extraUpgrades);
                    addToList(cardsList,new ENSteadyWill(),extraUpgrades);
                    addToList(cardsList, new ENTopEntertainment(),extraUpgrades);
                    addToList(cardsList, new ENInnocence(),extraUpgrades);
                    addToList(cardsList, new ENWishPower(),true);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENStartSignal());
                    addToList(cardsList, new ENJustOneMore());
                    addToList(cardsList, new ENBalance(),extraUpgrades);
                    addToList(cardsList, new ENDanceWithYou());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENCharmPerformance(),extraUpgrades);
                    addToList(cardsList, new ENOnePersonBallet(),true);
                    addToList(cardsList, new ENNationalIdol(),extraUpgrades);
                    addToList(cardsList, new ENOneMoreStep(),true);
                    addToList(cardsList, new ENBaseAppeal());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENEncoreCall(),extraUpgrades);
                    addToList(cardsList, new ENOnePersonBallet(),extraUpgrades);
                    addToList(cardsList, new ENGoWithTheFlow(),true);
                    addToList(cardsList, new ENLightGait());
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