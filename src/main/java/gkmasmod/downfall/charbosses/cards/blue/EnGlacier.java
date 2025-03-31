package gkmasmod.downfall.charbosses.cards.blue;

import gkmasmod.downfall.charbosses.actions.orb.EnemyChannelAction;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.downfall.charbosses.orbs.EnemyFrost;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnGlacier extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Glacier";
    private static final CardStrings cardStrings;

    public EnGlacier() {
        super(ID, cardStrings.NAME, "blue/skill/glacier", 2, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND_BUFF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 2;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.baseBlock = 7;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));

        for (int i = 0; i < this.magicNumber; ++i) {
            this.addToBot(new EnemyChannelAction(new EnemyFrost()));
        }

    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() + 10;
    }

    public AbstractCard makeCopy() {
        return new EnGlacier();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Glacier");
    }
}
