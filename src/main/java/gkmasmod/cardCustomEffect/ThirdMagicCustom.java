package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import gkmasmod.cards.anomaly.BattlePractice;
import gkmasmod.cards.anomaly.OneStep;
import gkmasmod.cards.anomaly.PotentialAbility;
import gkmasmod.cards.anomaly.SurpriseMiss;
import gkmasmod.cards.free.IdolDeclaration;
import gkmasmod.cards.logic.HandwrittenLetter;
import gkmasmod.cards.logic.HardStretching;
import gkmasmod.utils.CustomHelper;

public class ThirdMagicCustom extends AbstractCardCustomEffect {

    public static String growID = "ThirdMagicCustom";

    public ThirdMagicCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ThirdMagicCustom(this.amount);
    }


    @Override
    public void onInitialApplication(AbstractCard card) {
        if(card instanceof IdolDeclaration){
            card.exhaust = false;
        }
    }

    @Override
    public void onRemove(AbstractCard card) {
        if(card instanceof IdolDeclaration){
            card.exhaust = true;
        }
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if(card instanceof IdolDeclaration){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:IdolDeclaration_Effect0").DESCRIPTION;
        }
        if(card instanceof OneStep){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:OneStep_Effect0").DESCRIPTION;
        }
        if(card instanceof SurpriseMiss){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:SurpriseMiss_Effect0").DESCRIPTION;
        }
        if(card instanceof PotentialAbility){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:PotentialAbility_Effect0").DESCRIPTION;
        }
        if(card instanceof BattlePractice){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:BattlePractice_Effect1").DESCRIPTION;
        }
        return rawDescription;
    }
}
