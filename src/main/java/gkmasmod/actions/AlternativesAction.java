package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import gkmasmod.cards.GkmasCardTag;

import java.util.Iterator;


public class AlternativesAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;

    /**
     * 秋色Action：选择2张牌，将第1张牌转化为第2张牌
     */
    public AlternativesAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            }

            if (this.p.hand.group.size() < 2) {
                this.isDone = true;
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 2, false, false, false,false);
            this.tickDuration();
            return;
        }
        else {
            Iterator var1;
            AbstractCard c1,c2;
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                    var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();
                    if(AbstractDungeon.handCardSelectScreen.selectedCards.group.size()==2){
                        c1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0);
                        c2 = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(1);
                        p.hand.removeCard(c1);
                        addToBot(new MakeTempCardInHandAction(c2, true, true));
                        addToBot(new MakeTempCardInHandAction(c2, 1));
                    }
                    this.returnCards();
                    AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                    AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
                    this.isDone = true;
                }
            this.tickDuration();
        }
    }


    private void returnCards() {
        this.p.hand.refreshHandLayout();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("gkmasMod:AlternativesAction");
        TEXT = uiStrings.TEXT;
    }
}
