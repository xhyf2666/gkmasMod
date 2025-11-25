package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import gkmasmod.cards.anomaly.*;
import gkmasmod.cards.free.IdolDeclaration;
import gkmasmod.cards.free.Repartitioning;
import gkmasmod.cards.free.TurnBack;
import gkmasmod.cards.logic.*;
import gkmasmod.cards.sense.DressedUpInStyle;
import gkmasmod.cards.sense.EachPath;
import gkmasmod.cards.sense.NeverLose;
import gkmasmod.cards.sense.SenseOfDistance;
import gkmasmod.utils.CustomHelper;

public class EffectAddCustom extends AbstractCardCustomEffect {

    public static String growID = "EffectAddCustom";

    public boolean fromCopy = false;

    public EffectAddCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public EffectAddCustom(int damage, boolean fromCopy) {
        this.amount = damage;
        this.fromCopy = fromCopy;
        growEffectID = growID;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        if(card instanceof TurnBack){
            card.exhaust = false;
        }
        else if(card instanceof SenseOfDistance){
            if(!fromCopy)
                CustomHelper.custom(card,MagicCustom.growID,-1);
        }
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if(card instanceof WaitALittleLonger){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:WaitALittleLonger_Effect0").DESCRIPTION;
        }
        else if(card instanceof ForShiningYou){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:ForShiningYou_Effect0").DESCRIPTION;
        }
        else if(card instanceof Repartitioning){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:Repartitioning_Effect0").DESCRIPTION;
        }
        else if(card instanceof TurnBack){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:TurnBack_Effect2").DESCRIPTION;
        }
        else if(card instanceof TearfulMemories){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:TearfulMemories_Effect0").DESCRIPTION;
        }
        else if(card instanceof OpeningAct){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:OpeningAct_Effect0").DESCRIPTION;
        }
        else if(card instanceof Countdown){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:Countdown_Effect0").DESCRIPTION;
        }
        else if(card instanceof Pride){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:Pride_Effect0").DESCRIPTION;
        }
        else if(card instanceof BattlePractice){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:BattlePractice_Effect0").DESCRIPTION;
        }
        else if(card instanceof TakeFlight){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:TakeFlight_Effect0").DESCRIPTION;
        }
        else if(card instanceof MayRain){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:MayRain_Effect0").DESCRIPTION;
        }
        else if(card instanceof LikeUsual){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:LikeUsual_Effect0").DESCRIPTION;
        }
        else if(card instanceof WorkHard){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:WorkHard_Effect0").DESCRIPTION;
        }
        else if(card instanceof CanYouAccept){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:CanYouAccept_Effect0").DESCRIPTION;
        }
        else if(card instanceof CareCard){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:CareCard_Effect0").DESCRIPTION;
        }
        else if(card instanceof MyColor){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:MyColor_Effect0").DESCRIPTION;
        }
        else if(card instanceof WalkAndWin){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:WalkAndWin_Effect0").DESCRIPTION;
        }
        else if(card instanceof RestAndWalk){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:RestAndWalk_Effect0").DESCRIPTION;
        }
        else if(card instanceof SeriousHobby){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:SeriousHobby_Effect0").DESCRIPTION;
        }
        else if(card instanceof SenseOfDistance){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:SenseOfDistance_Effect0").DESCRIPTION;
        }
        else if(card instanceof DressedUpInStyle){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:DressedUpInStyle_Effect0").DESCRIPTION;
        }
        else if(card instanceof SceneryOnHouse){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:SceneryOnHouse_Effect0").DESCRIPTION;
        }
        else if(card instanceof InternetInfluencer){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:InternetInfluencer_Effect0").DESCRIPTION;
        }
        else if(card instanceof HeatUp){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:HeatUp_Effect1").DESCRIPTION;
        }
        else if(card instanceof KeepOneMind){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:KeepOneMind_Effect0").DESCRIPTION;
        }
        else if(card instanceof Setting){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:Setting_Effect0").DESCRIPTION;
        }
        else if(card instanceof IdolDeclaration){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:IdolDeclaration_Effect0").DESCRIPTION;
        }
        else if(card instanceof Motivation){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:Motivation_Effect0").DESCRIPTION;
        }
        else if(card instanceof NextTimeMust){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:NextTimeMust_Effect0").DESCRIPTION;
        }
        else if(card instanceof Reversal){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:Reversal_Effect0").DESCRIPTION;
        }
        else if(card instanceof SenseOfAccomplishment){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:SenseOfAccomplishment_Effect1").DESCRIPTION;
        }
        else if(card instanceof NeverLose){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:NeverLose_Effect0").DESCRIPTION;
        }
        else if(card instanceof NoDistractions){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:NoDistractions_Effect0").DESCRIPTION;
        }
        else if(card instanceof EachPath){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:EachPath_Effect0").DESCRIPTION;
        }
        return rawDescription;
    }

    @Override
    public void onRemove(AbstractCard card) {
        if(card instanceof TurnBack){
            card.exhaust = true;
        }
        else if(card instanceof SenseOfDistance){
            CustomHelper.removeCustom(card,MagicCustom.growID);
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new EffectAddCustom(this.amount,true);
    }

}
