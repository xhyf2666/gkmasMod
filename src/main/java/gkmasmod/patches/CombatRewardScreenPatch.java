package gkmasmod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import gkmasmod.cards.free.ProduceCompetitor;
import gkmasmod.cards.special.Kiss;
import gkmasmod.cards.special.Rumor;
import gkmasmod.cards.special.WorkFighter;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.bosses.AbstractIdolBoss;
import gkmasmod.powers.NegativeNotPower;
import gkmasmod.relics.FirstStarBracelet;
import gkmasmod.relics.PocketBook;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.screen.ThreeSizeChangeScreen;
import gkmasmod.utils.IdolData;

import java.util.ArrayList;

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
                    __instance.rewards.add(cardReward);
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