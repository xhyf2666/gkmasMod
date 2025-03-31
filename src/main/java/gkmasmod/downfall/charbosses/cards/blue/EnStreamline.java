package gkmasmod.downfall.charbosses.cards.blue;

import gkmasmod.downfall.charbosses.actions.common.EnemyReduceCostAction;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnStreamline extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Streamline";
    private static final CardStrings cardStrings;

    public EnStreamline() {
        super(ID, cardStrings.NAME, "blue/attack/streamline", 2, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 15;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

        this.addToBot(new EnemyReduceCostAction(this));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
            this.initializeDescription();
        }

    }

    public AbstractCard makeCopy() {
        return new EnStreamline();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Streamline");
    }
}