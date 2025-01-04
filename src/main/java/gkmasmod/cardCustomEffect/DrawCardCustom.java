package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.actions.ModifyDamageAction;

public class DrawCardCustom extends AbstractCardCustomEffect {

    public static String growID = "DrawCardCustom";

    public DrawCardCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " " + String.format(CardCrawlGame.languagePack.getUIString("customEffect:DrawCardCustom").TEXT[0],this.amount);
    }

    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new DrawCardAction(this.amount));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DrawCardCustom(this.amount);
    }

}
