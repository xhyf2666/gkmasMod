package gkmasmod.room;

import actlikeit.RazIntent.AssetLoader;
import actlikeit.patches.AbstractRoomUpdateIncrementElitesPatch;
import basemod.BaseMod;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import gkmasmod.utils.IdolData;

import java.util.ArrayList;

public class GkmasBossRoom extends MonsterRoomBoss {

    public String encounterID;

    public GkmasBossRoom(String encounterID, String mapImg) {
        setMapImg(AssetLoader.loadImage(mapImg), AssetLoader.loadImage(mapImg));
    }

}
