package gkmasmod.downfall.charbosses.cards.blue;

import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.downfall.charbosses.powers.general.EnemyEnergizedPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnChargeBattery extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:ConserveBattery";
    private static final CardStrings cardStrings;

    public EnChargeBattery() {
        super(ID, cardStrings.NAME, "blue/skill/charge_battery", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND_BUFF);
        this.baseBlock = 7;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
        this.addToBot(new ApplyPowerAction(m, m, new EnemyEnergizedPower(m, 1), 1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() + 3;
    }

    public AbstractCard makeCopy() {
        return new EnChargeBattery();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Conserve Battery");
    }

}
