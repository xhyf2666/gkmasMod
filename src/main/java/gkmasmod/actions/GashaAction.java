package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.utils.CardHelper;

import java.util.ArrayList;

public class GashaAction extends AbstractGameAction {
    private AbstractPlayer p;
    private boolean isUpgraded;
    private String rarityColor="";
    private boolean playInstantly = false;

    public GashaAction(AbstractPlayer p,boolean isUpgraded) {
        this(p,isUpgraded, "",false);
    }

    public GashaAction(AbstractPlayer p, boolean isUpgraded, String rarityColor,boolean playInstantly) {
        this.p = p;
        this.isUpgraded = isUpgraded;
        this.rarityColor = rarityColor;
        this.playInstantly = playInstantly;
    }

    public void update() {
        ArrayList<AbstractCard> tmpPool = CardHelper.getAllIdolCards(rarityColor);
        AbstractCard card = tmpPool.get(AbstractDungeon.cardRandomRng.random(0,tmpPool.size()-1));
        if(isUpgraded)
            card.upgrade();
        if(playInstantly){
            addToBot(new NewQueueCardAction(card, true, false, true));
        }
        else
            addToBot(new MakeTempCardInHandAction(card));
        this.isDone = true;
    }

//    public ArrayList<AbstractCard> getRandomCard(AbstractCard.CardColor color,ArrayList<AbstractCard> tmpPool) {
//        Iterator<Map.Entry<String, AbstractCard>> cardLib = CardLibrary.cards.entrySet().iterator();
//        while (true) {
//            if (!cardLib.hasNext())
//                return tmpPool;
//            Map.Entry c = cardLib.next();
//            AbstractCard card = (AbstractCard)c.getValue();
//            if(this.rarityColor==null) {
//                if (card.color.equals(color) && card.tags.contains(GkmasCardTag.IDOL_CARD_TAG))
//                    tmpPool.add(card);
//            }
//            else {
//                if (card.color.equals(color)&& card.tags.contains(GkmasCardTag.IDOL_CARD_TAG)){
//                    GkmasCard gkmasCard = (GkmasCard)card;
//                    if(gkmasCard.bannerColor.equals(this.rarityColor))
//                        tmpPool.add(card);
//                }
//            }
//        }
//    }

}
