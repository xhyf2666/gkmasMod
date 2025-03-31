package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import gkmasmod.cards.anomaly.Happy;
import gkmasmod.cards.anomaly.ReadyToGo;
import gkmasmod.cards.anomaly.Starlight;
import gkmasmod.cards.free.IdolDeclaration;
import gkmasmod.cards.free.TurnBack;

public class MagicCustom extends AbstractCardCustomEffect {

    public static String growID = "MagicCustom";

    public MagicCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if(magic>0)
            return magic + this.amount;
        else
            return this.amount;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        if(card instanceof TurnBack){
            card.exhaust = false;
        }
    }

    @Override
    public void onRemove(AbstractCard card) {
        if(card instanceof TurnBack){
            card.exhaust = true;
        }
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if(card instanceof TurnBack){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:TurnBack_Effect0").DESCRIPTION;
        }
        if(card instanceof Starlight){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:Starlight_Effect0").DESCRIPTION;
        }
        if(card instanceof Happy){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:Happy_Effect0").DESCRIPTION;
        }
        if(card instanceof ReadyToGo){
            return CardCrawlGame.languagePack.getCardStrings("gkmasMod:ReadyToGo_Effect0").DESCRIPTION;
        }
        return rawDescription;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new MagicCustom(this.amount);
    }

}
