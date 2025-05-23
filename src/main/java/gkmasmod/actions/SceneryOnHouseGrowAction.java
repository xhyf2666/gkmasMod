package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.cardGrowEffect.DamageGrow;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.powers.TempSavePower;
import gkmasmod.utils.GrowHelper;

public class SceneryOnHouseGrowAction extends AbstractGameAction {
    int amount;

    public SceneryOnHouseGrowAction(int amount) {
        this.amount = amount;
    }

    public void update() {
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(c.tags.contains(GkmasCardTag.FULL_POWER_TAG)){
                GrowHelper.grow(c, DamageGrow.growID,this.amount);
            }
        }
        for(AbstractCard c:AbstractDungeon.player.drawPile.group){
            if(c.tags.contains(GkmasCardTag.FULL_POWER_TAG)){
                GrowHelper.grow(c, DamageGrow.growID,this.amount);
            }
        }
        for(AbstractCard c:AbstractDungeon.player.discardPile.group){
            if(c.tags.contains(GkmasCardTag.FULL_POWER_TAG)){
                GrowHelper.grow(c, DamageGrow.growID,this.amount);
            }
        }
        for(AbstractCard c:AbstractDungeon.player.exhaustPile.group) {
            if(c.tags.contains(GkmasCardTag.FULL_POWER_TAG)){
                GrowHelper.grow(c, DamageGrow.growID,this.amount);
            }
        }
        if(AbstractDungeon.player.hasPower(TempSavePower.POWER_ID)){
            TempSavePower tempSavePower = (TempSavePower) AbstractDungeon.player.getPower(TempSavePower.POWER_ID);
            for(AbstractCard c:tempSavePower.getCards()){
                if(c.tags.contains(GkmasCardTag.FULL_POWER_TAG)){
                    GrowHelper.grow(c, DamageGrow.growID,this.amount);
                }
            }
        }
        this.isDone = true;
    }


}
