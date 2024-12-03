package gkmasmod.patches;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.downfall.bosses.*;
import gkmasmod.room.EventMonsterRoom;
import gkmasmod.room.FixedMonsterRoom;
import gkmasmod.dungeons.IdolRoad;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.ending.MisuzuBoss;
import gkmasmod.relics.PocketBook;
import gkmasmod.relics.ReChallenge;
import gkmasmod.relics.StruggleRecord;
import gkmasmod.room.shop.AnotherShopScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.PlayerHelper;
import gkmasmod.vfx.IdolRoadOPEffect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

public class AbstractDungeonPatch {
    @SpirePatch(clz = AbstractDungeon.class,method = SpirePatch.CONSTRUCTOR,paramtypez = {String.class, String.class, AbstractPlayer.class, ArrayList.class})
    public static class PostPatchAbstractDungeonConstructor {
        @SpirePostfixPatch
        public static void Postfix(AbstractDungeon __instance, String name, String levelId, AbstractPlayer p, ArrayList<String> newSpecialOneTimeEventList) {
            if(p instanceof IdolCharacter){
                IdolCharacter idol = (IdolCharacter) p;
                idol.idolData = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex);

                idol.skinIndex = SkinSelectScreen.Inst.skinIndex;
                idol.shoulderImg = ImageMaster.loadImage(String.format("gkmasModResource/img/idol/%s/stand/fire.png",idol.idolName));
                idol.shoulder2Img = ImageMaster.loadImage(String.format("gkmasModResource/img/idol/%s/stand/fire.png",idol.idolName));
                idol.corpseImg = ImageMaster.loadImage(String.format("gkmasModResource/img/idol/%s/stand/sleep_skin10.png",idol.idolName));
                if (SkinSelectScreen.Inst.videoPlayer != null) {
                    SkinSelectScreen.Inst.videoPlayer.dispose();
                    SkinSelectScreen.Inst.videoPlayer = null;
                }
                if(AbstractDungeon.id.equals(IdolRoad.ID)){
                    String videoPath = "gkmasModResource/video/other/op4.webm";
                    CardCrawlGame.fadeIn(1.0F);
                    CardCrawlGame.music.dispose();
                    if(Gdx.files.local(videoPath).exists()){
                        AbstractDungeon.topLevelEffectsQueue.add(new IdolRoadOPEffect(videoPath,true));
                    }
                    if(Gdx.files.internal(videoPath).exists()){
                        AbstractDungeon.topLevelEffectsQueue.add(new IdolRoadOPEffect(videoPath,false));
                    }
                }
            }
        }
    }

    @SpirePatch(clz = AbstractDungeon.class,method = SpirePatch.CONSTRUCTOR,paramtypez = {String.class, AbstractPlayer.class, SaveFile.class})
    public static class PostPatchAbstractDungeonConstructor2 {
        @SpirePostfixPatch
        public static void Postfix(AbstractDungeon __instance, String name, AbstractPlayer p, SaveFile saveFile) {
            if(p instanceof IdolCharacter){
                IdolCharacter idol = (IdolCharacter) p;
                idol.idolData = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex);
                idol.skinIndex = SkinSelectScreen.Inst.skinIndex;
                idol.shoulderImg = ImageMaster.loadImage(String.format("gkmasModResource/img/idol/%s/stand/fire.png",idol.idolName));
                idol.shoulder2Img = ImageMaster.loadImage(String.format("gkmasModResource/img/idol/%s/stand/fire.png",idol.idolName));
                idol.corpseImg = ImageMaster.loadImage(String.format("gkmasModResource/img/idol/%s/stand/sleep_skin10.png",idol.idolName));
            }
        }
    }

    @SpirePatch(clz = AbstractDungeon.class,method = SpirePatch.CONSTRUCTOR,paramtypez = {String.class, String.class, AbstractPlayer.class, ArrayList.class})
    public static class PrePatchAbstractDungeonConstructor {
        @SpirePrefixPatch
        public static void Prefix(AbstractDungeon __instance, String name, String levelId, AbstractPlayer p, ArrayList<String> newSpecialOneTimeEventList) {
            if(p instanceof IdolCharacter){
                p.title = SkinSelectScreen.Inst.curName;
            }
        }
    }

    @SpirePatch(clz = AbstractDungeon.class,method = SpirePatch.CONSTRUCTOR,paramtypez = {String.class, AbstractPlayer.class, SaveFile.class})
    public static class PrePatchAbstractDungeonConstructor2 {
        @SpirePrefixPatch
        public static void Prefix(AbstractDungeon __instance, String name, AbstractPlayer p, SaveFile saveFile) {
            if(p instanceof IdolCharacter){
                p.title = SkinSelectScreen.Inst.curName;
            }
        }
    }


    @SpirePatch(clz = AbstractDungeon.class,method = "generateMap")
    public static class PostPatchAbstractDungeon_generateMap {
        @SpirePostfixPatch
        public static void Postfix() {
            if(AbstractDungeon.name.equals(IdolRoad.ID)){
                return;
            }
            if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                GkmasMod.listeners.forEach(listener -> listener.onCardImgUpdate());
                Random spRng = new Random(Settings.seed, AbstractDungeon.actNum*100);
                float chance = 0.15f;
                if(AbstractDungeon.player.hasRelic(StruggleRecord.ID))
                    chance += 0.1f;
                chance += AbstractDungeon.actNum * 0.1f;
                if(AbstractDungeon.floorNum %17 >7)
                    chance += 0.05f;
                int row_num = AbstractDungeon.map.size() - 1;
                while(row_num >= 0) {
                    for(MapRoomNode n : AbstractDungeon.map.get(row_num)){
                        if(n.room instanceof MonsterRoom){
                            if(spRng.randomBoolean(chance)){
                                MapRoomNodePatch.SPField.isSP.set(n, true);
                            }
                        }
                    }
                    row_num--;
                }

            }
        }
    }

    @SpirePatch(clz = AbstractDungeon.class,method = "setBoss",paramtypez = {String.class})
    public static class PrePatchAbstractDungeon_setBoss {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractDungeon __instance, String key) {
            System.out.println("setBoss");
            if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                if (key!=null&&key.equals("The Heart")){
                    AbstractDungeon.bossKey = "MisuzuBoss";
                    if (DungeonMap.boss != null && DungeonMap.bossOutline != null) {
                        DungeonMap.boss.dispose();
                        DungeonMap.bossOutline.dispose();
                    }
                    DungeonMap.boss = ImageMaster.loadImage("gkmasModResource/img/monsters/Misuzu/icon.png");
                    DungeonMap.bossOutline = ImageMaster.loadImage("gkmasModResource/img/monsters/Misuzu/icon.png");
                    return SpireReturn.Return(null);
                }
            }
            if(AbstractDungeon.id.equals(IdolRoad.ID)){
                if (DungeonMap.boss != null && DungeonMap.bossOutline != null) {
                    DungeonMap.boss.dispose();
                    DungeonMap.bossOutline.dispose();
                    DungeonMap.boss = ImageMaster.loadImage("gkmasModResource/img/map/TrueEndIcon.png");
                    DungeonMap.bossOutline = ImageMaster.loadImage("gkmasModResource/img/map/TrueEndIcon.png");
                }
                DungeonMap.boss = ImageMaster.loadImage("gkmasModResource/img/monsters/Misuzu/icon.png");
                DungeonMap.bossOutline = ImageMaster.loadImage("gkmasModResource/img/monsters/Misuzu/icon.png");
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = AbstractDungeon.class,method = "generateSeeds")
    public static class PostPatchAbstractDungeon_generateSeeds {
        @SpirePostfixPatch
        public static void Postfix() {
            GkmasMod.threeSizeTagRng = new Random(Settings.seed);
        }
    }

    @SpirePatch(clz = MonsterHelper.class,method = "getEncounter")
    public static class PatchGetEncounter {
        @SpirePrefixPatch
        public static SpireReturn<MonsterGroup> Prefix(String key) {
            if (key!=null&&key.equals("MisuzuBoss")) {
                AbstractMonster customBoss = new MisuzuBoss(-50.0F, 15.0F);
                MonsterGroup customGroup = new MonsterGroup(customBoss);
                return SpireReturn.Return(customGroup);
            }
            if(AbstractDungeon.id.equals(IdolRoad.ID)){
                ArrayList<AbstractMonster> bossList = new ArrayList<>();
                bossList.add(new IdolBoss_amao());
                bossList.add(new IdolBoss_fktn());
                bossList.add(new IdolBoss_hrnm());
                bossList.add(new IdolBoss_hski());
                bossList.add(new IdolBoss_hume());
                bossList.add(new IdolBoss_shro());
                bossList.add(new IdolBoss_kcna());
                bossList.add(new IdolBoss_kllj());
                bossList.add(new IdolBoss_ssmk());
                bossList.add(new IdolBoss_ttmr());


                String curIdolName = "";
                if(AbstractDungeon.player instanceof IdolCharacter){
                    curIdolName = SkinSelectScreen.Inst.idolName;
                    curIdolName = String.format("IdolBoss_%s",curIdolName);
                }
                if(!curIdolName.equals("")){
                    for(int i=0;i<bossList.size();i++){
                        if(bossList.get(i).id.equals(curIdolName)){
                            bossList.remove(i);
                            break;
                        }
                    }
                }

                //使用Setting.seed作为随机数，打乱bosslist的顺序
                java.util.Random rng = new java.util.Random(Settings.seed);
                Collections.shuffle(bossList, rng);

                // 8进4的3位胜者
                int battle1_1 = rng.nextInt(2);
                int battle1_2 = rng.nextInt(2)+2;
                int battle1_3 = rng.nextInt(2)+5;

                // 4进2的1位胜者
                int battle2_1 = rng.nextInt(2);
                if(battle2_1 == 0){
                    battle2_1 = battle1_1;
                }else{
                    battle2_1 = battle1_2;
                }
                MonsterGroup customGroup = new MonsterGroup(bossList.get(battle2_1));
                AbstractDungeon.lastCombatMetricKey = bossList.get(battle2_1).id;
                return SpireReturn.Return(customGroup);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = AbstractDungeon.class,method = "closeCurrentScreen")
    public static class AbstractDungeonPrePatchGetEncounter {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix() {
            if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                if(GkmasMod.AnotherShopUp)
                    AbstractDungeon.previousScreen = AnotherShopScreen.Enum.AnotherShop_Screen;
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = AbstractDungeon.class,method = "getRewardCards")
    public static class PostPatchAbstractDungeon_getRewardCards {
        @SpireInsertPatch(rloc = 1866 - 1792, localvars = {"retVal2", "numCards"})
        public static void Insert(ArrayList<AbstractCard> retVal2, int numCards) {
            if (AbstractDungeon.getCurrRoom() instanceof FixedMonsterRoom) {
                FixedMonsterRoom room = (FixedMonsterRoom) AbstractDungeon.getCurrRoom();
//                room.rewards.clear();
                String tmp = room.encounterID;
                if (tmp.startsWith("IdolBoss_")) {
                    tmp = tmp.substring(9);
                }
                ArrayList<String> cards = IdolData.getIdol(tmp).getCardList();
                //从cards中随机选取numCards张卡牌,需要考虑不足numCards张的情况
                ArrayList<String> cardList = new ArrayList<>();

                java.util.Random random = new java.util.Random(Settings.seed);
                numCards = 5;
                for (int i = 0; i < numCards; i++) {
                    if (cards.size() > 0) {
                        int index = random.nextInt(cards.size());
                        cardList.add(cards.get(index));
                        cards.remove(index);
                    }
                }

                retVal2.clear();
                for (String cardId : cardList) {
                    AbstractCard card = CardLibrary.getCard(cardId).makeCopy();
                    card.upgrade();
                    retVal2.add(card);
                }
            }

            if (AbstractDungeon.getCurrRoom() instanceof EventMonsterRoom) {
                EventMonsterRoom room = (EventMonsterRoom) AbstractDungeon.getCurrRoom();
//                room.rewards.clear();
                String tmp = room.encounterID;
                if (tmp.startsWith("IdolBoss_")) {
                    tmp = tmp.substring(9);
                }
                ArrayList<String> cards = IdolData.getIdol(tmp).getCardList();
                //从cards中随机选取numCards张卡牌,需要考虑不足numCards张的情况
                ArrayList<String> cardList = new ArrayList<>();

                java.util.Random random = new java.util.Random(Settings.seed);
                numCards = 5;
                for (int i = 0; i < numCards; i++) {
                    if (cards.size() > 0) {
                        int index = random.nextInt(cards.size());
                        cardList.add(cards.get(index));
                        cards.remove(index);
                    }
                }

                retVal2.clear();
                for (String cardId : cardList) {
                    AbstractCard card = CardLibrary.getCard(cardId).makeCopy();
                    card.upgrade();
                    retVal2.add(card);
                }
            }

            if (AbstractDungeon.player.hasRelic(PocketBook.ID)) {
                if (retVal2.size() > 2) {
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(retVal2.get(0), 0);
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(retVal2.get(1), 1);
                    AbstractCardPatch.ThreeSizeTagField.threeSizeTag.set(retVal2.get(2), 2);
                }
            }
        }
    }



    @SpirePatch(clz = AbstractDungeon.class,method = "nextRoomTransition",paramtypez = {SaveFile.class})
    public static class PrePatchAbstractDungeon_nextRoomTransition {
        @SpireInsertPatch(rloc = 2210-2126)
        public static void Insert(AbstractDungeon __instance,SaveFile saveFile) {
            if(AbstractDungeon.player.hasRelic(ReChallenge.ID)){
                Properties defaults = new Properties();
                defaults.setProperty("cardRate", String.valueOf(PlayerHelper.getCardRate()));
                defaults.setProperty("beat_hmsz", "0");
                defaults.setProperty("ReChallenge", "1");
                SpireConfig config = null;
                try {
                    config = new SpireConfig("GkmasMod", "config", defaults);
                    int tmp = config.getInt("ReChallenge");
                    if(tmp>0){
                        AbstractDungeon.monsterHpRng = new Random(Long.valueOf(Settings.seed.longValue() + AbstractDungeon.floorNum+tmp*100));
                        AbstractDungeon.aiRng = new Random(Long.valueOf(Settings.seed.longValue() + AbstractDungeon.floorNum+tmp*100));
                        AbstractDungeon.shuffleRng = new Random(Long.valueOf(Settings.seed.longValue() + AbstractDungeon.floorNum+tmp*100));
                        AbstractDungeon.cardRandomRng = new Random(Long.valueOf(Settings.seed.longValue() + AbstractDungeon.floorNum+tmp*100));
                        AbstractDungeon.miscRng = new Random(Long.valueOf(Settings.seed.longValue() + AbstractDungeon.floorNum+tmp*100));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
