//package gkmasmod.utils;
//
//import basemod.ReflectionHacks;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.evacipated.cardcrawl.modthespire.lib.*;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.CardGroup;
//import com.megacrit.cardcrawl.core.CardCrawlGame;
//import com.megacrit.cardcrawl.core.Settings;
//import com.megacrit.cardcrawl.helpers.CardLibrary;
//import com.megacrit.cardcrawl.helpers.FontHelper;
//import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
//import com.megacrit.cardcrawl.unlock.UnlockTracker;
//import gkmasmod.characters.PlayerColorEnum;
//import gkmasmod.ui.SkinSelectScreen;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import javassist.CtBehavior;
//import org.lwjgl.Sys;
//
//import java.util.ArrayList;
//import java.util.Map;
//
//public class GetCardPatch {
//    public static boolean isGkmasMod() {
//        return (CardCrawlGame.chosenCharacter == PlayerColorEnum.gkmasMod_character);
//    }
//
//    @SpirePatch2(clz = AbstractDungeon.class, method = "getRewardCards")
//    public static class getRewardCardsInsertPatch {
//        @SpireInsertPatch(locator = Locator.class , localvars = {"rarity","card"})
//        public static void Insert(AbstractCard.CardRarity rarity,@ByRef AbstractCard[] card){
//            if (GetCardPatch.isGkmasMod()){
//                card[0] = getAnyGkmasCard(rarity);
//            }
//        }
//    }
//
//    private static class Locator extends SpireInsertLocator {
//        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
//            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "getCard");
//            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList(), (Matcher)methodCallMatcher);
//        }
//    }
//
//
//    public static AbstractCard getAnyGkmasCard(AbstractCard.CardRarity rarity) {
//        CardGroup anyCard = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
//        for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
//            if (isGkmasColor((AbstractCard)c.getValue())&&((AbstractCard)c.getValue()).rarity == rarity && ((AbstractCard)c.getValue()).type != AbstractCard.CardType.CURSE && ((AbstractCard)c
//                    .getValue()).type != AbstractCard.CardType.STATUS && (!UnlockTracker.isCardLocked(c.getKey()) ||
//                    Settings.treatEverythingAsUnlocked()))
//                anyCard.addToBottom(c.getValue());
//        }
//        anyCard.shuffle(AbstractDungeon.cardRng);
//        return anyCard.getRandomCard(true, rarity).makeCopy();
//    }
//
//    public static boolean isGkmasColor(AbstractCard card) {
//        return card.color == PlayerColorEnum.gkmasModColor || card.color == PlayerColorEnum.gkmasModColorLogic || card.color == PlayerColorEnum.gkmasModColorSense;
//    }
//}
