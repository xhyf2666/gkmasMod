package gkmasmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import gkmasmod.cards.idol.MasterKey;
import gkmasmod.cards.idol.ProduceCompetitor;
import gkmasmod.cards.special.Kiss;
import gkmasmod.cards.special.Rumor;
import gkmasmod.cards.special.WorkFighter;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.characters.OtherIdolCharacter;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.relics.FirstStarBracelet;
import gkmasmod.relics.PocketBook;
import gkmasmod.relics.ProducerBaseSkill;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.screen.ThreeSizeChangeScreen;
import gkmasmod.utils.CardHelper;
import gkmasmod.utils.IdolData;

import java.util.ArrayList;
import java.util.Iterator;

public class CombatRewardScreenPatch {

    /**
     * 设置1层和2层Boss战的额外奖励
     */
    @SpirePatch(clz = CombatRewardScreen.class,method = "setupItemReward")
    public static class PostPatchCombatRewardScreen_setupItemReward {
        @SpirePostfixPatch
        public static void Postfix(CombatRewardScreen __instance) {
            if (AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoomBoss && AbstractDungeon.actNum == 1 && (AbstractDungeon.player instanceof IdolCharacter || AbstractDungeon.player instanceof MisuzuCharacter)) {
                if (AbstractDungeon.player instanceof MisuzuCharacter) {
                    RewardItem cardReward = new RewardItem();
                    cardReward.cards.clear();
                    cardReward.cards.add(new Kiss());
                    cardReward.cards.add(new Rumor());
                    cardReward.cards.add(new WorkFighter());
                    cardReward.cards.add(new MasterKey());
                    __instance.rewards.add(cardReward);
                }
                else if(AbstractDungeon.player instanceof OtherIdolCharacter){

                }
                else{
                    RewardItem cardReward = new RewardItem();
                    cardReward.cards.clear();
                    if(SkinSelectScreen.Inst.idolName.equals(IdolData.jsna)){
                        AbstractCard card = CardLibrary.getCard(ProduceCompetitor.ID);
                        cardReward.cards.add(card);
                    }
                    else{
                        String[] specialCards= IdolData.getIdol(SkinSelectScreen.Inst.idolName).getBossRewards();
                        for(String s : specialCards){
                            AbstractCard card = CardLibrary.getCard(s);
                            cardReward.cards.add(card);
                        }
                    }
                    __instance.rewards.add(cardReward);
                }
            }
            if (AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoomBoss && AbstractDungeon.actNum == 2 && (AbstractDungeon.player instanceof IdolCharacter || AbstractDungeon.player instanceof MisuzuCharacter)) {
                if (AbstractDungeon.player instanceof MisuzuCharacter){
                    RewardItem cardReward = new RewardItem();
                    cardReward.cards.clear();
                    ArrayList<String> cards = IdolData.getIdol(IdolData.ttmr).getCardListExcept("");
                    java.util.Random random = new java.util.Random(Settings.seed+AbstractDungeon.floorNum);
                    ArrayList<String> cardList = new ArrayList<>();
                    int numCards = 4;
                    for (int i = 0; i < numCards; i++) {
                        if (cards.size() > 0) {
                            int index = random.nextInt(cards.size());
                            cardList.add(cards.get(index));
                            cards.remove(index);
                        }
                    }
                    for(String s : cardList){
                        AbstractCard card = CardLibrary.getCard(s);
                        card.upgrade();
                        cardReward.cards.add(card);
                    }
                    __instance.rewards.add(cardReward);
                }
                else if(AbstractDungeon.player instanceof OtherIdolCharacter){

                }
                else{
                    RewardItem cardReward = new RewardItem();
                    cardReward.cards.clear();
                    ArrayList<String> cards = IdolData.getIdol(SkinSelectScreen.Inst.idolName).getCardListExcept(IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getCard(SkinSelectScreen.Inst.skinIndex));
                    java.util.Random random = new java.util.Random(Settings.seed+AbstractDungeon.floorNum);
                    ArrayList<String> cardList = new ArrayList<>();
                    int numCards = 4;
                    for (int i = 0; i < numCards; i++) {
                        if (cards.size() > 0) {
                            int index = random.nextInt(cards.size());
                            cardList.add(cards.get(index));
                            cards.remove(index);
                        }
                    }
                    for(String s : cardList){
                        AbstractCard card = CardLibrary.getCard(s);
                        card.upgrade();
                        cardReward.cards.add(card);
                    }
                    __instance.rewards.add(cardReward);
                }
            }
        }
    }


    @SpirePatch(clz = CombatRewardScreen.class,method = "setupItemReward")
    public static class InsertPatchCombatRewardScreen_setupItemReward {
        @SpireInsertPatch(rloc = 93-72)
        public static void Insert(CombatRewardScreen __instance) {
            if(AbstractDungeon.player.hasRelic(ProducerBaseSkill.ID)){
                if(!(AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoomElite) &&
                        !(AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoomBoss)){
                    return;
                }
                RewardItem cardReward = new RewardItem();
                cardReward.cards.clear();
                ArrayList<AbstractCard> cards = getProducerRewardCards();
                for(AbstractCard card : cards) {
                    cardReward.cards.add(card);
                }
                __instance.rewards.add(cardReward);
            }
        }
    }

