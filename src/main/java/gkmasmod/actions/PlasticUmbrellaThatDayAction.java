package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.cards.anomaly.BeyondTheCrossing;
import gkmasmod.powers.TempSavePower;

import java.util.Iterator;


public class PlasticUmbrellaThatDayAction extends AbstractGameAction {
    public PlasticUmbrellaThatDayAction() {

    }

    public void update() {
        for(AbstractCard c:AbstractDungeon.player.hand.group){
            if(c.cardID.equals(BeyondTheCrossing.ID)){
                TempSavePower.addCard(AbstractDungeon.player,c);
                addToBot(new DrawCardAction(1));
            }
        }
        for(AbstractCard c:AbstractDungeon.player.drawPile.group){
            if(c.cardID.equals(BeyondTheCrossing.ID)){
                TempSavePower.addCard(AbstractDungeon.player,c);
            }
        }
        for(AbstractCard c:AbstractDungeon.player.discardPile.group){
            if(c.cardID.equals(BeyondTheCrossing.ID)){
                TempSavePower.addCard(AbstractDungeon.player,c);
            }
        }
        this.isDone = true;
    }

}
