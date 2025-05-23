package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.ui.campfire.RestOption;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.relics.MisuzuNatureRelic;
import gkmasmod.relics.MysteriousObject;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;

import java.util.ArrayList;

public class CampfireSleepEffectPatch {
    /**
     * 睡觉时，触发特定遗物的效果
     */
    @SpirePatch(clz = CampfireSleepEffect.class,method = SpirePatch.CONSTRUCTOR)
    public static class PostPatchCampfireSleepEffect_Constructor {
        @SpirePostfixPatch
        public static void Postfix(CampfireSleepEffect __instance) {
            if(AbstractDungeon.player.hasRelic(MysteriousObject.ID)){
                ((MysteriousObject)AbstractDungeon.player.getRelic(MysteriousObject.ID)).onSleep();
            }
            if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasRelic(MisuzuNatureRelic.ID)){
                AbstractDungeon.player.getRelic(MisuzuNatureRelic.ID).onTrigger();
            }
        }
    }
    
}
