package gkmasmod.downfall.charbosses.cards.purple;

import gkmasmod.downfall.charbosses.actions.common.EnemyNotStanceCheckAction;
import gkmasmod.downfall.charbosses.actions.unique.EnemyChangeStanceAction;
import gkmasmod.downfall.charbosses.stances.AbstractEnemyStance;
import gkmasmod.downfall.charbosses.stances.EnNeutralStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.EmptyStanceEffect;

public class EnEmptyFist extends AbstractStanceChangeCard {
    public static final String ID = "downfall_Charboss:EmptyFist";
    private static final CardStrings cardStrings;

    public EnEmptyFist() {
        super(ID, cardStrings.NAME, "purple/attack/empty_fist", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 9;
        this.tags.add(CardTags.EMPTY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        this.addToBot(new EnemyNotStanceCheckAction(new VFXAction(new EmptyStanceEffect(m.hb.cX, m.hb.cY), 0.1F)));
        this.addToBot(new EnemyChangeStanceAction("Neutral"));
    }

    public AbstractCard makeCopy() {
        return new EnEmptyFist();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("EmptyFist");
    }

    @Override
    public AbstractEnemyStance changeStanceForIntentCalc(AbstractEnemyStance previousStance) {
        return new EnNeutralStance();
    }
}
