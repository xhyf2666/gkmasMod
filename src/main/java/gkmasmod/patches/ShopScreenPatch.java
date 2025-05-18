package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StorePotion;
import com.megacrit.cardcrawl.shop.StoreRelic;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.relics.FreeTicket;
import gkmasmod.relics.MysteriousObject;
import gkmasmod.relics.OverpoweredBall;

import java.util.ArrayList;

public class ShopScreenPatch {
    @SpirePatch(clz = ShopScreen.class,method = "getBuyMsg")
    public static class PrePatchShopScreen_getBuyMsg {
        @SpirePrefixPatch
        public static void Prefix() {
            if(AbstractDungeon.player.hasRelic(OverpoweredBall.ID)){
                ((OverpoweredBall)AbstractDungeon.player.getRelic(OverpoweredBall.ID)).onBuySomething();
            }
        }
    }

    @SpirePatch(clz = ShopScreen.class,method = "init")
    public static class PostPatchShopScreen_init {
        @SpireInsertPatch(rloc = 110,localvars = {"coloredCards","colorlessCards"})
        public static void Insert(ShopScreen __instance,ArrayList<AbstractCard> ___coloredCards,ArrayList<AbstractCard> ___colorlessCards,ArrayList<StoreRelic> ___relics,ArrayList<StorePotion> ___potions, ArrayList<AbstractCard> coloredCards, ArrayList<AbstractCard> colorlessCards) {
            if(AbstractDungeon.player.hasRelic(FreeTicket.ID)){
                int relicCount = 0;
                int potionCount = 0;
                int cardCount1 = 0;
                int cardCount2 = 0;
                int purgeCount = 0;
                relicCount = ___relics.size();
                potionCount = ___potions.size();
                cardCount1 = ___coloredCards.size();
                cardCount2 = ___colorlessCards.size();
                purgeCount = 1;
                com.megacrit.cardcrawl.random.Random spRng = new com.megacrit.cardcrawl.random.Random(Settings.seed, AbstractDungeon.floorNum*100);
                int roll = spRng.random(0,relicCount + potionCount + cardCount1 + cardCount2 + purgeCount-1);
                if(roll < relicCount){
                    ___relics.get(roll).price = 0;
                }else if(roll < relicCount + potionCount){
                    ___potions.get(roll-relicCount).price = 0;
                }
                else if(roll < relicCount + potionCount + cardCount1){
                    ___coloredCards.get(roll-relicCount-potionCount).price = 0;
                }
                else if(roll < relicCount + potionCount + cardCount1 + cardCount2){
                    ___colorlessCards.get(roll-relicCount-potionCount-cardCount1).price = 0;
                }
                else{
                    __instance.actualPurgeCost = 0;
                }
            }
        }
    }

    @SpirePatch(clz = ShopScreen.class,method = "initCards")
    public static class InsertPatchShopScreen_initCards {
        @SpireInsertPatch(rloc = 265-255,localvars = {"i","tmpPrice"})
        public static void Insert(ShopScreen __instance,int i,@ByRef float[] tmpPrice) {
            AbstractCard card = __instance.coloredCards.get(i);
            if(card instanceof GkmasCard &&card.hasTag(GkmasCardTag.IDOL_CARD_TAG)){
                GkmasCard gkmasCard = (GkmasCard)card;
                AbstractDungeon.merchantRng.setCounter(AbstractDungeon.merchantRng.counter-1);
                switch (gkmasCard.bannerColor){
                    case "blue":
                        tmpPrice[0] = (float)AbstractCard.getPrice(AbstractCard.CardRarity.COMMON) * AbstractDungeon.merchantRng.random(0.9F, 1.1F);
                        break;
                    case "yellow":
                        tmpPrice[0] = (float)AbstractCard.getPrice(AbstractCard.CardRarity.UNCOMMON) * AbstractDungeon.merchantRng.random(0.9F, 1.1F);
                        break;
                    case "color":
                        tmpPrice[0] = (float)AbstractCard.getPrice(AbstractCard.CardRarity.RARE) * AbstractDungeon.merchantRng.random(0.9F, 1.1F);
                        break;
                }
            }
        }
    }

    @SpirePatch(clz = ShopScreen.class,method = "setPrice")
    public static class InsertPatchShopScreen_setPrice {
        @SpireInsertPatch(rloc = 782-777,localvars = {"tmpPrice"})
        public static void Insert(ShopScreen __instance,AbstractCard card,@ByRef float[] tmpPrice) {
            if(card instanceof GkmasCard &&card.hasTag(GkmasCardTag.IDOL_CARD_TAG)){
                GkmasCard gkmasCard = (GkmasCard)card;
                AbstractDungeon.merchantRng.setCounter(AbstractDungeon.merchantRng.counter-1);
                switch (gkmasCard.bannerColor){
                    case "blue":
                        tmpPrice[0] = (float)AbstractCard.getPrice(AbstractCard.CardRarity.COMMON) * AbstractDungeon.merchantRng.random(0.9F, 1.1F);
                        break;
                    case "yellow":
                        tmpPrice[0] = (float)AbstractCard.getPrice(AbstractCard.CardRarity.UNCOMMON) * AbstractDungeon.merchantRng.random(0.9F, 1.1F);
                        break;
                    case "color":
                        tmpPrice[0] = (float)AbstractCard.getPrice(AbstractCard.CardRarity.RARE) * AbstractDungeon.merchantRng.random(0.9F, 1.1F);
                        break;
                }
            }
        }
    }


}
