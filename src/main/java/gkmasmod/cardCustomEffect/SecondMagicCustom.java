package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import gkmasmod.cards.anomaly.AccelerateLand;
import gkmasmod.cards.anomaly.JustAppeal;
import gkmasmod.cards.anomaly.Lucky;
import gkmasmod.cards.anomaly.StepByStep;
import gkmasmod.cards.free.TurnBack;

public class SecondMagicCustom extends AbstractCardCustomEffect {

    public static String growID = "SecondMagicCustom";

    public SecondMagicCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if(card instanceof JustAppeal){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:JustAppeal_Effect0").DESCRIPTION;
        }
        if(card instanceof Lucky){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:Lucky_Effect0").DESCRIPTION;
        }
        if(card instanceof StepByStep){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:StepByStep_Effect0").DESCRIPTION;
        }
        if(card instanceof AccelerateLand){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:AccelerateLand_Effect0").DESCRIPTION;
        }
        return rawDescription;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new SecondMagicCustom(this.amount);
    }

}
