package gkmasmod.room;

import actlikeit.patches.AbstractRoomUpdateIncrementElitesPatch;
import basemod.BaseMod;
import basemod.CustomEventRoom;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import gkmasmod.utils.IdolData;

import java.util.ArrayList;

public class EventMonsterRoom extends MonsterRoom {

    public String encounterID;

    public EventMonsterRoom(String encounterID) {
        this.encounterID = encounterID;
        setMapImg(ImageMaster.MAP_NODE_EVENT, ImageMaster.MAP_NODE_EVENT_OUTLINE);
    }

    public void onPlayerEntry() {
        playBGM((String)null);
        this.monsters = BaseMod.getMonster(this.encounterID);
        this.monsters.init();

//        if(!AbstractDungeon.loading_post_combat) {
//            String tmp = encounterID;
//            if (tmp.startsWith("IdolBoss_")) {
//                tmp = tmp.substring(9);
//            }
//            java.util.Random random = new java.util.Random(Settings.seed);
//            ArrayList<String> relics = IdolData.getIdol(tmp).getRelicList();
//            AbstractRelic relic;
//            relic = RelicLibrary.getRelic(relics.get(random.nextInt(relics.size()))).makeCopy();
//            this.rewards.add(new RewardItem(relic));
//        }

        waitTimer = 0.1F;
    }

    public void endBattle() {
        super.endBattle();
        AbstractRoomUpdateIncrementElitesPatch.Insert((AbstractRoom)null);
    }
}
