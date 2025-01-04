package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.SlowPower;
import gkmasmod.powers.GoodImpression;

public class SlowCustom extends AbstractCardCustomEffect {

    public static String growID = "SlowCustom";

    public SlowCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " NL " + String.format(CardCrawlGame.languagePack.getUIString("customEffect:SlowCustom").TEXT[0], this.amount);
    }

    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new ApplyPowerAction(target, target, new SlowPower(target, this.amount), this.amount));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new SlowCustom(this.amount);
    }

}
