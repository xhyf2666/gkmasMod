package gkmasmod.downfall.charbosses.cards.colorless;

import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.downfall.charbosses.powers.cardpowers.EnemyTheBombPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnTheBomb extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:The Bomb";
    private static final CardStrings cardStrings;

    public EnTheBomb() {
        super(ID, cardStrings.NAME, "colorless/skill/the_bomb", 2, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 40;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new EnemyTheBombPower(m, 3, this.magicNumber), 2));
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 30;
    }

    public AbstractCard makeCopy() {
        return new EnTheBomb();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(10);
        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("The Bomb");
    }
}
