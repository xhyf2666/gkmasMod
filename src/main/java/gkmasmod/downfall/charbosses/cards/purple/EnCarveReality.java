package gkmasmod.downfall.charbosses.cards.purple;

import gkmasmod.downfall.charbosses.actions.common.EnemyMakeTempCardInHandAction;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.downfall.charbosses.powers.cardpowers.EnemyMasterRealityPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnCarveReality extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:CarveReality";
    private static final CardStrings cardStrings;
    public boolean willUseSmite;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("CarveReality");
    }

    public EnCarveReality(boolean willUseSmite) {
        super(ID, cardStrings.NAME, "purple/attack/carve_reality", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 6;
        this.willUseSmite = willUseSmite;
        this.cardsToPreview = new EnSmite();
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        this.addToBot(new EnemyMakeTempCardInHandAction(new EnSmite(), 1));
    }

    @Override
    public int customIntentModifiedDamage() {
        int extraDamage = 0;

        if (owner.hasPower(EnemyMasterRealityPower.POWER_ID)) {
            extraDamage = 6;
        }
        return extraDamage;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnCarveReality(willUseSmite);
    }
}
