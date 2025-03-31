package gkmasmod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.relics.BalanceLogicAndSense;
import javassist.CtBehavior;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.Map;

public class GetCardPatch {
    @SpirePatch2(clz = AbstractDungeon.class, method = "getCard", paramtypez = {AbstractCard.CardRarity.class})
    public static class getCardPrefixPatch {
        @SpirePrefixPatch
        public static SpireReturn<AbstractCard> prefix(AbstractCard.CardRarity rarity){
            // 感理之衡
            if (AbstractDungeon.player!=null&&AbstractDungeon.player.hasRelic(BalanceLogicAndSense.ID)){
                return SpireReturn.Return(getAnyGkmasCard(rarity));
            }
            return SpireReturn.Continue();
        }
    }


    public static AbstractCard getAnyGkmasCard(AbstractCard.CardRarity rarity) {
        CardGroup anyCard = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
            if (isGkmasColor(c.getValue())&&(c.getValue()).rarity == rarity && (c.getValue()).type != AbstractCard.CardType.CURSE && (c
                    .getValue()).type != AbstractCard.CardType.STATUS && (!UnlockTracker.isCardLocked(c.getKey()) ||
                    Settings.treatEverythingAsUnlocked()))
                anyCard.addToBottom(c.getValue());
        }
        anyCard.shuffle(AbstractDungeon.cardRng);
        return anyCard.getRandomCard(true, rarity).makeCopy();
    }

    public static boolean isGkmasColor(AbstractCard card) {
        return card.color==AbstractDungeon.player.getCardColor()||card.color == PlayerColorEnum.gkmasModColor || card.color == PlayerColorEnum.gkmasModColorLogic || card.color == PlayerColorEnum.gkmasModColorSense|| card.color == PlayerColorEnum.gkmasModColorAnomaly;
    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "getRewardCards")
    public static class getRewardCardsInsertPatch {
        @SpireInsertPatch(rloc = 1837-1792,localvars = {"card","containsDupe"})
        public static SpireReturn<Void> insert(AbstractCard card,@ByRef boolean[] containsDupe){
            if(card!=null&&card.hasTag(GkmasCardTag.ONLY_ONE_TAG)){
                for(AbstractCard c:AbstractDungeon.player.masterDeck.group) {
                    if (c.cardID.equals(card.cardID)) {
                        containsDupe[0] = true;
                        return SpireReturn.Continue();
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "getCardFromPool", paramtypez = {AbstractCard.CardRarity.class,AbstractCard.CardType.class,boolean.class})
    public static class getCardFromPoolInsertPatch {
        @SpireInsertPatch(rlocs={4,10,22},localvars = {"retVal"})
        public static SpireReturn<AbstractCard> postfix(AbstractCard.CardRarity rarity, AbstractCard.CardType type, boolean useRng,AbstractCard retVal){
            if(retVal!=null&&retVal.hasTag(GkmasCardTag.ONLY_ONE_TAG)){
                for(AbstractCard c:AbstractDungeon.player.masterDeck.group) {
                    if (c.cardID.equals(retVal.cardID)) {
                        return SpireReturn.Return(AbstractDungeon.getCardFromPool(rarity,type,useRng));
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }
}
