package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.actions.DexterityPowerDamageAction;
import gkmasmod.actions.GoodImpressionDamageAction;

public class GoodImpressionRateAttackCustom extends AbstractCardCustomEffect {

    public static String growID = "GoodImpressionRateAttackCustom";
    public AbstractCard.CardTarget originalTarget;

    public GoodImpressionRateAttackCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " NL " + String.format(CardCrawlGame.languagePack.getUIString("customEffect:GoodImpressionRateAttackCustom").TEXT[0],this.amount);
    }

    public void onInitialApplication(AbstractCard card) {
        this.originalTarget = card.target;
        if (card.target != AbstractCard.CardTarget.ENEMY)
            card.target = AbstractCard.CardTarget.SELF_AND_ENEMY;
    }

    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new GoodImpressionDamageAction(this.amount*1.0F/100, 0,AbstractDungeon.player, target, card));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new GoodImpressionRateAttackCustom(this.amount);
    }

}
