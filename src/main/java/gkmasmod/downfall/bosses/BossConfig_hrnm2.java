package gkmasmod.downfall.bosses;

import com.megacrit.cardcrawl.powers.FocusPower;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.cards.blue.*;
import gkmasmod.downfall.charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.ENBaseAppeal;
import gkmasmod.downfall.cards.free.ENBlueSenpaiHelp;
import gkmasmod.downfall.cards.free.ENIdolSoul;
import gkmasmod.downfall.relics.CBR_CrackedCoreNew;
import gkmasmod.downfall.relics.CBR_ProducerPhone;
import gkmasmod.powers.NegativeNotPower;

import java.util.ArrayList;

public class BossConfig_hrnm2 extends AbstractBossDeckArchetype {

    public BossConfig_hrnm2() {
        super("hski_config3","hski", "focus");
        maxHPModifier += 0;
        actNum = 3;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BarricadePower(p)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FocusPower(p,10),10));
    }

    public void initialize() {
        addRelic(new CBR_PhilosopherStone());
        addRelic(new CBR_DataDisk());
        addRelic(new CBR_MummifiedHand());
        addRelic(new CBR_FusionHammer());
        addRelic(new CBR_ProducerPhone());
        addRelic(new CBR_CrackedCoreNew());
        if(AbstractDungeon.ascensionLevel >= 10)
            addRelic(new CBR_Sozu());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENIdolSoul(),extraUpgrades);
                    addToList(cardsList, new EnBiasedCognition(),extraUpgrades);
                    addToList(cardsList, new EnCapacitor());
                    addToList(cardsList, new EnRainbow(),extraUpgrades);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnCoreSurge(), extraUpgrades);
                    addToList(cardsList, new EnBiasedCognition(),extraUpgrades);
                    addToList(cardsList, new ENBlueSenpaiHelp(),true);
                    addToList(cardsList, new EnDarkness(),true);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new ENBlueSenpaiHelp(), extraUpgrades);
                    addToList(cardsList, new EnChargeBattery(),extraUpgrades);
                    addToList(cardsList, new EnBuffer(),extraUpgrades);
                    addToList(cardsList, new ENBaseAppeal(),extraUpgrades);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnBallLightning(),extraUpgrades);
                    addToList(cardsList, new EnGlacier());
                    addToList(cardsList, new EnRainbow(),true);
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}