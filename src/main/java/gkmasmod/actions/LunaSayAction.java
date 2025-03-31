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

import java.util.ArrayList;
import java.util.Iterator;


public class LunaSayAction extends AbstractGameAction {


    public LunaSayAction() {
    }

    public void update() {
        ArrayList<AbstractCard> toMove = new ArrayList<>();

        // 遍历消耗堆，找到所有诅咒卡和状态卡
        for (AbstractCard card : AbstractDungeon.player.exhaustPile.group) {
            if (card.type == AbstractCard.CardType.CURSE || card.type == AbstractCard.CardType.STATUS) {
                toMove.add(card);
            }
        }

        // 将找到的卡牌移回抽牌堆
        for (AbstractCard card : toMove) {
            card.unhover();
            card.fadingOut = false;
            AbstractDungeon.player.exhaustPile.moveToDeck(card, false);
        }

        this.isDone = true;
    }

}
