package gkmasmod.downfall.bosses;

import gkmasmod.downfall.charbosses.bosses.AbstractBossDeckArchetype;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.CBR_CoffeeDripper;
import gkmasmod.downfall.charbosses.relics.CBR_Girya;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import gkmasmod.downfall.cards.free.ENBaseAppeal;
import gkmasmod.downfall.cards.free.ENIdolSoul;
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.downfall.relics.CBR_EnjoyAfterHotSpring;
import gkmasmod.downfall.relics.CBR_OldCoin;
import gkmasmod.powers.AngelAndDemonPlusPower;
import gkmasmod.powers.ForShiningYouPlusSpPower;
import gkmasmod.powers.InnocencePower;
import gkmasmod.powers.NegativeNotPower;

import java.util.ArrayList;

public class BossConfig_kcna3 extends AbstractBossDeckArchetype {

    public BossConfig_kcna3() {
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
        addRelic(new CBR_OldCoin());
        addRelic(new CBR_OldCoin());
        addRelic(new CBR_OldCoin());
        addRelic(new CBR_CoffeeDripper());
        addRelic(new CBR_Girya(10));
        addRelic(new CBR_EnjoyAfterHotSpring());
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NegativeNotPower(p, 3), 3));
        if(AbstractDungeon.ascensionLevel >= 5)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new InnocencePower(p, 1), 1));
        if(AbstractDungeon.ascensionLevel >= 10)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AngelAndDemonPlusPower(p)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ForShiningYouPlusSpPower(p,1),1));
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 3;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENAQuickSip(),extraUpgrades);
                    addToList(cardsList, new ENIdolSoul(),extraUpgrades);
                    addToList(cardsList, new ENNationalIdol());
                    addToList(cardsList, new ENJustOneMore(),extraUpgrades);
                    addToList(cardsList, new ENBalance(),extraUpgrades);
                    addToList(cardsList, new ENSpecialTreasure(),true);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new ENCharmPerformance(),extraUpgrades);
                    addToList(cardsList, new ENWelcomeToTeaParty(),extraUpgrades);
                    addToList(cardsList, new ENEncoreCall(),extraUpgrades);
                    addToList(cardsList, new ENGonnaTrickYou(),extraUpgrades);
                    addToList(cardsList, new ENStartSignal());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new ENExistence(),extraUpgrades);
                    addToList(cardsList, new ENEncoreCall());
                    addToList(cardsList, new ENAwakening());
                    addToList(cardsList, new ENSpecialTreasure(),true);
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