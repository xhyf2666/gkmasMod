package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.relics.PocketBook;
import gkmasmod.room.shop.AnotherShopScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.ThreeSizeHelper;

import java.util.ArrayList;

public class GridCardSelectScreenPatch {

    @SpirePatch(clz = GridCardSelectScreen.class,method = "update")
    public static class GridCardSelectScreenInsert_update {
        @SpireInsertPatch(rloc = 145)
        public static void Insert(GridCardSelectScreen __instance) {
            if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                if(GkmasMod.screenIndex==1){
                    AbstractDungeon.overlayMenu.cancelButton.show(AnotherShopScreen.NAMES[12]);
                }
            }
        }
    }

}
