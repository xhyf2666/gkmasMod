package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.actions.DexterityPowerDamageAction;
import gkmasmod.actions.ModifyDamageAction;

public class DexterityRateAttackCustom extends AbstractCardCustomEffect {

    public static String growID = "DexterityRateAttackCustom";
    public AbstractCard.CardTarget originalTarget;

    public DexterityRateAttackCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " NL " + String.format(CardCrawlGame.languagePack.getUIString("customEffect:DexterityRateAttackCustom").TEXT[0],this.amount);
    }

    public void onInitialApplication(AbstractCard card) {
        this.originalTarget = card.target;
        if (card.target != AbstractCard.CardTarget.ENEMY)
            card.target = AbstractCard.CardTarget.SELF_AND_ENEMY;
    }

    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new DexterityPowerDamageAction(this.amount*1.0F/100, 0,AbstractDungeon.player, target, card));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DexterityRateAttackCustom(this.amount);
    }

}
