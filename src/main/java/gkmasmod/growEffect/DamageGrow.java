package gkmasmod.growEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DamageGrow extends AbstractGrowEffect {

    public static String growID = "DamageGrow";

    public DamageGrow(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " NL " + String.format(CardCrawlGame.languagePack.getUIString("growEffect:DamageGrow").TEXT[0], this.amount);
    }

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage + this.amount;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DamageGrow(this.amount);
    }

}
