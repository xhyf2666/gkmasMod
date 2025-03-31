package gkmasmod.downfall.charbosses.cards.colorless;

import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.downfall.charbosses.powers.cardpowers.EnemyMagnetismPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnMagnetism extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Magnetism";
    private static final CardStrings cardStrings;

    public EnMagnetism() {
        super(ID, cardStrings.NAME, "colorless/power/magnetism", 2, cardStrings.DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new EnemyMagnetismPower(m, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 20;
    }

    public AbstractCard makeCopy() {
        return new EnMagnetism();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Magnetism");
    }
}
