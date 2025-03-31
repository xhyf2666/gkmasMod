package gkmasmod.downfall.bosses;

import gkmasmod.downfall.cards.free.ENTriggerRelic;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.CBR_Ectoplasm;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.ENBasePerform;
import gkmasmod.downfall.cards.free.ENFullOfPower;
import gkmasmod.downfall.cards.free.ENSkipWater;
import gkmasmod.downfall.cards.logic.*;
import gkmasmod.downfall.relics.*;
import gkmasmod.powers.*;

import java.util.ArrayList;

public class BossConfig_fktn3 extends AbstractBossDeckArchetype {

    public BossConfig_fktn3() {
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
        addRelic(new CBR_Ectoplasm());
        addRelic(new CBR_FreeLoveMax());
        addRelic(new CBR_FavoriteSneakers());
        addRelic(new CBR_PigDreamPiggyBank());
        addRelic(new CBR_CracklingSparkler());
        addRelic(new CBR_OnlyMyFirstStar());

        AbstractCreature p = AbstractCharBoss.boss;
        if(AbstractDungeon.ascensionLevel >= 15){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new KawaiiPower(p,2),2));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new GoodImpression(p,20),20));
        }
        else
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new KawaiiPower(p,1),1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NegativeNotPower(p, 3), 3));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MyPrideBigSisterPower(p,180)));
        if(AbstractDungeon.ascensionLevel >= 10)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new HappyChristmasPower(p,2),2));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IsENotAPower(p,6),6));
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENSummerEveningSparklers(),true);
                    addToList(cardsList, new ENUnstoppableThoughts(),extraUpgrades);
                    addToList(cardsList, new ENSmile(),true);
                    addToList(cardsList, new ENHandwrittenLetter(),extraUpgrades);
                    addToList(cardsList, new ENWorldFirstCute(),true);
                    addToList(cardsList, new ENPromiseThatTime(),true);
                    addToList(cardsList, new ENTriggerRelic());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENHardStretching(), extraUpgrades);
                    addToList(cardsList, new ENFlowerBouquet(),extraUpgrades);
                    addToList(cardsList, new ENFullOfPower(),extraUpgrades);
                    addToList(cardsList, new ENHandwrittenLetter(),extraUpgrades);
                    addToList(cardsList, new ENPromiseThatTime(),true);
                    addToList(cardsList, new ENTriggerRelic());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENWakeUp(), extraUpgrades);
                    addToList(cardsList, new ENHardStretching(), extraUpgrades);
                    addToList(cardsList, new ENSkipWater(), extraUpgrades);
                    addToList(cardsList, new ENRestart(),extraUpgrades);
                    addToList(cardsList, new ENPromiseThatTime(),true);
                    addToList(cardsList, new ENLikeEveryone());
                    addToList(cardsList, new ENTriggerRelic());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENYeah(),extraUpgrades);
                    addToList(cardsList, new ENHardStretching(), extraUpgrades);
                    addToList(cardsList, new ENRestart());
                    addToList(cardsList, new ENPromiseThatTime(),extraUpgrades);
                    addToList(cardsList, new ENBasePerform());
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}