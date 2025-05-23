package gkmasmod.downfall.bosses;

import com.megacrit.cardcrawl.powers.MetallicizePower;
import gkmasmod.downfall.cards.free.ENGodArrival;
import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.cards.red.EnBodySlam;
import gkmasmod.downfall.charbosses.cards.red.EnEntrench;
import gkmasmod.downfall.charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.ENBasePerform;
import gkmasmod.downfall.cards.free.ENIdolSoul;
import gkmasmod.downfall.cards.logic.*;
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.relics.CBR_PrismaticShard;
import gkmasmod.downfall.relics.CBR_RollingSourceOfEnergy;
import gkmasmod.powers.AngelAndDemonPlusPower;
import gkmasmod.powers.NegativeNotPower;
import gkmasmod.powers.WakuWakuPower;

import java.util.ArrayList;

public class BossConfig_hume3 extends AbstractBossDeckArchetype {

    public BossConfig_hume3() {
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
        addRelic(new CBR_IncenseBurner(4));
        addRelic(new CBR_CaptainsWheel());
        addRelic(new CBR_OddlySmoothStone());
        addRelic(new CBR_PhilosopherStone());
        addRelic(new CBR_PrismaticShard());
        addRelic(new CBR_RollingSourceOfEnergy());
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NegativeNotPower(p, 3), 3));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WakuWakuPower(p,2),2));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AngelAndDemonPlusPower(p)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MetallicizePower(p,20),20));
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENGodArrival(),true);
                    addToList(cardsList, new ENBeforeThePerformance(),extraUpgrades);
                    addToList(cardsList, new ENIdolSoul(),extraUpgrades);
                    addToList(cardsList, new ENIamBigStar(),true);
                    addToList(cardsList, new ENSunbathing(),extraUpgrades);
                    addToList(cardsList, new ENImaginaryTraining(),extraUpgrades);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENUnstoppableThoughts(), extraUpgrades);
                    addToList(cardsList, new ENBigOnigiri(), extraUpgrades);
                    addToList(cardsList, new EnEntrench(),extraUpgrades);
                    addToList(cardsList, new EnBodySlam(),true);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENBasePerform());
                    addToList(cardsList, new EnEntrench(),extraUpgrades);
                    addToList(cardsList, new EnBodySlam(),true);
                    addToList(cardsList, new ENOverwhelmingNumbers(),true);
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
    }
}