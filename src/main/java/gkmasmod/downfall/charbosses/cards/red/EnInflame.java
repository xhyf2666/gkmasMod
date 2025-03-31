package gkmasmod.downfall.charbosses.cards.red;

import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;


import java.util.ArrayList;

public class EnInflame extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Inflame";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Inflame");
    }

    public EnInflame() {
        super("Inflame", EnInflame.cardStrings.NAME, "red/power/inflame", 1, EnInflame.cardStrings.DESCRIPTION, CardType.POWER, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;

    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new VFXAction(m, new InflameEffect(m), 1.0f));
        this.addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnInflame();
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 50;
    }

}
