package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DamageCustom extends AbstractCardCustomEffect {

    public static String growID = "DamageCustom";

    private int originBaseDamage;

    public AbstractCard.CardTarget originalTarget;

    public DamageCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
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
    public void onRemove(AbstractCard card) {
        card.target = this.originalTarget;
    }


    @Override
    public AbstractCardModifier makeCopy() {
        return new DamageCustom(this.amount);
    }

}
