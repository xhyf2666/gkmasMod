package gkmasmod.downfall.charbosses.cards.purple;

import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.downfall.charbosses.powers.cardpowers.EnemyEstablishmentPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnEstablishment extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Establishment";
    private static final CardStrings cardStrings;

    public EnEstablishment() {
        super(ID, cardStrings.NAME, "purple/power/establishment", 1, cardStrings.DESCRIPTION, CardType.POWER, CardColor.PURPLE, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new EnemyEstablishmentPower(m, this.magicNumber), this.magicNumber));
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 100;
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isInnate = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    public AbstractCard makeCopy() {
        return new EnEstablishment();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Establishment");
    }
}
