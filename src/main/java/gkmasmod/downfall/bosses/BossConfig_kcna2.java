package gkmasmod.downfall.bosses;

import com.megacrit.cardcrawl.powers.StrengthPower;
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
import gkmasmod.downfall.relics.CBR_HeartFlutteringCup;
import gkmasmod.downfall.relics.CBR_OldCoin;
import gkmasmod.powers.AngelAndDemonPlusPower;
import gkmasmod.powers.InnocencePower;
import gkmasmod.powers.NegativeNotPower;
import gkmasmod.powers.TopEntertainmentPlusPower;

import java.util.ArrayList;

public class BossConfig_kcna2 extends AbstractBossDeckArchetype {

    public BossConfig_kcna2() {
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
        addRelic(new CBR_OldCoin());
        addRelic(new CBR_OldCoin());
        addRelic(new CBR_OldCoin());
        addRelic(new CBR_Sozu());
        addRelic(new CBR_ArtOfWar());
        addRelic(new CBR_HeartFlutteringCup());
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NegativeNotPower(p, 2), 2));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new InnocencePower(p, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 3), 3));
        if(AbstractDungeon.ascensionLevel >= 10)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AngelAndDemonPlusPower(p)));
        if(AbstractDungeon.ascensionLevel >= 15)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TopEntertainmentPlusPower(p,1),1));
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENWelcomeToTeaParty(),extraUpgrades);
                    addToList(cardsList, new ENImprovise());
                    addToList(cardsList, new ENWantToGoThere(),true);
                    addToList(cardsList, new ENEncoreCall(),extraUpgrades);
                    addToList(cardsList, new ENSpecialTreasure(),true);
                    addToList(cardsList, new ENTriggerRelic());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENEncoreCall(),extraUpgrades);
                    addToList(cardsList, new ENWarmCare());
                    addToList(cardsList, new ENBaseAppeal());
                    addToList(cardsList, new ENTryError());
                    addToList(cardsList, new ENSpecialTreasure(),true);
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}