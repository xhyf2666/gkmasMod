package gkmasmod.patches;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
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
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.characters.OtherIdolCharacter;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.bosses.*;
import gkmasmod.monster.ending.MonsterRinha;
import gkmasmod.relics.*;
import gkmasmod.room.EventMonsterRoom;
import gkmasmod.room.FixedMonsterRoom;
import gkmasmod.dungeons.IdolRoad;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.ending.MisuzuBoss;
import gkmasmod.room.shop.AnotherShopScreen;
import gkmasmod.room.specialTeach.SpecialTeachScreen;
import gkmasmod.screen.OtherSkinSelectScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.PlayerHelper;
import gkmasmod.vfx.IdolRoadOPEffect;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.lwjgl.Sys;

import java.io.IOException;
import java.util.*;

public class AbstractDungeonPatch {

    /**
     * 进入新地图时，初始化偶像数据。 进入偶像之路时，播放视频
     */
    @SpirePatch(clz = AbstractDungeon.class,method = SpirePatch.CONSTRUCTOR,paramtypez = {String.class, String.class, AbstractPlayer.class, ArrayList.class})
    public static class PostPatchAbstractDungeon_Constructor {
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
            else if(p instanceof OtherIdolCharacter){
                OtherIdolCharacter idol = (OtherIdolCharacter) p;
                idol.idolData = IdolData.getOtherIdol(OtherSkinSelectScreen.Inst.idolIndex);

                idol.skinIndex = OtherSkinSelectScreen.Inst.skinIndex;
                idol.shoulderImg = ImageMaster.loadImage(String.format("gkmasModResource/img/idol/othe/%s/stand/fire.png",idol.idolName));
                idol.shoulder2Img = ImageMaster.loadImage(String.format("gkmasModResource/img/idol/othe/%s/stand/fire.png",idol.idolName));
                idol.corpseImg = ImageMaster.loadImage(String.format("gkmasModResource/img/idol/othe/%s/stand/sleep_skin10.png",idol.idolName));
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
            else if(p instanceof MisuzuCharacter){
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

    /**
     * 从存档加载数据时，初始化偶像数据
     */
    @SpirePatch(clz = AbstractDungeon.class,method = SpirePatch.CONSTRUCTOR,paramtypez = {String.class, AbstractPlayer.class, SaveFile.class})
    public static class PostPatchAbstractDungeon_Constructor2 {
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
            else if(p instanceof OtherIdolCharacter){
                OtherIdolCharacter idol = (OtherIdolCharacter) p;
                idol.idolData = IdolData.getOtherIdol(OtherSkinSelectScreen.Inst.idolIndex);
                idol.skinIndex = OtherSkinSelectScreen.Inst.skinIndex;
                idol.shoulderImg = ImageMaster.loadImage(String.format("gkmasModResource/img/idol/othe/%s/stand/fire.png",idol.idolName));
                idol.shoulder2Img = ImageMaster.loadImage(String.format("gkmasModResource/img/idol/othe/%s/stand/fire.png",idol.idolName));
                idol.corpseImg = ImageMaster.loadImage(String.format("gkmasModResource/img/idol/othe/%s/stand/sleep_skin10.png",idol.idolName));
            }
        }
    }

    /**
     * 设置左上角的角色名字
     */
    @SpirePatch(clz = AbstractDungeon.class,method = SpirePatch.CONSTRUCTOR,paramtypez = {String.class, String.class, AbstractPlayer.class, ArrayList.class})
    public static class PrePatchAbstractDungeon_Constructor {
        @SpirePrefixPatch
        public static void Prefix(AbstractDungeon __instance, String name, String levelId, AbstractPlayer p, ArrayList<String> newSpecialOneTimeEventList) {
            if(p instanceof IdolCharacter){
                p.title = SkinSelectScreen.Inst.curName;
            }
            else if(p instanceof OtherIdolCharacter){
                p.title = OtherSkinSelectScreen.Inst.curName;
            }
        }
    }

    /**
     * 设置左上角的角色名字
     */
    @SpirePatch(clz = AbstractDungeon.class,method = SpirePatch.CONSTRUCTOR,paramtypez = {String.class, AbstractPlayer.class, SaveFile.class})
    public static class PrePatchAbstractDungeon_Constructor2 {
        @SpirePrefixPatch
        public static void Prefix(AbstractDungeon __instance, String name, AbstractPlayer p, SaveFile saveFile) {
            if(p instanceof IdolCharacter){
                p.title = SkinSelectScreen.Inst.curName;
            }
            else if(p instanceof OtherIdolCharacter){
                p.title = OtherSkinSelectScreen.Inst.curName;
            }
        }
    }

    /**
     * 生成地图时，设置SP节点
     */
    @SpirePatch(clz = AbstractDungeon.class,method = "generateMap")
    public static class PostPatchAbstractDungeon_generateMap {
        @SpirePostfixPatch
        public static void Postfix() {
            if(AbstractDungeon.name.equals(IdolRoad.ID)){
                return;
            }
            if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasRelic(PocketBook.ID)){
                GkmasMod.listeners.forEach(listener -> listener.onCardImgUpdate());
                Random spRng = new Random(Settings.seed, AbstractDungeon.actNum*100);
                float chance = 0.2f;
                if(AbstractDungeon.player.hasRelic(StruggleRecord.ID))
                    chance += 0.1f;
                chance += AbstractDungeon.actNum * 0.1f;
                if(AbstractDungeon.actNum==2)
                    chance += AbstractDungeon.ascensionLevel * 0.005f;
                if(AbstractDungeon.actNum==3)
                    chance += AbstractDungeon.ascensionLevel * 0.01f;
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

    /**
     * 设置4层Boss
     */
    @SpirePatch(clz = AbstractDungeon.class,method = "setBoss",paramtypez = {String.class})
    public static class PrePatchAbstractDungeon_setBoss {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractDungeon __instance, String key) {
            if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                if(AbstractDungeon.player instanceof MisuzuCharacter||AbstractDungeon.player.hasRelic(NIABadge.ID)){
                    if (key!=null&&key.equals("The Heart")){
                        AbstractDungeon.bossKey = "MonsterRinha";
                        if (DungeonMap.boss != null && DungeonMap.bossOutline != null) {
                            DungeonMap.boss.dispose();
                            DungeonMap.bossOutline.dispose();
                        }
                        DungeonMap.boss = ImageMaster.loadImage("gkmasModResource/img/monsters/Rinha/icon.png");
                        DungeonMap.bossOutline = ImageMaster.loadImage("gkmasModResource/img/monsters/Rinha/icon.png");
                        return SpireReturn.Return(null);
                    }
                }
                else if (key!=null&&key.equals("The Heart")){
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
                bossList.add(new IdolBoss_jsna());


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
                AbstractDungeon.bossKey = bossList.get(battle2_1).id;
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

    /**
     * 设置三维TAG的随机数
     */
    @SpirePatch(clz = AbstractDungeon.class,method = "generateSeeds")
    public static class PostPatchAbstractDungeon_generateSeeds {
        @SpirePostfixPatch
        public static void Postfix() {
            GkmasMod.threeSizeTagRng = new Random(Settings.seed);
        }
    }

    /**
     * 设置4层Boss
     */
    @SpirePatch(clz = MonsterHelper.class,method = "getEncounter")
    public static class PrePatchMonsterHelper_getEncounter {
        @SpirePrefixPatch
        public static SpireReturn<MonsterGroup> Prefix(String key) {
            if (key!=null&&key.equals("MisuzuBoss")) {
                AbstractMonster customBoss = new MisuzuBoss(-50.0F, 15.0F);
                MonsterGroup customGroup = new MonsterGroup(customBoss);
                return SpireReturn.Return(customGroup);
            }
            if (key!=null&&key.equals("MonsterRinha")) {
                AbstractMonster customBoss = new MonsterRinha(-50.0F, 15.0F);
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
                bossList.add(new IdolBoss_jsna());


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

    /**
     * 在特殊商店或特殊指导进入星系仪界面后，返回原来的窗口
     */
    @SpirePatch(clz = AbstractDungeon.class,method = "closeCurrentScreen")
    public static class PrePatchAbstractDungeon_closeCurrentScreen {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix() {
            if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                if(GkmasMod.screenIndex==1&&AbstractDungeon.screen!= AbstractDungeon.CurrentScreen.CARD_REWARD)
                    AbstractDungeon.previousScreen = AnotherShopScreen.Enum.AnotherShop_Screen;
                else if(GkmasMod.screenIndex==2)
                    AbstractDungeon.previousScreen = SpecialTeachScreen.Enum.SpecialTeach_Screen;
            }
            return SpireReturn.Continue();
        }
    }

    /**
     * 设置偶像之路的掉落物
     */
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
                ArrayList<String> cardList = new ArrayList<>();

                java.util.Random random = new java.util.Random(Settings.seed+AbstractDungeon.floorNum);
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
                ArrayList<String> cardList = new ArrayList<>();

                java.util.Random random = new java.util.Random(Settings.seed+AbstractDungeon.floorNum);
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

    /**
     * 遗物 再挑战券 的相关逻辑，已废弃
     */
//    @SpirePatch(clz = AbstractDungeon.class,method = "nextRoomTransition",paramtypez = {SaveFile.class})
//    public static class PrePatchAbstractDungeon_nextRoomTransition {
//        @SpireInsertPatch(rloc = 2210-2126)
//        public static void Insert(AbstractDungeon __instance,SaveFile saveFile) {
//            if(AbstractDungeon.player.hasRelic(ReChallenge.ID)){
//                Properties defaults = new Properties();
//                defaults.setProperty("cardRate", String.valueOf(PlayerHelper.getCardRate()));
//                defaults.setProperty("beat_hmsz", "0");
//                defaults.setProperty("ReChallenge", "1");
//                int tmp = GkmasMod.config.getInt("ReChallenge");
//                if(tmp>0){
//                    AbstractDungeon.monsterHpRng = new Random(Long.valueOf(Settings.seed.longValue() + AbstractDungeon.floorNum+tmp*100));
//                    AbstractDungeon.aiRng = new Random(Long.valueOf(Settings.seed.longValue() + AbstractDungeon.floorNum+tmp*100));
//                    AbstractDungeon.shuffleRng = new Random(Long.valueOf(Settings.seed.longValue() + AbstractDungeon.floorNum+tmp*100));
//                    AbstractDungeon.cardRandomRng = new Random(Long.valueOf(Settings.seed.longValue() + AbstractDungeon.floorNum+tmp*100));
//                    AbstractDungeon.miscRng = new Random(Long.valueOf(Settings.seed.longValue() + AbstractDungeon.floorNum+tmp*100));
//                }
//            }
//        }
//    }

    /**
     * 前3层Boss战中，取消绘制原有的地图背景
     */
    @SpirePatch(clz = AbstractDungeon.class, method = "render")
    public static class InstrumentPatchAbstractDungeon_render {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals("com.megacrit.cardcrawl.scenes.AbstractScene") && m
                            .getMethodName().equals("renderCombatRoomFg"))
                        m.replace("if (gkmasmod.modcore.GkmasMod.renderScene) {$_ = $proceed($$);}");
                }
            };
        }
    }

    /**
     * 添加偶像卡到制作人的战后卡池中
     */
    @SpirePatch(clz = AbstractDungeon.class,method = "initializeCardPools")
    public static class InsertPatchAbstractDungeon_initializeCardPools {
        @SpireInsertPatch(rloc = 1471-1419)
        public static void Insert(AbstractDungeon __instance) {
            if(AbstractDungeon.player instanceof OtherIdolCharacter){
                if(OtherSkinSelectScreen.Inst.idolName.equals(IdolData.prod)){
                    Iterator<Map.Entry<String, AbstractCard>> cardLib = CardLibrary.cards.entrySet().iterator();
                    while (true) {
                        if (!cardLib.hasNext())
                            break;
                        Map.Entry c = cardLib.next();
                        AbstractCard card = (AbstractCard)c.getValue();
                        if (card instanceof GkmasCard &&card.hasTag(GkmasCardTag.IDOL_CARD_TAG) && (
                                !UnlockTracker.isCardLocked((String)c.getKey()) || Settings.isDailyRun)){
                            GkmasCard gkmasCard = (GkmasCard)card;
                            switch (gkmasCard.bannerColor){
                                case "blue":
                                    AbstractDungeon.commonCardPool.addToTop(card);
                                    break;
                                case "yellow":
                                    AbstractDungeon.uncommonCardPool.addToTop(card);
                                    break;
                                case "color":
                                    AbstractDungeon.rareCardPool.addToTop(card);
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 感理之衡
     */
    @SpirePatch2(clz = AbstractDungeon.class, method = "getCard", paramtypez = {AbstractCard.CardRarity.class})
    public static class PrefixPatchAbstractDungeon_getCard {
        @SpirePrefixPatch
        public static SpireReturn<AbstractCard> prefix(AbstractCard.CardRarity rarity){
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
        return card.color==AbstractDungeon.player.getCardColor()||card.color == PlayerColorEnum.gkmasModColor || card.color == PlayerColorEnum.gkmasModColorLogic || card.color == PlayerColorEnum.gkmasModColorSense|| card.color == PlayerColorEnum.gkmasModColorAnomaly|| card.color == PlayerColorEnum.gkmasModColorMoon;
    }

    /**
     * 卡组中的唯一卡不会重复出现
     */
    @SpirePatch2(clz = AbstractDungeon.class, method = "getRewardCards")
    public static class InsertPatchAbstractDungeon_getRewardCards {
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

    /**
     * 卡组中的唯一卡不会重复出现
     */
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
