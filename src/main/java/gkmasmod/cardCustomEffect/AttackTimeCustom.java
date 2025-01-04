package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.actions.ModifyDamageAction;
import gkmasmod.cards.GkmasCardTag;

public class AttackTimeCustom extends AbstractCardCustomEffect {

    public static String growID = "AttackTimeCustom";

    public AttackTimeCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " " + String.format(CardCrawlGame.languagePack.getUIString("customEffect:AttackTimeCustom").TEXT[0],this.amount);
    }

    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if(card.baseDamage<0)
            return;
        for (int i = 0; i < this.amount; i++) {
            addToBot(new ModifyDamageAction(target,new DamageInfo(AbstractDungeon.player,card.baseDamage,card.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL,card,false));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new AttackTimeCustom(this.amount);
    }

}
