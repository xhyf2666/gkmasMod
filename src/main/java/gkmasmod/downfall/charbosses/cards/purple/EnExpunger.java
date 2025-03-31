package gkmasmod.downfall.charbosses.cards.purple;

import gkmasmod.downfall.charbosses.actions.unique.EnemyExpungeVFXAction;
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

public class EnExpunger extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Expunger";
    private static final CardStrings cardStrings;

    public EnExpunger() {
        super(ID, cardStrings.NAME, "colorless/attack/expunger", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 9;
        this.intentMultiAmt = 3;
        this.baseMagicNumber = magicNumber = 3;
        this.isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        this.addToBot(new EnemyExpungeVFXAction(p));
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        this.addToBot(new EnemyExpungeVFXAction(p));
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        this.addToBot(new EnemyExpungeVFXAction(p));
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
    }

    public AbstractCard makeCopy() {
        return new EnExpunger();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(6);
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() * 2;
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Expunger");
    }
}