    public static ArrayList<AbstractCard> getProducerRewardCards() {
        ArrayList<AbstractCard> retVal = new ArrayList();
        int numCards = 3;

        AbstractCard card;
        for(int i = 0; i < numCards; ++i) {
            AbstractCard.CardRarity rarity = AbstractDungeon.rollRarity();
            card = null;
            switch(rarity) {
                case COMMON:
                    AbstractDungeon.cardBlizzRandomizer -= AbstractDungeon.cardBlizzGrowth;
                    if (AbstractDungeon.cardBlizzRandomizer <= AbstractDungeon.cardBlizzMaxOffset) {
                        AbstractDungeon.cardBlizzRandomizer = AbstractDungeon.cardBlizzMaxOffset;
                    }
                case UNCOMMON:
                    break;
                case RARE:
                    AbstractDungeon.cardBlizzRandomizer = AbstractDungeon.cardBlizzStartOffset;
                    break;
            }

            boolean containsDupe = true;

            while(true) {
                while(containsDupe) {
                    containsDupe = false;
                    switch (i){
                        case 0:
                            card = CardHelper.getOneCard(PlayerColorEnum.gkmasModColorLogic,rarity);
                            break;
                        case 1:
                            card = CardHelper.getOneCard(PlayerColorEnum.gkmasModColorSense,rarity);
                            break;
                        case 2:
                            card = CardHelper.getOneCard(PlayerColorEnum.gkmasModColorAnomaly,rarity);
                            break;
                        default:
                            card = CardHelper.getOneCard(PlayerColorEnum.gkmasModColorMisuzu,rarity);
                            break;
                    }

                    Iterator var6 = retVal.iterator();

                    while(var6.hasNext()) {
                        AbstractCard c = (AbstractCard)var6.next();
                        if (c.cardID.equals(card.cardID)) {
                            containsDupe = true;
                            break;
                        }
                    }
                }

                if (card != null) {
                    retVal.add(card);
                }
                break;
            }
        }

        ArrayList<AbstractCard> retVal2 = new ArrayList();
        Iterator var11 = retVal.iterator();

        while(var11.hasNext()) {
            card = (AbstractCard)var11.next();
            retVal2.add(card.makeCopy());
        }

        var11 = retVal2.iterator();

        while(true) {
            while(var11.hasNext()) {
                card = (AbstractCard)var11.next();
                if (card.rarity != AbstractCard.CardRarity.RARE && AbstractDungeon.cardRng.randomBoolean(0.3F) && card.canUpgrade()) {
                    card.upgrade();
                } else {
                    Iterator var12 = AbstractDungeon.player.relics.iterator();
                    while(var12.hasNext()) {
                        AbstractRelic r = (AbstractRelic)var12.next();
                        r.onPreviewObtainCard(card);
                    }
                }
            }
            return retVal2;
        }
    }

    /**
     * 战后获得三维时，显示三维变化
     */
    @SpirePatch(clz = CombatRewardScreen.class, method = "update")
    public static class PrePatchCombatRewardScreenPatch_update {
        public static void Prefix(CombatRewardScreen _inst) {
            if (AbstractDungeon.player.hasRelic(PocketBook.ID)){
                if(ThreeSizeChangeScreen.VoInst != null)
                    ThreeSizeChangeScreen.VoInst.update();
                if(ThreeSizeChangeScreen.DaInst != null)
                    ThreeSizeChangeScreen.DaInst.update();
                if(ThreeSizeChangeScreen.ViInst != null)
                    ThreeSizeChangeScreen.ViInst.update();
            }
        }
    }

    /**
     * 战后获得三维时，显示三维变化
     */
    @SpirePatch(clz = CombatRewardScreen.class, method = "render")
    public static class PostPatchCombatRewardScreen_render {
        public static void Postfix(CombatRewardScreen _inst, SpriteBatch sb) {
            if (AbstractDungeon.player.hasRelic(PocketBook.ID)){
                if(ThreeSizeChangeScreen.VoInst != null)
                    ThreeSizeChangeScreen.VoInst.render(sb);
                if(ThreeSizeChangeScreen.DaInst != null)
                    ThreeSizeChangeScreen.DaInst.render(sb);
                if(ThreeSizeChangeScreen.ViInst != null)
                    ThreeSizeChangeScreen.ViInst.render(sb);
            }
        }
    }

    @SpirePatch(clz = CombatRewardScreen.class, method = "open", paramtypez = {String.class})
    public static class PostPatchCombatRewardScreen_open {
        public static void Postfix(CombatRewardScreen _inst) {

        }
    }

    /**
     * SP战后，触发遗物 初星手镯 的效果
     */
    @SpirePatch(clz = CombatRewardScreen.class, method = "open", paramtypez = {})
    public static class PostPatchCombatRewardScreen_open2 {
        public static void Postfix(CombatRewardScreen _inst) {
            if(AbstractDungeon.player.hasRelic(FirstStarBracelet.ID)){
                FirstStarBracelet relic = (FirstStarBracelet)AbstractDungeon.player.getRelic(FirstStarBracelet.ID);
                relic.afterVictory();
            }
        }
    }

    @SpirePatch(clz = CombatRewardScreen.class, method = "openCombat", paramtypez = {String.class, boolean.class})
    public static class PostPatchCombatRewardScreen_openCombat {
        public static void Postfix(CombatRewardScreen _inst) {
        }
    }
}