package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import gkmasmod.powers.TempSavePower;
import gkmasmod.utils.GrowHelper;

import java.util.ArrayList;
import java.util.Iterator;


public class SelectCardGrowAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;

    private ArrayList<AbstractCard> cards = new ArrayList<>();

    private int num;
    private String growID;
    private boolean tempSave = false;
    private int selectedCardNum = 1;

    public SelectCardGrowAction(int num,String growID) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.num = num;
        this.growID = growID;
    }

    public SelectCardGrowAction(int num,String growID,int selectedCardNum,boolean tempSave) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.num = num;
        this.growID = growID;
        this.selectedCardNum = selectedCardNum;
        this.tempSave = tempSave;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            }
            if(p.hand.group.size()>0)
            {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.selectedCardNum, false, false, false,false);
                this.tickDuration();
                return;
            }
        }
        else {
            Iterator var1;
            AbstractCard c=null;
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();
                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    GrowHelper.grow(c,this.growID,this.num);
                    if(tempSave){
                        TempSavePower.addCard(AbstractDungeon.player,c);
                    }
                    else{
                        AbstractDungeon.player.hand.addToTop(c);
                    }
                }
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
                this.isDone = true;
            }
            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("gkmasMod:SelectCardGrowAction");
        TEXT = uiStrings.TEXT;
    }
}
