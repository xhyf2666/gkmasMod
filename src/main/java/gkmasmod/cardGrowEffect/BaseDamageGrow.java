package gkmasmod.cardGrowEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BaseDamageGrow extends AbstractGrowEffect {

    public static String growID = "BaseDamageGrow";

    public AbstractCard.CardTarget originalTarget;

    private int originBaseDamage;

    public BaseDamageGrow(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " " + String.format(CardCrawlGame.languagePack.getUIString("growEffect:BaseDamageGrow").TEXT[0], this.amount);
    }

    public void onInitialApplication(AbstractCard card) {
        this.originBaseDamage = card.baseDamage;
        this.originalTarget = card.target;
        if (card.target != AbstractCard.CardTarget.ENEMY)
            card.target = AbstractCard.CardTarget.SELF_AND_ENEMY;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if(originBaseDamage <=0){
            addToBot(new DamageAction(target, new DamageInfo(target, card.damage, DamageInfo.DamageType.NORMAL)));
        }
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.target = this.originalTarget;
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if(originBaseDamage <=0){
            return this.amount;
        }

        return damage + this.amount;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BaseDamageGrow(this.amount);
    }

}
