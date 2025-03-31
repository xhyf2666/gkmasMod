package gkmasmod.downfall.bosses;

import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.downfall.cards.free.ENBaseAppeal;
import gkmasmod.downfall.cards.free.ENBasePose;
import gkmasmod.downfall.cards.free.ENIdolDeclaration;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.ENNecessaryContrast;
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.relics.CBR_TowardsAnUnseenWorld;
import gkmasmod.powers.EurekaPower;
import gkmasmod.powers.EyePowerPower;
import gkmasmod.powers.InnocencePower;
import gkmasmod.powers.NegativeNotPower;

import java.util.ArrayList;

public class BossConfig_shro2 extends AbstractBossDeckArchetype {

    public BossConfig_shro2() {
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
        addRelic(new CBR_StoneCalendar());
        addRelic(new CBR_Kunai());
        addRelic(new CBR_VelvetChoker());
        addRelic(new CBR_RedSkull());
        addRelic(new CBR_TowardsAnUnseenWorld());
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EurekaPower(p)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NegativeNotPower(p, 2), 2));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 3), 3));
        if(AbstractDungeon.ascensionLevel >= 10)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new InnocencePower(p, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EyePowerPower(p, 2), 2));
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENIdolDeclaration());
                    addToList(cardsList, new ENNecessaryContrast(),true);
                    addToList(cardsList, new ENWishPower(),extraUpgrades);
                    addToList(cardsList, new ENAchievement(),true);
                    addToList(cardsList, new ENSwayingOnTheBus(),extraUpgrades);
                    addToList(cardsList, new ENStartDash(),true);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENEncoreCall(),true);
                    addToList(cardsList, new ENImprovise(),true);
                    addToList(cardsList, new ENJustOneMore(),extraUpgrades);
                    addToList(cardsList, new ENStartSignal(),extraUpgrades);
                    addToList(cardsList, new ENWarmCare());
                    addToList(cardsList, new ENCharmPerformance(),true);
                    addToList(cardsList, new ENEyePower(),extraUpgrades);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENEncoreCall(),extraUpgrades);
                    addToList(cardsList, new ENWarmCare());
                    addToList(cardsList, new ENAchievement(),extraUpgrades);
                    addToList(cardsList, new ENLeap(),true);
                    addToList(cardsList, new ENBasePose(),true);
                    addToList(cardsList, new ENBaseAppeal(),true);
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}