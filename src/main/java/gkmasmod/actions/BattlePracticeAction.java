package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.powers.TempSavePower;
import gkmasmod.powers.WhatDoesSheDoPower;

import java.util.ArrayList;
import java.util.Iterator;


public class BattlePracticeAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer player;
    private int numberOfCards;
    private boolean optional;

    private ArrayList<AbstractCard> retain = new ArrayList<>();

    public BattlePracticeAction(int numberOfCards) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
        this.optional = true;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (this.numberOfCards > 0) {
                AbstractCard c;
                Iterator var6;
                CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                var6 = this.player.drawPile.group.iterator();

                while(var6.hasNext()) {
                    c = (AbstractCard)var6.next();
                    temp.addToTop(c);
                }

                var6 = this.player.discardPile.group.iterator();

                while(var6.hasNext()) {
                    c = (AbstractCard)var6.next();
                    temp.addToTop(c);
                }

                temp.sortAlphabetically(true);
                temp.sortByRarityPlusStatusCardType(false);
                if (this.optional) {
                    AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, true, String.format(TEXT[0], this.numberOfCards));
                } else {
                    AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, String.format(TEXT[0], this.numberOfCards), false);
                }

                this.tickDuration();
                }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();
                AbstractCard c;
                ArrayList<AbstractCard> cards = new ArrayList<>();
                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    cards.add(c);
                }
                TempSavePower.addCard(AbstractDungeon.player,cards);

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }


    static {
        TEXT = CardCrawlGame.languagePack.getUIString("gkmasMod:BattlePracticeAction").TEXT;
    }
}
