package gkmasmod.downfall.bosses;

import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.ENBaseAppeal;
import gkmasmod.downfall.cards.logic.*;
import gkmasmod.downfall.relics.*;
import gkmasmod.powers.*;

import java.util.ArrayList;

public class BossConfig_hrnm3 extends AbstractBossDeckArchetype {

    public BossConfig_hrnm3() {
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
        addRelic(new CBR_OddlySmoothStone());
        addRelic(new CBR_ProducerPhone());
        addRelic(new CBR_FusionHammer());
        addRelic(new CBR_ItsPerfect());
        addRelic(new CBR_ClapClapFan());
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NegativeNotPower(p, 3), 3));
        if(AbstractDungeon.ascensionLevel >= 15){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new GoodImpression(p,20),20));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WakuWakuPower(p,2),2));
        if(AbstractDungeon.ascensionLevel >= 10)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IsENotAPower(p,6),6));
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENUnstoppableThoughts(),extraUpgrades);
                    addToList(cardsList, new ENIamBigStar(),extraUpgrades);
                    addToList(cardsList, new ENFlowerBouquet());
                    addToList(cardsList, new ENRefreshingBreak(),extraUpgrades);
                    addToList(cardsList, new ENHandwrittenLetter(),extraUpgrades);
                    addToList(cardsList, new ENEnjoySummer(),extraUpgrades);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENRefreshingBreak(),extraUpgrades);
                    addToList(cardsList, new ENSeeYouTomorrow(),true);
                    addToList(cardsList, new ENPromiseThatTime(),extraUpgrades);
                    addToList(cardsList, new ENWakeUp(),true);
                    addToList(cardsList, new ENRestart(),extraUpgrades);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENFlowerBouquet(),extraUpgrades);
                    addToList(cardsList, new ENRestart(),extraUpgrades);
                    addToList(cardsList, new ENSweetWink());
                    addToList(cardsList, new ENPromiseThatTime(),extraUpgrades);
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}