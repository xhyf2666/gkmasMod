package gkmasmod.downfall.charbosses.cards.blue;

import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnForceField extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:ForceField";
    private static final CardStrings cardStrings;

    public EnForceField() {
        super("Force Field", cardStrings.NAME, "blue/skill/forcefield", 4, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND);
        this.baseBlock = 12;

    }


    public void triggerOnCardPlayed(AbstractCard c) {
        if (c.type == CardType.POWER) {
            this.updateCost(-1);
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        if (this.cost > 1) return 0;
        else return autoPriority();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(4);
        }

    }

    public AbstractCard makeCopy() {
        return new EnForceField();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Force Field");
    }
}
