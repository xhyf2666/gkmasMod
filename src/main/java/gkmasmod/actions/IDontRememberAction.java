package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.cardGrowEffect.EnergyGrow;
import gkmasmod.cardGrowEffect.EtherealGrow;
import gkmasmod.utils.CardHelper;
import gkmasmod.utils.GrowHelper;

import java.util.ArrayList;

public class IDontRememberAction extends AbstractGameAction {
    private AbstractCreature p;

    public IDontRememberAction(AbstractCreature player) {
        this.p = player;
    }

    @Override
    public void update() {
        int count = AbstractDungeon.player.hand.size();

        int i;

        for(i = 0; i < count; ++i) {
            if (Settings.FAST_MODE) {
                this.addToTop(new ExhaustAction(1, true, true, false, Settings.ACTION_DUR_XFAST));
            } else {
                this.addToTop(new ExhaustAction(1, true, true));
            }
        }

        ArrayList<AbstractCard> cards = CardHelper.getAllIdolCards();
        for (i = 0; i < count; i++) {
            AbstractCard card= cards.get(i);
            GrowHelper.grow(card, EnergyGrow.growID,-1);
            GrowHelper.grow(card, EtherealGrow.growID,1);
            addToBot(new MakeTempCardInHandAction(card, 1));
        }

        this.isDone = true;
    }
}
