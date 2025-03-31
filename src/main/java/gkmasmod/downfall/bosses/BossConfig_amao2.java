package gkmasmod.downfall.bosses;

import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.downfall.cards.anomaly.*;
import gkmasmod.downfall.cards.free.*;
import gkmasmod.downfall.charbosses.actions.common.EnemyGainEnergyAction;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.CBR_BustedCrown;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.charbosses.relics.CBR_Lantern;
import gkmasmod.downfall.relics.*;
import gkmasmod.powers.*;

import java.util.ArrayList;

public class BossConfig_amao2 extends AbstractBossDeckArchetype {

    public BossConfig_amao2() {
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
        addRelic(new CBR_BustedCrown());
        addRelic(new CBR_LastSummerMemory());
        addRelic(new CBR_GentlemanHandkerchief());
        addRelic(new CBR_DearLittlePrince());
        addRelic(new CBR_InnerLightEarrings());
        addRelic(new CBR_MyPart());
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NegativeNotPower(p, 2), 2));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 4), 4));
        if(AbstractDungeon.ascensionLevel >= 5)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new InnocencePower(p, 1), 1));
        if(AbstractDungeon.ascensionLevel >= 10)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WishPowerPower(p, 2), 2));
        if(AbstractDungeon.ascensionLevel >= 15) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TopEntertainmentPlusPower(p, 1), 1));
        }
        AbstractDungeon.actionManager.addToBottom(new EnemyGainEnergyAction(1));

    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENLoveMyselfCool(),true);
                    addToList(cardsList, new ENGetAnswer(),true);
                    addToList(cardsList, new ENGradualDisappearance());
                    addToList(cardsList, new ENDetermination(),AbstractDungeon.ascensionLevel >= 5);
                    addToList(cardsList, new ENNationalIdol(),extraUpgrades);
                    addToList(cardsList, new ENBecomeIdol(),true);
                    addToList(cardsList, new ENAuthenticity(),extraUpgrades);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
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
                    addToList(cardsList, new ENIdolDeclaration(),true);
                    addToList(cardsList, new ENHeartAndSoul(),true);
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}