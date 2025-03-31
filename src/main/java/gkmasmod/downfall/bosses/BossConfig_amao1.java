package gkmasmod.downfall.bosses;

import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.downfall.charbosses.relics.CBR_FossilizedHelix;
import gkmasmod.downfall.charbosses.relics.CBR_Sozu;
import gkmasmod.downfall.charbosses.relics.CBR_ThreadAndNeedle;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.*;
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.relics.*;

import java.util.ArrayList;
import java.util.HashMap;

public class BossConfig_amao1 extends AbstractBossDeckArchetype {

    public BossConfig_amao1() {
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
        addRelic(new CBR_ThreadAndNeedle());
        addRelic(new CBR_FossilizedHelix());
        addRelic(new CBR_GentlemanHandkerchief());
        addRelic(new CBR_DearLittlePrince());
        addRelic(new CBR_InnerLightEarrings());
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
                    addToList(cardsList, new ENLoveMyselfCool(),true);
                    addToList(cardsList, new ENSteadyWill(),extraUpgrades);
                    addToList(cardsList, new ENEndlessApplause(),extraUpgrades);
                    addToList(cardsList, new ENAuthenticity(),extraUpgrades);
                    addToList(cardsList, new ENBaseAppeal());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENLoveMyselfCute(),true);
                    addToList(cardsList, new ENStartSignal());
                    addToList(cardsList, new ENDressedUpInStyle(),extraUpgrades);
                    addToList(cardsList, new ENLeap(),extraUpgrades);
                    addToList(cardsList, new ENBaseAppeal());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENLoveMyselfCool(),true);
                    addToList(cardsList, new ENCharmPerformance(),extraUpgrades);
                    addToList(cardsList, new ENNationalIdol(),extraUpgrades);
                    addToList(cardsList, new ENAchievement());
                    addToList(cardsList, new ENBaseAppeal());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENLoveMyselfCute(),true);
                    addToList(cardsList, new ENExistence(),extraUpgrades);
                    addToList(cardsList, new ENEncoreCall(),extraUpgrades);
                    addToList(cardsList, new ENPopPhrase());
                    addToList(cardsList, new ENBaseAppeal());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENLoveMyselfCool(),true);
                    addToList(cardsList, new ENExistence(),extraUpgrades);
                    addToList(cardsList, new ENEncoreCall(),extraUpgrades);
                    addToList(cardsList, new ENBaseAppeal());
                    addToList(cardsList, new ENBasePose());
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