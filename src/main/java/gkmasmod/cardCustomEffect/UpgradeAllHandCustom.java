package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.actions.ModifyDamageAction;
import gkmasmod.actions.UpgradeAllHandCardAction;

public class UpgradeAllHandCustom extends AbstractCardCustomEffect {

    public static String growID = "UpgradeAllHandCustom";

    public UpgradeAllHandCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " NL " + String.format(CardCrawlGame.languagePack.getUIString("customEffect:UpgradeAllHandCustom").TEXT[0],this.amount);
    }

    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new UpgradeAllHandCardAction());
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new UpgradeAllHandCustom(this.amount);
    }

}
