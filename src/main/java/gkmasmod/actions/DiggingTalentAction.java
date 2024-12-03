package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.powers.TempSavePower;

import java.util.ArrayList;
import java.util.Iterator;


public class DiggingTalentAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer player;
    private int numberOfCards;
    private boolean optional;

    private ArrayList<AbstractCard> retain = new ArrayList<>();

    public DiggingTalentAction(int numberOfCards) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
        this.optional = true;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (AbstractDungeon.player.exhaustPile.isEmpty()) {
                this.isDone = true;
                return;
            }

            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.exhaustPile, 1, TEXT[0], false);
            tickDuration();

            this.tickDuration();
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while(var1.hasNext()) {
                    AbstractCard c = (AbstractCard)var1.next();
                    TempSavePower.addCard(AbstractDungeon.player,c);
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }


static {
        TEXT = CardCrawlGame.languagePack.getUIString("gkmasMod:DiggingTalentAction").TEXT;
        }
}
