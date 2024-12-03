package gkmasmod.downfall.bosses;

import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.CBR_Mango;
import gkmasmod.downfall.charbosses.relics.CBR_MarkOfPain;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.ENCallMeAnyTime;
import gkmasmod.downfall.cards.logic.*;
import gkmasmod.downfall.relics.CBR_ClumsyBat;
import gkmasmod.downfall.relics.CBR_ProducerPhone;
import gkmasmod.downfall.relics.CBR_ThisIsMe;

import java.util.ArrayList;

public class BossConfig_ttmr3 extends AbstractBossDeckArchetype {

    public BossConfig_ttmr3() {
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
        addRelic(new CBR_Mango());
        addRelic(new CBR_Mango());
        addRelic(new CBR_Mango());
        addRelic(new CBR_Mango());
        addRelic(new CBR_Mango());
        addRelic(new CBR_Mango());
        addRelic(new CBR_MarkOfPain());
        addRelic(new CBR_ClumsyBat());
        addRelic(new CBR_ThisIsMe());
        addRelic(new CBR_ProducerPhone());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList,new ENBeforeThePerformance(),extraUpgrades);
                    addToList(cardsList, new ENUnstoppableThoughts(),extraUpgrades);
                    addToList(cardsList, new ENStruggleHandmade(),extraUpgrades);
                    addToList(cardsList, new ENWakuWaku(),extraUpgrades);
                    addToList(cardsList, new ENCallMeAnyTime(),extraUpgrades);
                    addToList(cardsList, new ENIsENotA(), extraUpgrades);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENImaginaryTraining(), true);
                    addToList(cardsList, new ENFlowerBouquet(),extraUpgrades);
                    addToList(cardsList, new ENHardStretching(), true);
                    addToList(cardsList, new ENRainbowDreamer(), true);
                    addToList(cardsList, new ENIamBigStar(),extraUpgrades);
                    addToList(cardsList, new ENBaseAwareness(),extraUpgrades);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENSparklingConfetti(),extraUpgrades);
                    addToList(cardsList, new ENFlowerBouquet(), extraUpgrades);
                    addToList(cardsList, new ENHandwrittenLetter(),extraUpgrades);
                    addToList(cardsList, new ENPromiseThatTime());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENSparklingConfetti(),extraUpgrades);
                    addToList(cardsList, new ENFlowerBouquet(),extraUpgrades);
                    addToList(cardsList, new ENGirlHeart());
                    addToList(cardsList, new ENPromiseThatTime());
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}