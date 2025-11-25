package gkmasmod.actions.cardAction;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Headbutt;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;

import java.util.ArrayList;
import java.util.Iterator;


public class TheGeniusAction extends AbstractGameAction {
    private AbstractPlayer p;
    private int num;

    /**
     * 天赋之才Action：
     * @param numCards 选择的卡牌数量
     **/
    public TheGeniusAction(int numCards) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.num = numCards;
    }

    public void update() {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        for (AbstractCard c : this.p.drawPile.group) {
            if(c.rarity == AbstractCard.CardRarity.RARE){
                cards.add(c);
            }
            else if(c instanceof GkmasCard){
                GkmasCard gkmasCard = (GkmasCard)c;
                if(gkmasCard.bannerColor.equals("color")){
                    cards.add(c);
                }
            }
        }
        for (AbstractCard c : this.p.discardPile.group) {
            if(c.rarity == AbstractCard.CardRarity.RARE){
                cards.add(c);
            }
            else if(c instanceof GkmasCard){
                GkmasCard gkmasCard = (GkmasCard)c;
                if(gkmasCard.bannerColor.equals("color")){
                    cards.add(c);
                }
            }
        }
        if(cards.size() == 0){
            this.isDone = true;
            return;
        }
        // 从cards中随机选择num张卡牌，将其加入抽牌堆顶部
        for (int i = 0; i < this.num; i++) {
            if(cards.size() == 0){
                break;
            }
            AbstractCard c = cards.remove(AbstractDungeon.cardRandomRng.random(cards.size() - 1));
            if(this.p.drawPile.contains(c)){
                this.p.drawPile.removeCard(c);
                this.p.drawPile.moveToDeck(c, false);
            }
            else if(this.p.discardPile.contains(c)){
                this.p.discardPile.removeCard(c);
                this.p.drawPile.moveToDeck(c, false);
            }
        }
        this.isDone = true;
        return;
    }


}
