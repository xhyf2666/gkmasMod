package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import gkmasmod.powers.GreatNotGoodTune;
import gkmasmod.powers.NotGoodTune;

public class NotGreatGoodTuneCustom extends AbstractCardCustomEffect {

    public static String growID = "NotGreatGoodTuneCustom";

    public AbstractCard.CardTarget originalTarget;

    public NotGreatGoodTuneCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " NL " + String.format(CardCrawlGame.languagePack.getUIString("customEffect:NotGreatGoodTuneCustom").TEXT[0], this.amount);
    }

    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new ApplyPowerAction(target, target, new GreatNotGoodTune(target, this.amount), this.amount));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new NotGreatGoodTuneCustom(this.amount);
    }

}
