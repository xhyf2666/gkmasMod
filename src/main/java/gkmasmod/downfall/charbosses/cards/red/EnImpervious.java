package gkmasmod.downfall.charbosses.cards.red;

import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class EnImpervious extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Impervious";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Impervious");
    }

    public EnImpervious() {
        super(ID, EnImpervious.cardStrings.NAME, "red/skill/impervious", 2, EnImpervious.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.DEFEND);
        this.baseBlock = 30;
        this.exhaust = true;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(10);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnImpervious();
    }
}
