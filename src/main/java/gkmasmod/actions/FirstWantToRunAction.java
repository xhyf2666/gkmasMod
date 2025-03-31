package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.utils.PlayerHelper;

import java.util.ArrayList;
import java.util.Iterator;

public class FirstWantToRunAction extends AbstractGameAction{
    private float rate;

    public FirstWantToRunAction(float rate) {
        this.rate = rate;
    }

    public void update() {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if(c.type== AbstractCard.CardType.STATUS || c.type== AbstractCard.CardType.CURSE){
                cards.add(c);
            }
        }

        if(cards.size()>0){
            int count = PlayerHelper.getPowerAmount(AbstractDungeon.player, StrengthPower.POWER_ID);
            count = (int) (cards.size()*count * rate);
            if(count>0){
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, count), count));
            }
        }
        for (AbstractCard c : cards) {
            AbstractDungeon.player.hand.moveToExhaustPile(c);
        }
        this.isDone = true;
    }
}
