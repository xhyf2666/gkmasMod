package gkmasmod.downfall.charbosses.cards.colorless;

import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoBlockPower;

public class EnPanicButton extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:PanicButton";
    private static final CardStrings cardStrings;

    public EnPanicButton() {
        super(ID, cardStrings.NAME, "colorless/skill/panic_button", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND);
        this.baseBlock = 30;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
        this.addToBot(new ApplyPowerAction(m, m, new NoBlockPower(m, this.magicNumber, false), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(10);
        }

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return (!m.hasPower(NoBlockPower.POWER_ID));
    }

    public AbstractCard makeCopy() {
        return new EnPanicButton();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("PanicButton");
    }
}
