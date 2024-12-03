package gkmasmod.downfall.bosses;

import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.cards.free.IdolDeclaration;
import gkmasmod.downfall.cards.free.ENBaseAppeal;
import gkmasmod.downfall.cards.free.ENBasePose;
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.relics.CBR_Courier;
import gkmasmod.downfall.relics.CBR_MawBank;
import gkmasmod.downfall.relics.CBR_UltimateSourceOfHappiness;

import java.util.ArrayList;

public class BossConfig_fktn2 extends AbstractBossDeckArchetype {

    public BossConfig_fktn2() {
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
        addRelic(new CBR_MawBank());
        addRelic(new CBR_Vajra());
        addRelic(new CBR_Courier());
        addRelic(new CBR_Ectoplasm());
        addRelic(new CBR_UltimateSourceOfHappiness());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList,new ENSteadyWill(),extraUpgrades);
                    addToList(cardsList, new ENTopEntertainment(),extraUpgrades);
                    addToList(cardsList, new ENTenThousandVolts(),extraUpgrades);
                    addToList(cardsList, new ENBaseAppeal());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENEndlessApplause());
                    addToList(cardsList, new ENStartSignal(),true);
                    addToList(cardsList, new ENExistence(),true);
                    addToList(cardsList, new ENImprovise());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList,new IdolDeclaration(),true);
                    addToList(cardsList, new ENFullAdrenaline(),extraUpgrades);
                    addToList(cardsList, new ENWarmUp(),extraUpgrades);
                    addToList(cardsList, new ENAchievement());
                    addToList(cardsList, new ENBaseAppeal());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENExistence(),true);
                    addToList(cardsList, new ENEncoreCall(),extraUpgrades);
                    addToList(cardsList, new ENTenThousandVolts(),extraUpgrades);
                    addToList(cardsList, new ENBaseAppeal());
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