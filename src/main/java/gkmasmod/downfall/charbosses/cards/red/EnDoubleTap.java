package gkmasmod.downfall.charbosses.cards.red;

import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.downfall.charbosses.powers.cardpowers.EnemyDoubleTapPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnDoubleTap extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Double Tap";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Double Tap");
    }

    public EnDoubleTap() {
        super(ID, EnDoubleTap.cardStrings.NAME, "red/skill/double_tap", 1, EnDoubleTap.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new EnemyDoubleTapPower(m, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = EnDoubleTap.cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        for (AbstractCard c : hand) {
            if (c.type == CardType.ATTACK) {
                return 20;
            }
        }
        return 0;
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnDoubleTap();
    }
}
