package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import gkmasmod.cards.anomaly.ComprehensiveArt;
import gkmasmod.cards.anomaly.DoYourBest;
import gkmasmod.cards.anomaly.KiraKiraPrism;
import gkmasmod.cards.free.IdolDeclaration;
import gkmasmod.cards.free.Repartitioning;
import gkmasmod.cards.free.TurnBack;
import gkmasmod.cards.free.WhatDoesSheDo;
import gkmasmod.cards.logic.HandwrittenLetter;
import gkmasmod.cards.logic.HardStretching;
import gkmasmod.cards.sense.Balance;
import gkmasmod.cards.sense.JustOneMore;
import gkmasmod.cards.sense.StartSignal;
import gkmasmod.cards.sense.WishPower;

public class EffectReduceCustom extends AbstractCardCustomEffect {

    public static String growID = "EffectReduceCustom";

    public EffectReduceCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        if(card instanceof JustOneMore){
            ((JustOneMore)card).textureImg = "gkmasModResource/img/cards/common/JustOneMoreSp.png";
            ((JustOneMore)card).loadCardImage("gkmasModResource/img/cards/common/JustOneMoreSp.png");
        }
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if(card instanceof JustOneMore){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:JustOneMore_Effect0").DESCRIPTION;
        }
        if(card instanceof DoYourBest){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:DoYourBest_Effect0").DESCRIPTION;
        }
        if(card instanceof ComprehensiveArt){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:ComprehensiveArt_Effect0").DESCRIPTION;
        }
        if(card instanceof KiraKiraPrism){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:KiraKiraPrism_Effect0").DESCRIPTION;
        }
        return rawDescription;
    }

    @Override
    public void onRemove(AbstractCard card) {
        if(card instanceof JustOneMore){
            ((JustOneMore)card).textureImg = "gkmasModResource/img/cards/common/JustOneMore.png";
            ((JustOneMore)card).loadCardImage("gkmasModResource/img/cards/common/JustOneMore.png");
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new EffectReduceCustom(this.amount);
    }



}
