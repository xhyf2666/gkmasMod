package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.utils.CommonEnum;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class GashaAction extends AbstractGameAction {
    private AbstractPlayer p;
    private boolean isUpgraded;
    private String rarityColor;
    private boolean playInstantly = false;

    public GashaAction(AbstractPlayer p,boolean isUpgraded) {
        this(p,isUpgraded, null,false);
    }

    public GashaAction(AbstractPlayer p, boolean isUpgraded, String rarityColor,boolean playInstantly) {
        this.p = p;
        this.isUpgraded = isUpgraded;
        this.rarityColor = rarityColor;
        this.playInstantly = playInstantly;
    }

    public void update() {
        ArrayList<AbstractCard> tmpPool = new ArrayList<>();
        if(p instanceof IdolCharacter){
            IdolCharacter idol = (IdolCharacter)p;
            CommonEnum.IdolType type = idol.idolData.getType(idol.skinIndex);
            if(type == CommonEnum.IdolType.LOGIC){
                getRandomCard(PlayerColorEnum.gkmasModColorLogic,tmpPool);
            }
            else if(type == CommonEnum.IdolType.SENSE){
                getRandomCard(PlayerColorEnum.gkmasModColorSense,tmpPool);
            }
            else if(type == CommonEnum.IdolType.ANOMALY){
                getRandomCard(PlayerColorEnum.gkmasModColorAnomaly,tmpPool);
            }
        }
        else if(p instanceof MisuzuCharacter){
            getRandomCard(PlayerColorEnum.gkmasModColorSense,tmpPool);
        }
        else{
            getRandomCard(PlayerColorEnum.gkmasModColorLogic,tmpPool);
            getRandomCard(PlayerColorEnum.gkmasModColorSense,tmpPool);
            getRandomCard(PlayerColorEnum.gkmasModColorAnomaly,tmpPool);
        }
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

    public ArrayList<AbstractCard> getRandomCard(AbstractCard.CardColor color,ArrayList<AbstractCard> tmpPool) {
        Iterator<Map.Entry<String, AbstractCard>> cardLib = CardLibrary.cards.entrySet().iterator();
        while (true) {
            if (!cardLib.hasNext())
                return tmpPool;
            Map.Entry c = cardLib.next();
            AbstractCard card = (AbstractCard)c.getValue();
            if(this.rarityColor==null) {
                if (card.color.equals(color) && card.tags.contains(GkmasCardTag.IDOL_CARD_TAG))
                    tmpPool.add(card);
            }
            else {
                if (card.color.equals(color)&& card.tags.contains(GkmasCardTag.IDOL_CARD_TAG)){
                    GkmasCard gkmasCard = (GkmasCard)card;
                    if(gkmasCard.bannerColor.equals(this.rarityColor))
                        tmpPool.add(card);
                }
            }
        }
    }

}
