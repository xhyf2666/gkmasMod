package gkmasmod.downfall.charbosses.cards.blue;

import gkmasmod.downfall.charbosses.actions.orb.EnemyChannelAction;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.downfall.charbosses.orbs.AbstractEnemyOrb;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnChaos extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Chaos";
    private static final CardStrings cardStrings;

    public EnChaos() {
        super(ID, cardStrings.NAME, "blue/skill/chaos", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            this.addToBot(new EnemyChannelAction(AbstractEnemyOrb.getRandomOrb(true)));
        }

        this.addToBot(new EnemyChannelAction(AbstractEnemyOrb.getRandomOrb(true)));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.showEvokeOrbCount = 2;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 10;
    }

    public AbstractCard makeCopy() {
        return new EnChaos();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Chaos");
    }
}
