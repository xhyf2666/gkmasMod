package gkmasmod.downfall.charbosses.cards.purple;

import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnWeave extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Weave";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Weave");
    }

    public EnWeave() {
        super(ID, cardStrings.NAME, "purple/attack/weave", 0, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.BASIC, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 4;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnWeave();
    }
}
