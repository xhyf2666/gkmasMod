package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.cards.anomaly.Starlight;
import gkmasmod.cardGrowEffect.AttackTimeGrow;
import gkmasmod.powers.TempSavePower;
import gkmasmod.utils.GrowHelper;

public class GetAnswerGrowAction extends AbstractGameAction {

    public GetAnswerGrowAction() {
    }

    public void update() {
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(c instanceof Starlight){
                GrowHelper.grow(c, AttackTimeGrow.growID,1);
            }
        }
        for(AbstractCard c:AbstractDungeon.player.drawPile.group){
            if(c instanceof Starlight){
                GrowHelper.grow(c, AttackTimeGrow.growID,1);
            }
        }
        for(AbstractCard c:AbstractDungeon.player.discardPile.group){
            if(c instanceof Starlight){
                GrowHelper.grow(c, AttackTimeGrow.growID,1);
            }
        }
        for(AbstractCard c:AbstractDungeon.player.exhaustPile.group) {
            if (c instanceof Starlight) {
                GrowHelper.grow(c, AttackTimeGrow.growID,1);
            }
        }
        if(AbstractDungeon.player.hasPower(TempSavePower.POWER_ID)){
            TempSavePower tempSavePower = (TempSavePower) AbstractDungeon.player.getPower(TempSavePower.POWER_ID);
            for(AbstractCard c:tempSavePower.getCards()){
                if(c instanceof Starlight){
                    GrowHelper.grow(c, AttackTimeGrow.growID,1);
                }
            }
        }
        this.isDone = true;
    }


}
