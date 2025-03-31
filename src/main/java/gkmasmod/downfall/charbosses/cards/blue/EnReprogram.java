package gkmasmod.downfall.charbosses.cards.blue;

import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class EnReprogram extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Reprogram";
    private static final CardStrings cardStrings;

    public EnReprogram() {
        super("Reprogram", cardStrings.NAME, "blue/skill/reprogram", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.NONE, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        strengthGeneratedIfPlayed = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new FocusPower(m, -this.magicNumber), -this.magicNumber));
        this.addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, this.magicNumber), 1));
        this.addToBot(new ApplyPowerAction(m, m, new DexterityPower(m, this.magicNumber), 1));
    }

    public AbstractCard makeCopy() {
        return new EnReprogram();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Reprogram");
    }
}