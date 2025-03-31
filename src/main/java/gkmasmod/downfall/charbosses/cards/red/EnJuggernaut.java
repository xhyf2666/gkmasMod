package gkmasmod.downfall.charbosses.cards.red;

import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.downfall.charbosses.powers.cardpowers.EnemyJuggernautPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


import java.util.ArrayList;

public class EnJuggernaut extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Juggernaut";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Juggernaut");
    }

    public EnJuggernaut() {
        super(ID, EnJuggernaut.cardStrings.NAME, "red/power/juggernaut", 2, EnJuggernaut.cardStrings.DESCRIPTION, CardType.POWER, CardColor.RED, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;

    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new EnemyJuggernautPower(m, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 50;
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnJuggernaut();
    }
}
