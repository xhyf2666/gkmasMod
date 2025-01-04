package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.GoodTune;

public class GoodTuneCustom extends AbstractCardCustomEffect {

    public static String growID = "GoodTuneCustom";

    public GoodTuneCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " NL " + String.format(CardCrawlGame.languagePack.getUIString("customEffect:GoodTuneCustom").TEXT[0], this.amount);
    }

    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new GoodTune(AbstractDungeon.player, this.amount), this.amount));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new GoodTuneCustom(this.amount);
    }

}
