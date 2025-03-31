package gkmasmod.downfall.charbosses.cards.blue;

import gkmasmod.downfall.charbosses.actions.common.EnemyGainEnergyAction;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnTurbo extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Turbo";
    private static final CardStrings cardStrings;

    public EnTurbo() {
        super(ID, cardStrings.NAME, "blue/skill/turbo", 0, cardStrings.DESCRIPTION, AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.BLUE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF, AbstractMonster.Intent.BUFF);

        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.energyGeneratedIfPlayed = magicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new EnemyGainEnergyAction(this.magicNumber));
        addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), 1, false, true));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
            this.energyGeneratedIfPlayed = magicNumber;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() * 2;
    }

    public AbstractCard makeCopy() {
        return new EnTurbo();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Turbo");
    }
}