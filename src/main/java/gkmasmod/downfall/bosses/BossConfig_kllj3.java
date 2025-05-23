package gkmasmod.downfall.bosses;

import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.ENIdolDeclaration;
import gkmasmod.downfall.cards.logic.*;
import gkmasmod.downfall.relics.CBR_BeyondTheSea;
import gkmasmod.downfall.relics.CBR_DreamLifeLog;
import gkmasmod.downfall.relics.CBR_MemoryBot;
import gkmasmod.powers.*;

import java.util.ArrayList;

public class BossConfig_kllj3 extends AbstractBossDeckArchetype {

    public BossConfig_kllj3() {
        super("hski_config3","hski", "focus");
        maxHPModifier += 200;
        actNum = 3;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
    }

    public void initialize() {
        addRelic(new CBR_FusionHammer());
        addRelic(new CBR_Orichalcum());
        addRelic(new CBR_LetterOpener());
        addRelic(new CBR_MemoryBot());
        addRelic(new CBR_DreamLifeLog());
        addRelic(new CBR_BeyondTheSea());
        AbstractCreature p = AbstractCharBoss.boss;
        if(AbstractDungeon.ascensionLevel >= 15){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new GoodImpression(p,30),30));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SSDSecretPower(p,1),1));
        }
        else
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new GoodImpression(p,10),10));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NegativeNotPower(p, 3), 3));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NotAfraidAnymorePower(p,3),3));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RainbowDreamerPower(p,2),2));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IsENotAPower(p,7),7));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TheScenerySawSomedayPower(p, 5), 5));
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENIdolDeclaration(),extraUpgrades);
                    addToList(cardsList, new ENBeforeThePerformance(),extraUpgrades);
                    addToList(cardsList, new ENIamBigStar(),extraUpgrades);
                    addToList(cardsList, new ENUnstoppableThoughts(),extraUpgrades);
                    addToList(cardsList, new ENYoungDream(),true);
                    addToList(cardsList, new ENHappyCurse(), extraUpgrades);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENStarDust(),extraUpgrades);
                    addToList(cardsList, new ENFutureTrajectory());
                    addToList(cardsList, new ENWakeUp(),extraUpgrades);
                    addToList(cardsList, new ENWakeUp(),true);
                    addToList(cardsList, new ENPureWhiteFairy(),extraUpgrades);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENFlowerBouquet(),extraUpgrades);
                    addToList(cardsList, new ENLikeEveryone());
                    addToList(cardsList, new ENWithLove(),true);
                    addToList(cardsList, new ENSparklingConfetti(),true);
                    addToList(cardsList, new ENRestart(),true);
                    addToList(cardsList, new ENSweetWink(),extraUpgrades);
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}