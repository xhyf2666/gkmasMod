package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import gkmasmod.cards.GkmasCardTag;

import java.util.ArrayList;
import java.util.Iterator;


public class BlossomingSeasonAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private int num;
    private boolean upgrade;

    public BlossomingSeasonAction(int numCards,boolean upgrade) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.num = numCards;
        this.upgrade = upgrade;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            }

            if (this.p.hand.group.size() <= this.num) {

                ArrayList<AbstractCard> cards = new ArrayList<>();
                for (AbstractCard c : this.p.hand.group) {
                    cards.add(c);
                }
                for (AbstractCard c : cards) {
                    c.tags.add(GkmasCardTag.OUTSIDE_TAG);
                    this.p.hand.moveToExhaustPile(c);
                    addToBot(new AojiruAction(AbstractDungeon.player,this.upgrade, null));
                }
                this.isDone = true;
                return;
            }

            if(p.hand.group.size()>2)
            {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 2, false, false, false,false);
                this.tickDuration();
                return;
            }
        }
        else {
            Iterator var1;
            AbstractCard c;
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                    var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

                    while(var1.hasNext()) {
                        c = (AbstractCard)var1.next();
                        c.tags.add(GkmasCardTag.OUTSIDE_TAG);
                        this.p.hand.moveToExhaustPile(c);
                        addToBot(new AojiruAction(AbstractDungeon.player,this.upgrade, null));
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
        uiStrings = CardCrawlGame.languagePack.getUIString("gkmasMod:BlossomingSeasonAction");
        TEXT = uiStrings.TEXT;
    }
}
