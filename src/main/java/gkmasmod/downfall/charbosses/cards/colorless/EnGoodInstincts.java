package gkmasmod.downfall.charbosses.cards.colorless;

import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnGoodInstincts extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Good Instincts";
    private static final CardStrings cardStrings;

    public EnGoodInstincts() {
        super(ID, cardStrings.NAME, "colorless/skill/good_instincts", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND);
        this.baseBlock = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
        }

    }

    public AbstractCard makeCopy() {
        return new EnGoodInstincts();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Good Instincts");
    }
}
