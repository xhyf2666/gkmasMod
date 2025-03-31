package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Iterator;


public class TurnBackAction3 extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    AbstractCard handCardSelected = null;


    public TurnBackAction3() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_MED;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            if (p.hand.isEmpty() || p.discardPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.size() == 1) {
                this.handCardSelected = this.p.hand.getBottomCard();
            }
            else{
                // 让玩家选择手牌中的一张卡
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false,false,true);
            }
            this.duration = Settings.ACTION_DUR_FAST;
        }
        else if(this.duration == Settings.ACTION_DUR_FAST){
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved&&this.handCardSelected==null) {

                for(Iterator var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); var1.hasNext(); ) {
                    this.handCardSelected = (AbstractCard)var1.next();
                }

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            }
            else{
                if(p.discardPile.size() == 1){
                    AbstractCard abstractCard = this.p.discardPile.getTopCard();
                    abstractCard.unfadeOut();
//                    System.out.println("exhaustPile: "+abstractCard.name);
                    this.p.hand.addToTop(abstractCard);
                    if (AbstractDungeon.player.hasPower("Corruption") && abstractCard.type == AbstractCard.CardType.SKILL)
                        abstractCard.setCostForTurn(-9);
                    this.p.discardPile.removeCard(abstractCard);
                    abstractCard.unhover();
                    abstractCard.fadingOut = false;
                    this.p.hand.moveToDiscardPile(this.handCardSelected);
                    this.isDone = true;
                }
                else{
                    AbstractDungeon.gridSelectScreen.open(p.discardPile, 1, TEXT[1], false);
                    tickDuration();
                }
            }
        }
        else{
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                AbstractCard abstractCard = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                abstractCard.unfadeOut();
                this.p.hand.addToHand(abstractCard);
                if (AbstractDungeon.player.hasPower("Corruption") && abstractCard.type == AbstractCard.CardType.SKILL)
                    abstractCard.setCostForTurn(-9);
                this.p.discardPile.removeCard(abstractCard);
                abstractCard.unhover();
                abstractCard.fadingOut = false;
                this.p.hand.moveToDiscardPile(this.handCardSelected);
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.isDone = true;
            }
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("gkmasMod:TurnBackAction");
        TEXT = uiStrings.TEXT;
    }
}
