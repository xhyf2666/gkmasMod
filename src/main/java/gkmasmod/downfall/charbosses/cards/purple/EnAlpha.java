package gkmasmod.downfall.charbosses.cards.purple;

import gkmasmod.downfall.charbosses.actions.common.EnemyMakeTempCardInDrawPileAction;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnAlpha extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Alpha";
    private static final CardStrings cardStrings;
    private boolean usedOnce;

    public EnAlpha() {
        super("Alpha", cardStrings.NAME, "purple/skill/alpha", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.RARE, CardTarget.NONE, AbstractMonster.Intent.MAGIC);
        this.exhaust = true;
    }

    public AbstractCard makeCopy() {
        return new EnAlpha();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isInnate = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (usedOnce) {
            this.addToBot(new EnemyMakeTempCardInDrawPileAction(new EnBeta(), 1, true, true));
        } else {
            this.usedOnce = true;
        }
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 100;
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Alpha");
    }
}
