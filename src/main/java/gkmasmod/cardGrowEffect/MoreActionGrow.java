package gkmasmod.cardGrowEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.cards.GkmasCardTag;

public class MoreActionGrow extends AbstractGrowEffect {

    public static String growID = "MoreActionGrow";

    public MoreActionGrow(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        if(card.tags.contains(GkmasCardTag.MORE_ACTION_TAG))
            return rawDescription;
        if(this.amount >1)
            return rawDescription + " " + String.format(CardCrawlGame.languagePack.getUIString("customEffect:MoreActionCustom").TEXT[0])+" "+this.amount;
        return rawDescription + " " + CardCrawlGame.languagePack.getUIString("customEffect:MoreActionCustom").TEXT[0];
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new GainTrainRoundPowerAction(AbstractDungeon.player,this.amount));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new MoreActionGrow(this.amount);
    }

}
