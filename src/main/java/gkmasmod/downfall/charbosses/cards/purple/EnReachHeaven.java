package gkmasmod.downfall.charbosses.cards.purple;

import gkmasmod.downfall.charbosses.actions.common.EnemyMakeTempCardInDrawPileAction;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnReachHeaven extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:ReachHeaven";
    private static final CardStrings cardStrings;
    private boolean usedOnce;

    public EnReachHeaven() {
        super(ID, cardStrings.NAME, "purple/attack/reach_heaven", 2, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK_BUFF);
        this.baseDamage = 10;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        if (usedOnce) {
            this.addToBot(new EnemyMakeTempCardInDrawPileAction(new EnThroughViolence(), 1, true, true));
        } else {
            this.usedOnce = true;
        }
    }

    public AbstractCard makeCopy() {
        return new EnReachHeaven();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() + 15;
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("ReachHeaven");
    }
}
