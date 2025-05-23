package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.Iterator;
import java.util.UUID;


public class TurnBackAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    AbstractCard handCardSelected = null;


    public TurnBackAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_MED;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            if (p.hand.isEmpty() || p.exhaustPile.isEmpty()) {
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

                if(p.exhaustPile.size() == 1){
                    AbstractCard abstractCard = this.p.exhaustPile.getTopCard();
                    abstractCard.unfadeOut();
                    this.p.hand.addToTop(abstractCard);
                    if (AbstractDungeon.player.hasPower("Corruption") && abstractCard.type == AbstractCard.CardType.SKILL)
                        abstractCard.setCostForTurn(-9);
                    this.p.exhaustPile.removeCard(abstractCard);
                    abstractCard.unhover();
                    abstractCard.fadingOut = false;
                    this.p.hand.moveToExhaustPile(this.handCardSelected);
                    this.isDone = true;
                }
                else{
                    AbstractDungeon.gridSelectScreen.open(p.exhaustPile, 1, TEXT[1], false);
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
                this.p.exhaustPile.removeCard(abstractCard);
                abstractCard.unhover();
                abstractCard.fadingOut = false;
                this.p.hand.moveToExhaustPile(this.handCardSelected);
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
