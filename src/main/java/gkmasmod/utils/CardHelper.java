package gkmasmod.utils;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.characters.OtherIdolCharacter;
import gkmasmod.characters.PlayerColorEnum;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class CardHelper {

    /**
     * 获取所有偶像卡
     */
    public static ArrayList<AbstractCard> getAllIdolCards() {
        return getAllIdolCards(null,"", "");
    }

    /**
     * 指定稀有度，获取所有偶像卡
     * @param rarityColor 稀有度
     */
    public static ArrayList<AbstractCard> getAllIdolCards(String rarityColor) {
        return getAllIdolCards(null,rarityColor, "");
    }

    /**
     * 指定稀有度和偶像，获取所有偶像卡
     * @param rarityColor 稀有度
     * @param idolColor 偶像
     */
    public static ArrayList<AbstractCard> getAllIdolCards(String rarityColor, String idolColor) {
        return getAllIdolCards(null, rarityColor, idolColor);
    }

    /**
     * 指定职业，稀有度和偶像，获取所有偶像卡
     * @param cardColor 职业
     * @param rarityColor 稀有度
     * @param idolColor 偶像
     */
    public static ArrayList<AbstractCard> getAllIdolCards(ArrayList<AbstractCard.CardColor> cardColor, String rarityColor, String idolColor) {
        ArrayList<AbstractCard> tmpPool = new ArrayList<>();
        if(cardColor!=null){
            for (AbstractCard.CardColor color : cardColor) {
                getRandomCard(color,tmpPool,rarityColor,idolColor);
            }
        }
        else{
            AbstractPlayer p = AbstractDungeon.player;
            if(p instanceof IdolCharacter){
                IdolCharacter idol = (IdolCharacter)p;
                CommonEnum.IdolType type = idol.idolData.getType(idol.skinIndex);
                if(type == CommonEnum.IdolType.LOGIC){
                    getRandomCard(PlayerColorEnum.gkmasModColorLogic,tmpPool,rarityColor,idolColor);
                }
                else if(type == CommonEnum.IdolType.SENSE){
                    getRandomCard(PlayerColorEnum.gkmasModColorSense,tmpPool,rarityColor,idolColor);
                }
                else if(type == CommonEnum.IdolType.ANOMALY){
                    getRandomCard(PlayerColorEnum.gkmasModColorAnomaly,tmpPool,rarityColor,idolColor);
                }
            }
            else if(p instanceof OtherIdolCharacter){
                OtherIdolCharacter idol = (OtherIdolCharacter)p;
                CommonEnum.IdolType type = idol.idolData.getType(idol.skinIndex);
                if(type == CommonEnum.IdolType.LOGIC){
                    getRandomCard(PlayerColorEnum.gkmasModColorLogic,tmpPool,rarityColor,idolColor);
                }
                else if(type == CommonEnum.IdolType.SENSE){
                    getRandomCard(PlayerColorEnum.gkmasModColorSense,tmpPool,rarityColor,idolColor);
                }
                else if(type == CommonEnum.IdolType.ANOMALY){
                    getRandomCard(PlayerColorEnum.gkmasModColorAnomaly,tmpPool,rarityColor,idolColor);
                }
                else if(type== CommonEnum.IdolType.PRODUCE){
                    getRandomCard(PlayerColorEnum.gkmasModColorLogic,tmpPool,rarityColor,idolColor);
                    getRandomCard(PlayerColorEnum.gkmasModColorSense,tmpPool,rarityColor,idolColor);
                    getRandomCard(PlayerColorEnum.gkmasModColorAnomaly,tmpPool,rarityColor,idolColor);
                }
            }
            else if(p instanceof MisuzuCharacter){
                getRandomCard(PlayerColorEnum.gkmasModColorSense,tmpPool,rarityColor,idolColor);
            }
            else{
                getRandomCard(PlayerColorEnum.gkmasModColorLogic,tmpPool,rarityColor,idolColor);
                getRandomCard(PlayerColorEnum.gkmasModColorSense,tmpPool,rarityColor,idolColor);
                getRandomCard(PlayerColorEnum.gkmasModColorAnomaly,tmpPool,rarityColor,idolColor);
            }
        }
        return tmpPool;
    }

    public static ArrayList<AbstractCard> getRandomCard(AbstractCard.CardColor color, ArrayList<AbstractCard> tmpPool,String rarityColor,String idolColor) {
        Iterator<Map.Entry<String, AbstractCard>> cardLib = CardLibrary.cards.entrySet().iterator();
        while (true) {
            if (!cardLib.hasNext())
                return tmpPool;
            Map.Entry c = cardLib.next();
            AbstractCard _card = (AbstractCard)c.getValue();
            if(!(_card instanceof GkmasCard))
                continue;
            GkmasCard card = (GkmasCard)_card;
            if ((rarityColor.equals("")||rarityColor.equals(card.bannerColor))&&(idolColor.equals("")||idolColor.equals(card.backGroundColor))&&card.color.equals(color) && card.tags.contains(GkmasCardTag.IDOL_CARD_TAG))
                tmpPool.add(card.makeCopy());
        }
    }
}
