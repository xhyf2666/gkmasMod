package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;


public class ProudStudentAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final String RetainString;
    private AbstractPlayer p;

    private ArrayList<AbstractCard> retain = new ArrayList<>();

    public ProudStudentAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            }

            for (AbstractCard c : this.p.hand.group) {
                if (c.selfRetain)
                    this.retain.add(c);
            }

            if (this.p.hand.group.size()-this.retain.size() == 1) {
                for (AbstractCard c : this.p.hand.group) {
                    if (!c.selfRetain) {
                        c.selfRetain = true;
                        c.superFlash();
                        c.applyPowers();
                        c.rawDescription = c.rawDescription + " NL " + RetainString;
                        c.initializeDescription();
                        p.hand.refreshHandLayout();
                        this.isDone = true;
                        return;
                    }
                }

            }

            this.p.hand.group.removeAll(this.retain);

            if(p.hand.group.size()>1)
            {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false,true);
                this.tickDuration();
                return;
            }
            if (this.p.hand.group.size() == 1) {
                this.p.hand.getTopCard().selfRetain = true;
                this.p.hand.getTopCard().superFlash();
                this.p.hand.getTopCard().applyPowers();
                this.p.hand.getTopCard().rawDescription = this.p.hand.getTopCard().rawDescription + " NL " + RetainString;
                this.p.hand.getTopCard().initializeDescription();
                returnCards();

                this.isDone = true;
            }
        }
        else {
            Iterator var1;
            AbstractCard c;
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                    var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

                    while(var1.hasNext()) {
                        c = (AbstractCard)var1.next();
                        System.out.println(c.name);
                        c.selfRetain = true;
                        c.rawDescription = c.rawDescription + " NL " + RetainString;
                        c.superFlash();
                        c.applyPowers();
                        c.initializeDescription();
                        this.p.hand.addToTop(c);
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
        for (AbstractCard c : this.retain)
            this.p.hand.addToTop(c);
        this.p.hand.refreshHandLayout();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("gkmasMod:ProudStudentAction");
        TEXT = uiStrings.TEXT;
        RetainString = CardCrawlGame.languagePack.getUIString("gkmasMod:Retain").TEXT[0];
    }
}
