package gkmasmod.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.characters.OtherIdolCharacter;
import gkmasmod.monster.MonsterRoomCustomBoss;
import gkmasmod.relics.NIABadge;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;

import java.util.ArrayList;

public class TheEndingPatch {

    @SpirePatch(clz= TheEnding.class, method="generateSpecialMap")
    public static class PostPatchTheEnding_generateSpecialMap {
        public static void Postfix(TheEnding __instance) {
            if(AbstractDungeon.actNum == 4){
                if(AbstractDungeon.player instanceof MisuzuCharacter||AbstractDungeon.player.hasRelic(NIABadge.ID)){
                    DungeonMap.boss = ImageMaster.loadImage("gkmasModResource/img/monsters/Rinha/icon.png");
                    DungeonMap.bossOutline = ImageMaster.loadImage("gkmasModResource/img/monsters/Rinha/icon.png");
                }
                else if(AbstractDungeon.player instanceof IdolCharacter){
                    DungeonMap.boss = ImageMaster.loadImage("gkmasModResource/img/monsters/Misuzu/icon.png");
                    DungeonMap.bossOutline = ImageMaster.loadImage("gkmasModResource/img/monsters/Misuzu/icon.png");
                }
                else if(AbstractDungeon.player instanceof OtherIdolCharacter){
                    DungeonMap.boss = ImageMaster.loadImage("gkmasModResource/img/monsters/Misuzu/icon.png");
                    DungeonMap.bossOutline = ImageMaster.loadImage("gkmasModResource/img/monsters/Misuzu/icon.png");
                }
            }
        }

    }

}
