package gkmasmod.downfall.bosses;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.downfall.cards.free.ENBaseAppeal;
import gkmasmod.downfall.cards.free.ENProduceCompetitorGreatGoodTune;
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.cards.red.EnLimitBreak;
import gkmasmod.downfall.charbosses.relics.CBR_FusionHammer;
import gkmasmod.downfall.charbosses.relics.CBR_Girya;
import gkmasmod.downfall.charbosses.relics.CBR_RedSkull;
import gkmasmod.downfall.relics.*;
import gkmasmod.powers.*;

import java.util.ArrayList;

public class BossConfig_jsna3 extends AbstractBossDeckArchetype {

    public BossConfig_jsna3() {
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
        addRelic(new CBR_Girya(4));
        addRelic(new CBR_ProducerPhone());
        addRelic(new CBR_RedSkull());
        addRelic(new CBR_FusionHammer());
        addRelic(new CBR_PastLittleStar());
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NegativeNotPower(p, 3), 3));
        if(AbstractDungeon.ascensionLevel >= 5)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 4), 4));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new InnocencePower(p, 1), 1));
        if(AbstractDungeon.ascensionLevel >= 10)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WishPowerPower(p, 2), 2));
        if(AbstractDungeon.ascensionLevel>=15)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TopEntertainmentPlusPower(p, 1), 1));
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        AbstractCharBoss.boss.drawPile.clear();
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENProduceCompetitorGreatGoodTune(),true);
                    addToList(cardsList, new ENNationalIdol(), extraUpgrades);
                    addToList(cardsList, new EnLimitBreak(),true);
                    addToList(cardsList, new ENEncoreCall(), extraUpgrades);
                    addToList(cardsList, new ENDreamStillContinue(), true);
                    addToList(cardsList, new ENServiceSpirit(), true);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENProduceCompetitorGreatGoodTune(),true);
                    addToList(cardsList, new ENEncoreCall(), extraUpgrades);
                    addToList(cardsList, new ENDreamStillContinue(), true);
                    addToList(cardsList, new ENServiceSpirit(), true);
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}