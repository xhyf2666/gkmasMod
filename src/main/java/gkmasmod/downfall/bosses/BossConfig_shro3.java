package gkmasmod.downfall.bosses;

import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.ENBasePose;
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.relics.*;

import java.util.ArrayList;

public class BossConfig_shro3 extends AbstractBossDeckArchetype {

    public BossConfig_shro3() {
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
        addRelic(new CBR_Sozu());
        addRelic(new CBR_Shuriken());
        addRelic(new CBR_RedSkull());
        addRelic(new CBR_NaughtyPuppet());
        addRelic(new CBR_LizardTail());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList,new ENMakeExactSignboard(),extraUpgrades);
                    addToList(cardsList, new ENInnocence(),extraUpgrades);
                    addToList(cardsList, new ENWonderfulPerformance(),extraUpgrades);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENWishPower(),extraUpgrades);
                    addToList(cardsList, new ENPerformancePlan(),extraUpgrades);
                    addToList(cardsList, new ENCharmGaze());
                    addToList(cardsList, new ENLeap(),extraUpgrades);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENNationalIdol(), extraUpgrades);
                    addToList(cardsList, new ENLeap(),extraUpgrades);
                    addToList(cardsList, new ENStartDash());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENExistence());
                    addToList(cardsList, new ENEncoreCall());
                    addToList(cardsList, new ENAwakening(),extraUpgrades);
                    addToList(cardsList, new ENBasePose(),extraUpgrades);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENExistence());
                    addToList(cardsList, new ENEncoreCall());
                    addToList(cardsList, new ENAwakening(),extraUpgrades);
                    addToList(cardsList, new ENBaseBehave(),extraUpgrades);
                    turn=0;
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}