package gkmasmod.downfall.bosses;

import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.cards.curses.EnNormality;
import gkmasmod.downfall.charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.*;
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.relics.CBR_SparklingInTheBottle;
import gkmasmod.downfall.relics.CBR_TheDreamSawSomeday;
import gkmasmod.powers.*;

import java.util.ArrayList;

public class BossConfig_kllj2 extends AbstractBossDeckArchetype {

    public BossConfig_kllj2() {
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
        addRelic(new CBR_Boot());
        addRelic(new CBR_VelvetChoker());
        addRelic(new CBR_IceCream());
        addRelic(new CBR_PenNib());
        addRelic(new CBR_TheDreamSawSomeday());
        addRelic(new CBR_SparklingInTheBottle());
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NegativeNotPower(p, 2), 2));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 3), 3));
        if(AbstractDungeon.ascensionLevel >= 10)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new InnocencePower(p, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TheScenerySawSomedayPower(p, 5), 5));
        if(AbstractDungeon.ascensionLevel >= 15)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TopEntertainmentPlusPower(p, 1), 1));
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENGrowthProcess(),true);
                    addToList(cardsList, new ENWarmUp(),true);
                    addToList(cardsList, new ENAwakening(),extraUpgrades);
                    addToList(cardsList, new ENGoWithTheFlow(),extraUpgrades);
                    addToList(cardsList, new ENStartSignal(),extraUpgrades);
                    addToList(cardsList, new ENStartDash(),extraUpgrades);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENIdolDeclaration());
                    addToList(cardsList, new ENAlternatives());
                    addToList(cardsList, new ENTheScenerySawSomeday(),true);
                    addToList(cardsList, new EnNormality());
                    addToList(cardsList, new ENWarmUp(),extraUpgrades);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENEncoreCall(),extraUpgrades);
                    addToList(cardsList, new ENWarmCare());
                    addToList(cardsList, new ENWarmUp(),extraUpgrades);
                    addToList(cardsList, new ENPopPhrase());
                    addToList(cardsList, new ENTryError());
                    addToList(cardsList, new ENStartDash(),extraUpgrades);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENBasePose(), extraUpgrades);
                    addToList(cardsList, new ENHighFive(),extraUpgrades);
                    addToList(cardsList, new ENWarmUp(),extraUpgrades);
                    addToList(cardsList, new ENBaseAppeal());
                    addToList(cardsList, new ENPopPhrase());
                    addToList(cardsList, new ENStartDash(),extraUpgrades);
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