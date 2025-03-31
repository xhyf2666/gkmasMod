package gkmasmod.downfall.charbosses.cards.green;

import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.downfall.charbosses.powers.cardpowers.EnemyThousandCutsPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.AThousandCuts;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnThousandCuts extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Thousand Cuts";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(AThousandCuts.ID);
    }

    public EnThousandCuts() {
        super(ID, EnThousandCuts.cardStrings.NAME, "green/power/a_thousand_cuts", 2, EnThousandCuts.cardStrings.DESCRIPTION, CardType.POWER, CardColor.GREEN, CardRarity.RARE, CardTarget.ENEMY, AbstractMonster.Intent.BUFF);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, m, new EnemyThousandCutsPower(m, magicValue), magicValue));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 25;
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnThousandCuts();
    }
}
