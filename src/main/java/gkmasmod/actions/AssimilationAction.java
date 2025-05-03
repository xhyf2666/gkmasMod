package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import gkmasmod.cards.free.Sleepy;

import java.util.Iterator;


public class AssimilationAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private int num;

    /**
     * 同化Action：选择num张牌，将其转化为眠气
     * @param numCards 选择的牌数
     */
    public AssimilationAction(int numCards) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.num = numCards;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            }

            if (this.p.hand.group.size() < this.num) {
                this.isDone = true;
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.num, false, false, false,false);
            this.tickDuration();
            return;
        }
        else {
            Iterator var1;
            AbstractCard c1;
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                    var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();
                    if(AbstractDungeon.handCardSelectScreen.selectedCards.group.size()==this.num){
                        c1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0);
                        p.hand.removeCard(c1);
                        addToBot(new MakeTempCardInHandAction(new Sleepy(), 1));
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
        uiStrings = CardCrawlGame.languagePack.getUIString("gkmasMod:AssimilationAction");
        TEXT = uiStrings.TEXT;
    }
}
