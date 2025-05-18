package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.cardGrowEffect.BlockGrow;
import gkmasmod.cardGrowEffect.DamageGrow;
import gkmasmod.cardGrowEffect.EnergyGrow;
import gkmasmod.powers.TempSavePower;
import gkmasmod.utils.GrowHelper;

public class ResilienceGrowAction extends AbstractGameAction {
    int amount;
    boolean flag=false;

    public ResilienceGrowAction(int amount,boolean flag) {
        this.amount = amount;
        this.flag=flag;
    }

    public void update() {
        if(!AbstractDungeon.player.hand.isEmpty()){
            AbstractCard card = AbstractDungeon.player.hand.getRandomCard(AbstractDungeon.cardRandomRng);
            GrowHelper.grow(card, EnergyGrow.growID,1);
        }
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(c.type == AbstractCard.CardType.ATTACK){
                GrowHelper.grow(c, DamageGrow.growID,this.amount);
            }
            if(flag&&c.type== AbstractCard.CardType.SKILL){
                GrowHelper.grow(c, BlockGrow.growID,this.amount);
            }
        }
        for(AbstractCard c:AbstractDungeon.player.drawPile.group){
            if(c.type == AbstractCard.CardType.ATTACK){
                GrowHelper.grow(c, DamageGrow.growID,this.amount);
            }
            if(flag&&c.type== AbstractCard.CardType.SKILL){
                GrowHelper.grow(c, BlockGrow.growID,this.amount);
            }
        }
        for(AbstractCard c:AbstractDungeon.player.discardPile.group){
            if(c.type == AbstractCard.CardType.ATTACK){
                GrowHelper.grow(c, DamageGrow.growID,this.amount);
            }
            if(flag&&c.type== AbstractCard.CardType.SKILL){
                GrowHelper.grow(c, BlockGrow.growID,this.amount);
            }
        }
        for(AbstractCard c:AbstractDungeon.player.exhaustPile.group) {
            if(c.type == AbstractCard.CardType.ATTACK){
                GrowHelper.grow(c, DamageGrow.growID,this.amount);
            }
            if(flag&&c.type== AbstractCard.CardType.SKILL){
                GrowHelper.grow(c, BlockGrow.growID,this.amount);
            }
        }
        if(AbstractDungeon.player.hasPower(TempSavePower.POWER_ID)){
            TempSavePower tempSavePower = (TempSavePower) AbstractDungeon.player.getPower(TempSavePower.POWER_ID);
            for(AbstractCard c:tempSavePower.getCards()){
                if(c.type == AbstractCard.CardType.ATTACK){
                    GrowHelper.grow(c, DamageGrow.growID,this.amount);
                }
                if(flag&&c.type== AbstractCard.CardType.SKILL){
                    GrowHelper.grow(c, BlockGrow.growID,this.amount);
                }
            }
        }
        this.isDone = true;
    }


}
