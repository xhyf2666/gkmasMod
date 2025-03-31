package gkmasmod.downfall.charbosses.cards.blue;

import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BiasPower;
import com.megacrit.cardcrawl.powers.FocusPower;

import java.util.ArrayList;

public class EnBiasedCognition extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:BiasedCognition";
    private static final CardStrings cardStrings;

    public EnBiasedCognition() {
        super(ID, cardStrings.NAME, "blue/power/biased_cognition", 1, cardStrings.DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
        alwaysDisplayText = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        this.addToBot(new ApplyPowerAction(m, m, new FocusPower(m, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(m, m, new BiasPower(m, 1), 1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 20;
    }

    public AbstractCard makeCopy() {
        return new EnBiasedCognition();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Biased Cognition");
    }
}
