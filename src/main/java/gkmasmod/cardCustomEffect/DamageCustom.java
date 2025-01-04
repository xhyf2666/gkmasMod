package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DamageCustom extends AbstractCardCustomEffect {

    public static String growID = "DamageCustom";

    private int originalDamage;

    public DamageCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        if(card.baseDamage>0)
            return rawDescription;
        return rawDescription + " NL " + CardCrawlGame.languagePack.getUIString("customEffect:DamageCustom").TEXT[0];
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if(damage>0)
            return damage + this.amount;
        else
            return this.amount;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DamageCustom(this.amount);
    }

}
