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
import gkmasmod.downfall.relics.CBR_AnimateEquipment;
import gkmasmod.downfall.relics.CBR_FaceTheAwakening;
import gkmasmod.downfall.relics.CBR_RoaringLion;
import gkmasmod.powers.*;

import java.util.ArrayList;

public class BossConfig_hski3 extends AbstractBossDeckArchetype {

    public BossConfig_hski3() {
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
        addRelic(new CBR_RedSkull());
        addRelic(new CBR_FusionHammer());
        addRelic(new CBR_AnimateEquipment());
        addRelic(new CBR_RoaringLion());
        addRelic(new CBR_FaceTheAwakening());
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NegativeNotPower(p, 3), 3));
        if(AbstractDungeon.ascensionLevel >= 5)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TrueLateBloomerPower(p, 2).setMagic2(1), 2));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 4), 4));
        if(AbstractDungeon.ascensionLevel >= 10)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new InnocencePower(p, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EyePowerPower(p, 2), 2));
        if(AbstractDungeon.ascensionLevel >= 15)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TopEntertainmentPlusPower(p, 1), 1));
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENLightGait(),true);
                    addToList(cardsList, new ENNationalIdol(),extraUpgrades);
                    addToList(cardsList, new ENVeryEasy(),true);
                    addToList(cardsList, new ENUntilNowAndFromNow(),true);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENExistence(), extraUpgrades);
                    addToList(cardsList, new ENImprovise());
                    addToList(cardsList, new ENUntilNowAndFromNow(),true);
                    addToList(cardsList, new ENLightGait(),true);
                    addToList(cardsList, new ENBaseAppeal());
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}