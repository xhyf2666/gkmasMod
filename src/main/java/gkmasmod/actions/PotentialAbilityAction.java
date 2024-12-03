package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import gkmasmod.powers.TempSavePower;

import java.util.ArrayList;
import java.util.Iterator;


public class PotentialAbilityAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final String RetainString;
    private AbstractPlayer p;

    private ArrayList<AbstractCard> cards = new ArrayList<>();

    private int num;

    public PotentialAbilityAction(int num) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.num = num;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            }

            if(p.hand.group.size()>0)
            {
                AbstractDungeon.handCardSelectScreen.open(String.format(TEXT[0],this.num), this.num, true, true, false,false);
                this.tickDuration();
                return;
            }
        }
        else {
            Iterator var1;
            AbstractCard c;
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                    var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();
                    ArrayList<AbstractCard> cards = new ArrayList<>();
                    while(var1.hasNext()) {
                        c = (AbstractCard)var1.next();
                        cards.add(c);
                    }
                TempSavePower.addCard(AbstractDungeon.player,cards);
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
                this.isDone = true;
                }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("gkmasMod:PotentialAbilityAction");
        TEXT = uiStrings.TEXT;
        RetainString = CardCrawlGame.languagePack.getUIString("gkmasMod:TempSave").TEXT[0];
    }
}
