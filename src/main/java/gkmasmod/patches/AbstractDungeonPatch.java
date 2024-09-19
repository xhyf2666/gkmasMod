package gkmasmod.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.MonsterRoomCustomBoss;
import gkmasmod.monster.ending.MisuzuBoss;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.PlayerHelper;

import java.util.ArrayList;

public class AbstractDungeonPatch {
    @SpirePatch(clz = AbstractDungeon.class,method = SpirePatch.CONSTRUCTOR,paramtypez = {String.class, String.class, AbstractPlayer.class, ArrayList.class})
    public static class PostPatchAbstractDungeonConstructor {
        @SpirePostfixPatch
        public static void Postfix(AbstractDungeon __instance, String name, String levelId, AbstractPlayer p, ArrayList<String> newSpecialOneTimeEventList) {
            if(p instanceof IdolCharacter){
                //((IdolCharacter) p).initializeData();
                IdolCharacter idol = (IdolCharacter) p;
                idol.idolData = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex);
                idol.skinIndex = SkinSelectScreen.Inst.skinIndex;
                idol.shoulderImg = ImageMaster.loadImage(String.format("gkmasModResource/img/idol/%s/stand/fire.png",idol.idolName));
                idol.shoulder2Img = ImageMaster.loadImage(String.format("gkmasModResource/img/idol/%s/stand/fire.png",idol.idolName));
                idol.corpseImg = ImageMaster.loadImage(String.format("gkmasModResource/img/idol/%s/stand/sleep_skin10.png",idol.idolName));
            }
        }
    }

    @SpirePatch(clz = AbstractDungeon.class,method = SpirePatch.CONSTRUCTOR,paramtypez = {String.class, AbstractPlayer.class, SaveFile.class})
    public static class PostPatchAbstractDungeonConstructor2 {
        @SpirePostfixPatch
        public static void Postfix(AbstractDungeon __instance, String name, AbstractPlayer p, SaveFile saveFile) {
            if(p instanceof IdolCharacter){
                //((IdolCharacter) p).initializeData();
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
            if(AbstractDungeon.player instanceof IdolCharacter){
                GkmasMod.listeners.forEach(listener -> listener.onCardImgUpdate());
                Random spRng = new Random(Settings.seed, AbstractDungeon.actNum*100);
                float chance = 0.15f;
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
            if(AbstractDungeon.player instanceof IdolCharacter){
                if (key.equals("The Heart")){
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
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = MonsterHelper.class,method = "getEncounter")
    public static class PatchGetEncounter {
        @SpirePrefixPatch
        public static SpireReturn<MonsterGroup> Prefix(String key) {
            if (key.equals("MisuzuBoss")) {
                AbstractMonster customBoss = new MisuzuBoss(-50.0F, 15.0F);
                MonsterGroup customGroup = new MonsterGroup(customBoss);
                return SpireReturn.Return(customGroup);
            }
            return SpireReturn.Continue();
        }
    }


}
