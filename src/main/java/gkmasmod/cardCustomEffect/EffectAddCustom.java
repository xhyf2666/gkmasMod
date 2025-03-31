package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import gkmasmod.cards.anomaly.*;
import gkmasmod.cards.free.IdolDeclaration;
import gkmasmod.cards.free.Repartitioning;
import gkmasmod.cards.free.TurnBack;
import gkmasmod.cards.logic.ForShiningYou;
import gkmasmod.cards.logic.HardStretching;
import gkmasmod.cards.logic.WaitALittleLonger;
import gkmasmod.utils.CustomHelper;

public class EffectAddCustom extends AbstractCardCustomEffect {

    public static String growID = "EffectAddCustom";

    public EffectAddCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        if(card instanceof TurnBack){
            card.exhaust = false;
        }
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if(card instanceof WaitALittleLonger){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:WaitALittleLonger_Effect0").DESCRIPTION;
        }
        if(card instanceof ForShiningYou){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:ForShiningYou_Effect0").DESCRIPTION;
        }
        if(card instanceof Repartitioning){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:Repartitioning_Effect0").DESCRIPTION;
        }
        if(card instanceof TurnBack){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:TurnBack_Effect2").DESCRIPTION;
        }
        if(card instanceof TearfulMemories){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:TearfulMemories_Effect0").DESCRIPTION;
        }
        if(card instanceof OpeningAct){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:OpeningAct_Effect0").DESCRIPTION;
        }
        if(card instanceof Countdown){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:Countdown_Effect0").DESCRIPTION;
        }
        if(card instanceof Pride){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:Pride_Effect0").DESCRIPTION;
        }
        if(card instanceof BattlePractice){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:BattlePractice_Effect0").DESCRIPTION;
        }
        if(card instanceof TakeFlight){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:TakeFlight_Effect0").DESCRIPTION;
        }
        if(card instanceof MayRain){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:MayRain_Effect0").DESCRIPTION;
        }
        if(card instanceof LikeUsual){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:LikeUsual_Effect0").DESCRIPTION;
        }
        if(card instanceof WorkHard){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:WorkHard_Effect0").DESCRIPTION;
        }
        if(card instanceof CanYouAccept){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:CanYouAccept_Effect0").DESCRIPTION;
        }
        if(card instanceof CareCard){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:CareCard_Effect0").DESCRIPTION;
        }
        return rawDescription;
    }

    @Override
    public void onRemove(AbstractCard card) {
        if(card instanceof TurnBack){
            card.exhaust = true;
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new EffectAddCustom(this.amount);
    }

}
