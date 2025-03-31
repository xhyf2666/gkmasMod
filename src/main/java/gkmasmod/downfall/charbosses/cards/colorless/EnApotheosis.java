package gkmasmod.downfall.charbosses.cards.colorless;

import gkmasmod.downfall.charbosses.actions.unique.EnemyApotheosisAction;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnApotheosis extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Apotheosis";
    private static final CardStrings cardStrings;

    public EnApotheosis() {
        super(ID, cardStrings.NAME, "colorless/skill/apotheosis", 2, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.RARE, CardTarget.NONE, AbstractMonster.Intent.BUFF);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new EnemyApotheosisAction());
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 100;
    }

    public AbstractCard makeCopy() {
        return new EnApotheosis();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Apotheosis");
    }
}
