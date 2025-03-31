package gkmasmod.downfall.charbosses.cards.purple;

import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.downfall.charbosses.powers.cardpowers.EnemyWaveOfTheHandPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnWaveOfTheHand extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:SandsOfTime";
    private static final CardStrings cardStrings;

    public EnWaveOfTheHand() {
        super("WaveOfTheHand", cardStrings.NAME, "purple/skill/wave_of_the_hand", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new EnemyWaveOfTheHandPower(m, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 30;
    }

    public AbstractCard makeCopy() {
        return new EnWaveOfTheHand();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("WaveOfTheHand");
    }
}
