package gkmasmod.downfall.charbosses.cards.green;

import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.downfall.charbosses.powers.cardpowers.EnemyAfterImagePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.AfterImage;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnAfterImage extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:After Image";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(AfterImage.ID);
    }

    public EnAfterImage() {
        super(ID, EnAfterImage.cardStrings.NAME, "green/power/after_image", 1, EnAfterImage.cardStrings.DESCRIPTION, CardType.POWER, CardColor.GREEN, CardRarity.RARE, CardTarget.ENEMY, AbstractMonster.Intent.BUFF);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, m, new EnemyAfterImagePower(m, 1), 1));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            isInnate = true;
            rawDescription = EnAfterImage.cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 30;
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnAfterImage();
    }
}
