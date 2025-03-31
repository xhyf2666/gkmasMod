package gkmasmod.downfall.charbosses.cards.green;

import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnDash extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Dash";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Dash");
    }

    public EnDash() {
        super(ID, EnDash.cardStrings.NAME, "green/attack/dash", 2, EnDash.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF_AND_ENEMY, AbstractMonster.Intent.ATTACK_DEFEND);
        this.baseDamage = 10;
        this.baseBlock = 10;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        atb(new GainBlockAction(m, m, block));
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            upgradeBlock(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnDash();
    }
}
