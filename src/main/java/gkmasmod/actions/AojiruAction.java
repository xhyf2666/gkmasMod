package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.characters.OtherIdolCharacter;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.utils.CommonEnum;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class AojiruAction extends AbstractGameAction {
    private AbstractPlayer p;
    private boolean isUpgraded;
    private AbstractCard.CardRarity rarity;

    /**
     * 青汁Action：生成随机卡到手牌
     * @param isUpgraded 是否升级
     * @param rarity 卡牌稀有度
     */
    public AojiruAction(AbstractPlayer p, boolean isUpgraded, AbstractCard.CardRarity rarity) {
        this.p = p;
        this.isUpgraded = isUpgraded;
        this.rarity = rarity;
    }

    public void update() {
        ArrayList<AbstractCard> tmpPool = new ArrayList<>();
        if(p instanceof IdolCharacter){
            IdolCharacter idol = (IdolCharacter)p;
            CommonEnum.IdolType type = idol.idolData.getType(idol.skinIndex);
            getRandomCard(PlayerColorEnum.gkmasModColor,tmpPool);
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
        else if(p instanceof OtherIdolCharacter){
            OtherIdolCharacter idol = (OtherIdolCharacter)p;
            CommonEnum.IdolType type = idol.idolData.getType(idol.skinIndex);
            getRandomCard(PlayerColorEnum.gkmasModColor,tmpPool);
            if(type == CommonEnum.IdolType.LOGIC){
                getRandomCard(PlayerColorEnum.gkmasModColorLogic,tmpPool);
            }
            else if(type == CommonEnum.IdolType.SENSE){
                getRandomCard(PlayerColorEnum.gkmasModColorSense,tmpPool);
            }
            else if(type == CommonEnum.IdolType.ANOMALY){
                getRandomCard(PlayerColorEnum.gkmasModColorAnomaly,tmpPool);
            }
            else if(type== CommonEnum.IdolType.PRODUCE){
                getRandomCard(PlayerColorEnum.gkmasModColorLogic,tmpPool);
                getRandomCard(PlayerColorEnum.gkmasModColorSense,tmpPool);
                getRandomCard(PlayerColorEnum.gkmasModColorAnomaly,tmpPool);
            }
        }
        else if(p instanceof MisuzuCharacter){
            getRandomCard(PlayerColorEnum.gkmasModColorMisuzu,tmpPool);
        }
        else{
            getRandomCard(PlayerColorEnum.gkmasModColorLogic,tmpPool);
            getRandomCard(PlayerColorEnum.gkmasModColorSense,tmpPool);
            getRandomCard(PlayerColorEnum.gkmasModColorAnomaly,tmpPool);
        }
        AbstractCard card = tmpPool.get(AbstractDungeon.cardRandomRng.random(0,tmpPool.size()-1));
        if(isUpgraded)
            card.upgrade();
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
            if(this.rarity==null){
                if (card.color.equals(color) && !card.tags.contains(GkmasCardTag.IDOL_CARD_TAG) && card.rarity != AbstractCard.CardRarity.BASIC && card.rarity != AbstractCard.CardRarity.SPECIAL && card.rarity != AbstractCard.CardRarity.CURSE)
                    tmpPool.add(card);
            }
            else{
                if (card.color.equals(color) && !card.tags.contains(GkmasCardTag.IDOL_CARD_TAG) && card.rarity == this.rarity)
                    tmpPool.add(card);
            }
        }
    }

}
