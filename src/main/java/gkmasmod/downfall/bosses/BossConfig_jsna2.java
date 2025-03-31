package gkmasmod.downfall.bosses;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.anomaly.*;
import gkmasmod.downfall.cards.free.*;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.CBR_BustedCrown;
import gkmasmod.downfall.charbosses.relics.CBR_Girya;
import gkmasmod.downfall.charbosses.relics.CBR_Sozu;
import gkmasmod.downfall.relics.CBR_AbsoluteNewSelf;
import gkmasmod.downfall.relics.CBR_EveryoneDream;
import gkmasmod.downfall.relics.CBR_MembershipCard;
import gkmasmod.downfall.relics.CBR_ProducerPhone;
import gkmasmod.powers.*;

import java.util.ArrayList;

public class BossConfig_jsna2 extends AbstractBossDeckArchetype {

    public BossConfig_jsna2() {
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
        addRelic(new CBR_EveryoneDream());
        AbstractCreature p = AbstractCharBoss.boss;
        if(AbstractDungeon.ascensionLevel >= 10)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TopWisdomPlusPower(p, 1), 1));
        if(AbstractDungeon.ascensionLevel >= 15) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FullPowerValue(p, 9), 9));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FullPowerValue(p, 5), 5));
        }
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENProduceCompetitorGoodTune(),true);
                    addToList(cardsList, new ENDetermination(),extraUpgrades);
                    addToList(cardsList, new ENKingAppear(),true);
                    addToList(cardsList, new ENEyesOfTheScenery(),true);
                    addToList(cardsList, new ENIdolDeclaration());
                    addToList(cardsList, new ENProducingIsChallenging(),true);
                    addToList(cardsList, new ENTakeFlight(),true);
                    addToList(cardsList, new ENBasePose(),true);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENProduceCompetitorGoodTune(),true);
                    addToList(cardsList, new ENAlternatives(),true);
                    if(AbstractCharBoss.boss.drawPile.size()>0)
                    {
                        AbstractCard tmp = AbstractCharBoss.boss.drawPile.getBottomCard();
                        cardsList.add(tmp);
                        AbstractCharBoss.boss.drawPile.removeCard(tmp);
                    }
                    if(AbstractCharBoss.boss.drawPile.size()>0)
                    {
                        AbstractCard tmp = AbstractCharBoss.boss.drawPile.getBottomCard();
                        cardsList.add(tmp);
                        AbstractCharBoss.boss.drawPile.removeCard(tmp);
                    }
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENProduceCompetitorGoodTune(),true);
                    if(AbstractCharBoss.boss.drawPile.size()>0)
                    {
                        AbstractCard tmp = AbstractCharBoss.boss.drawPile.getBottomCard();
                        cardsList.add(tmp);
                        AbstractCharBoss.boss.drawPile.removeCard(tmp);
                    }
                    if(AbstractCharBoss.boss.drawPile.size()>0)
                    {
                        AbstractCard tmp = AbstractCharBoss.boss.drawPile.getBottomCard();
                        cardsList.add(tmp);
                        AbstractCharBoss.boss.drawPile.removeCard(tmp);
                    }
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