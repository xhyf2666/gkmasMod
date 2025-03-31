package gkmasmod.downfall.charbosses.cards.green;

import gkmasmod.downfall.charbosses.actions.unique.EnemyMalaiseAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.downfall.charbosses.ui.EnemyEnergyPanel;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Malaise;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnMalaise extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Bane";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(Malaise.ID);
    }

    public EnMalaise() {
        super(ID, EnMalaise.cardStrings.NAME, "green/skill/malaise", -1, EnMalaise.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.RARE, CardTarget.ENEMY, AbstractMonster.Intent.STRONG_DEBUFF);
        this.exhaust = true;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new EnemyMalaiseAction((AbstractCharBoss) m, this.upgraded, this.freeToPlayOnce, EnemyEnergyPanel.totalCount));// 31
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 100;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            rawDescription = EnMalaise.cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnMalaise();
    }
}
