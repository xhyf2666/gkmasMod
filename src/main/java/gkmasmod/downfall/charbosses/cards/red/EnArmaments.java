package gkmasmod.downfall.charbosses.cards.red;

import gkmasmod.downfall.charbosses.actions.unique.EnemyArmamentsAction;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnArmaments extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Armaments";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Armaments");
    }

    public EnArmaments() {
        super(ID, EnArmaments.cardStrings.NAME, "red/skill/armaments", 1, EnArmaments.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND_BUFF);
        this.baseBlock = 5;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
        this.addToBot(new EnemyArmamentsAction(this.upgraded));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = EnArmaments.cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        int unupgradedCards = 0;
        for (AbstractCard c : hand) {
            if (!c.upgraded) {
                unupgradedCards++;
            }
        }
        if (this.upgraded) {
            return (100 - unupgradedCards * 10);
        } else {
            return (100 - unupgradedCards * 15);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnArmaments();
    }
}
