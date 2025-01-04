package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.cards.GkmasCardTag;

public class TempMoreActionCustom extends AbstractCardCustomEffect {

    public static String growID = "TempMoreActionCustom";

    public TempMoreActionCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        if(card.tags.contains(GkmasCardTag.MORE_ACTION_TAG))
            return rawDescription;
        return rawDescription + " " + String.format(CardCrawlGame.languagePack.getUIString("customEffect:TempMoreActionCustom").TEXT[0])+" "+this.amount;
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        --this.amount;
        return this.amount == 0;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new GainTrainRoundPowerAction(AbstractDungeon.player,this.amount));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new TempMoreActionCustom(this.amount);
    }

}
