package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.cards.anomaly.*;
import gkmasmod.cards.free.*;
import gkmasmod.cards.logic.*;
import gkmasmod.cards.sense.Balance;
import gkmasmod.cards.sense.PushingTooHardAgain;
import gkmasmod.cards.sense.StartSignal;
import gkmasmod.cards.sense.WishPower;
import gkmasmod.utils.CustomHelper;

public class EffectChangeCustom extends AbstractCardCustomEffect {

    public static String growID = "EffectChangeCustom";

    public boolean fromCopy = false;

    public EffectChangeCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
        this.priority = 1;
    }

    public EffectChangeCustom(int damage, boolean fromCopy) {
        this.amount = damage;
        this.fromCopy = fromCopy;
        growEffectID = growID;
        this.priority = 1;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        if(card instanceof HardStretching){
            card.tags.remove(gkmasmod.cards.GkmasCardTag.MORE_ACTION_TAG);
            CustomHelper.removeCustom(card,MoreActionCustom.growID);
            if(!fromCopy)
                CustomHelper.custom(card,TempMoreActionCustom.growID,1);
            card.cost = 0;
            card.costForTurn = 0;
        }
        if(card instanceof HandwrittenLetter){
            card.cost = -1;
            card.costForTurn = -1;
        }
        if(card instanceof IdolDeclaration){
            card.exhaust = false;
            card.tags.remove(gkmasmod.cards.GkmasCardTag.MORE_ACTION_TAG);
            CustomHelper.removeCustom(card,MoreActionCustom.growID);
            if(!fromCopy)
                CustomHelper.custom(card,TempMoreActionCustom.growID,((IdolDeclaration) card).thirdMagicNumber);
        }
        if(card instanceof Repartitioning){
            card.exhaust = false;
            card.tags.remove(gkmasmod.cards.GkmasCardTag.MORE_ACTION_TAG);
            CustomHelper.removeCustom(card,MoreActionCustom.growID);
            if(!fromCopy)
                CustomHelper.custom(card,SecondMagicCustom.growID,-1);
            card.cost = 0;
            card.costForTurn = 0;
        }
        if(card instanceof Balance){
            card.cost = 2;
            card.costForTurn = 2;
        }
        if(card instanceof HeartAndSoul){
            card.cost = 1;
            card.costForTurn = 1;
            card.tags.remove(gkmasmod.cards.GkmasCardTag.MORE_ACTION_TAG);
            CustomHelper.removeCustom(card,MoreActionCustom.growID);
            if(!fromCopy) {
                CustomHelper.custom(card, MagicCustom.growID, -1);
                CustomHelper.custom(card, SecondMagicCustom.growID, -1);
            }
            card.exhaust = false;
        }
        if(card instanceof BecomeIdol){
            card.exhaust = false;
            card.tags.remove(gkmasmod.cards.GkmasCardTag.MORE_ACTION_TAG);
            CustomHelper.removeCustom(card,MoreActionCustom.growID);
        }
        if(card instanceof StarPicking){
            card.exhaust = true;
        }
        if(card instanceof TurnBack){
            card.exhaust = false;
        }
        if(card instanceof GetAnswer){
            if(!fromCopy)
                CustomHelper.custom(card,MoreActionCustom.growID,1);
        }
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if(card instanceof HardStretching){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:HardStretching_Effect0").DESCRIPTION;
        }
        if(card instanceof HandwrittenLetter){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:HandwrittenLetter_Effect0").DESCRIPTION;
        }
        if(card instanceof IdolDeclaration){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:IdolDeclaration_Effect1").DESCRIPTION;
        }
        if(card instanceof WhatDoesSheDo){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:WhatDoesSheDo_Effect0").DESCRIPTION;
        }
        if(card instanceof Repartitioning){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:Repartitioning_Effect1").DESCRIPTION;
        }
        if(card instanceof StartSignal){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:StartSignal_Effect0").DESCRIPTION;
        }
        if(card instanceof TurnBack){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:TurnBack_Effect1").DESCRIPTION;
        }
        if(card instanceof WishPower){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:WishPower_Effect0").DESCRIPTION;
        }
        if(card instanceof Balance){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:Balance_Effect0").DESCRIPTION;
        }
        if(card instanceof PushingTooHardAgain){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:PushingTooHardAgain_Effect0").DESCRIPTION;
        }
        if(card instanceof StartSmile){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:StartSmile_Effect0").DESCRIPTION;
        }
        if(card instanceof HeartAndSoul){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:HeartAndSoul_Effect0").DESCRIPTION;
        }
        if(card instanceof BecomeIdol){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:BecomeIdol_Effect0").DESCRIPTION;
        }
        if(card instanceof ThunderWillStop){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:ThunderWillStop_Effect0").DESCRIPTION;
        }
        if(card instanceof LikeStars){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:LikeStars_Effect0").DESCRIPTION;
        }
        if(card instanceof StarPicking){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:StarPicking_Effect0").DESCRIPTION;
        }
        if(card instanceof CanYouAccept){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:CanYouAccept_Effect1").DESCRIPTION;
        }
        if(card instanceof Todoku){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:Todoku_Effect0").DESCRIPTION;
        }
        if(card instanceof GetAnswer){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:GetAnswer_Effect0").DESCRIPTION;
        }
        if(card instanceof GiveYou){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:GiveYou_Effect0").DESCRIPTION;
        }
        if(card instanceof GachaAgain){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:GachaAgain_Effect0").DESCRIPTION;
        }
        if(card instanceof CareCard){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:CareCard_Effect1").DESCRIPTION;
        }
        if(card instanceof LittleSun){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:LittleSun_Effect0").DESCRIPTION;
        }
        if(card instanceof DreamColorLipstick){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:DreamColorLipstick_Effect1").DESCRIPTION;
        }
        if(card instanceof Resilience){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:Resilience_Effect0").DESCRIPTION;
        }
        return rawDescription;
    }

    @Override
    public void onRemove(AbstractCard card) {
        if(card instanceof HardStretching){
            card.tags.add(gkmasmod.cards.GkmasCardTag.MORE_ACTION_TAG);
            CustomHelper.removeCustom(card,TempMoreActionCustom.growID);
            CustomHelper.custom(card,MoreActionCustom.growID,1);
            card.cost = 1;
            card.costForTurn = 1;
        }
        if(card instanceof HandwrittenLetter){
            card.cost = 1;
            card.costForTurn = 1;
        }
        if(card instanceof IdolDeclaration){
            card.exhaust = true;
            card.tags.add(gkmasmod.cards.GkmasCardTag.MORE_ACTION_TAG);
            CustomHelper.removeCustom(card,TempMoreActionCustom.growID);
            CustomHelper.custom(card,MoreActionCustom.growID,1);
        }
        if(card instanceof Repartitioning){
            card.exhaust = true;
            card.tags.add(gkmasmod.cards.GkmasCardTag.MORE_ACTION_TAG);
            CustomHelper.custom(card,MoreActionCustom.growID,1);
            CustomHelper.removeCustom(card,SecondMagicCustom.growID);
            card.cost = 1;
            card.costForTurn = 1;
        }
        if(card instanceof Balance){
            card.cost = 1;
            card.costForTurn = 1;
        }
        if(card instanceof HeartAndSoul){
            card.cost = 3;
            card.costForTurn = 3;
            card.tags.add(gkmasmod.cards.GkmasCardTag.MORE_ACTION_TAG);
            CustomHelper.custom(card,MoreActionCustom.growID,2);
            CustomHelper.removeCustom(card,MagicCustom.growID);
            CustomHelper.removeCustom(card,SecondMagicCustom.growID);
            card.exhaust = true;
        }
        if(card instanceof BecomeIdol){
            card.tags.add(gkmasmod.cards.GkmasCardTag.MORE_ACTION_TAG);
            CustomHelper.custom(card,MoreActionCustom.growID,1);
            card.exhaust = true;
        }
        if(card instanceof StarPicking){
            card.exhaust = false;
        }
        if(card instanceof TurnBack){
            card.exhaust = true;
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new EffectChangeCustom(this.amount,true);
    }

}
