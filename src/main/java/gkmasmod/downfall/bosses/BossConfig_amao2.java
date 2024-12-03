package gkmasmod.downfall.bosses;

import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.CBR_BustedCrown;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.ENBaseAppeal;
import gkmasmod.downfall.cards.free.ENBasePose;
import gkmasmod.downfall.cards.free.ENLoveMyselfCool;
import gkmasmod.downfall.cards.free.ENLoveMyselfCute;
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.relics.CBR_DearLittlePrince;
import gkmasmod.downfall.relics.CBR_GentlemanHandkerchief;
import gkmasmod.downfall.relics.CBR_InnerLightEarrings;
import gkmasmod.downfall.relics.CBR_LastSummerMemory;

import java.util.ArrayList;

public class BossConfig_amao2 extends AbstractBossDeckArchetype {

    public BossConfig_amao2() {
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
        addRelic(new CBR_BustedCrown());
        addRelic(new CBR_LastSummerMemory());
        addRelic(new CBR_GentlemanHandkerchief());
        addRelic(new CBR_DearLittlePrince());
        addRelic(new CBR_InnerLightEarrings());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENLoveMyselfCool(),extraUpgrades);
                    addToList(cardsList, new ENSteadyWill(),extraUpgrades);
                    addToList(cardsList, new ENEndlessApplause(),extraUpgrades);
                    addToList(cardsList, new ENAuthenticity(),extraUpgrades);
                    addToList(cardsList, new ENBaseAppeal());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENLoveMyselfCute(),extraUpgrades);
                    addToList(cardsList, new ENDisposition());
                    addToList(cardsList, new ENStartSignal());
                    addToList(cardsList, new ENImprovise(),extraUpgrades);
                    addToList(cardsList, new ENLeap(),extraUpgrades);
                    addToList(cardsList, new ENBaseAppeal());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENLoveMyselfCool(),extraUpgrades);
                    addToList(cardsList, new ENCharmPerformance(),extraUpgrades);
                    addToList(cardsList, new ENNationalIdol(),extraUpgrades);
                    addToList(cardsList, new ENChillyBreak());
                    addToList(cardsList, new ENBaseAppeal());

                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENLoveMyselfCool(),extraUpgrades);
                    addToList(cardsList, new ENExistence(),extraUpgrades);
                    addToList(cardsList, new ENEncoreCall(),extraUpgrades);
                    addToList(cardsList, new ENAwakening());
                    addToList(cardsList, new ENBasePose());
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}