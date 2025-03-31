package gkmasmod.downfall.bosses;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.anomaly.*;
import gkmasmod.downfall.cards.free.*;
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.*;
import gkmasmod.downfall.relics.*;

import java.util.ArrayList;

public class BossConfig_jsna1 extends AbstractBossDeckArchetype {

    public BossConfig_jsna1() {
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
        addRelic(new CBR_BustedCrown());
        addRelic(new CBR_ProducerPhone());
        addRelic(new CBR_Girya(3));
        addRelic(new CBR_MembershipCard());
        addRelic(new CBR_AbsoluteNewSelf());
        if(AbstractDungeon.ascensionLevel >= 10)
            addRelic(new CBR_Sozu());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        AbstractCharBoss.boss.drawPile.clear();
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENProduceCompetitorGoodTune(),true);
                    addToList(cardsList, new ENMayRain());
                    addToList(cardsList, new ENMadeOneForYou(),true);
                    addToList(cardsList, new ENStepOnStage(),true);
                    addToList(cardsList, new ENEndurance());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENProduceCompetitorGoodTune(),true);
                    addToList(cardsList, new ENKawaiiKawaiiKawaii());
                    addToList(cardsList, new ENComprehensiveArt(1));
                    addToList(cardsList, new ENEndurance());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENProduceCompetitorGoodTune(),true);
                    addToList(cardsList, new ENComprehensiveArt(4),true);
                    addToList(cardsList, new ENEndurance(),true);
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