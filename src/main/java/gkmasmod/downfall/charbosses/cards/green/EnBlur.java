package gkmasmod.downfall.charbosses.cards.green;

import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;

import java.util.ArrayList;

public class EnBlur extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Blur";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Blur");
    }

    public EnBlur() {
        super(ID, EnBlur.cardStrings.NAME, "green/skill/blur", 1, EnBlur.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND_BUFF);
        this.baseBlock = 5;
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() + 3;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        atb(new GainBlockAction(m, m, block));
        atb(new ApplyPowerAction(m, m, new BlurPower(m, 1), 1));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBlock(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnBlur();
    }
}
