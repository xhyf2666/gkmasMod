package gkmasmod.patches;

import basemod.CustomEventRoom;
import basemod.eventUtil.EventUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.relics.PocketBook;
import gkmasmod.relics.StruggleRecord;
import gkmasmod.room.shop.AnotherShopScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;

import java.util.ArrayList;

public class AbstractEventPatch {

    /**
     * 添加事件 Master·其一 在涅奥事件后
     */
    @SpirePatch(clz = AbstractEvent.class,method = "openMap")
    public static class PrePatchAbstractEvent_openMap {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractEvent __instance) {
            if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasRelic(PocketBook.ID)){
                int masterEventCount=0;
                PocketBook book = (PocketBook)AbstractDungeon.player.getRelic(PocketBook.ID);
                if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                    masterEventCount = book.masterEventCount;
                }
                if(AbstractDungeon.floorNum == 0){
                    String eventID;
                    if(masterEventCount==0){
                        book.masterEventCount++;
                        eventID = "MasterEvent1";
                    }
                    else{
                        return SpireReturn.Continue();
                    }

                    if(true){
                        RoomEventDialog.optionList.clear();
                        MapRoomNode cur = AbstractDungeon.currMapNode;
                        MapRoomNode mapRoomNode2 = new MapRoomNode(cur.x, cur.y);
                        CustomEventRoom cer = new CustomEventRoom();
                        mapRoomNode2.room = cer;
                        ArrayList<MapEdge> curEdges = cur.getEdges();
                        for (MapEdge edge : curEdges)
                            mapRoomNode2.addEdge(edge);
                        AbstractDungeon.player.releaseCard();
                        AbstractDungeon.overlayMenu.hideCombatPanels();
                        AbstractDungeon.previousScreen = null;
                        AbstractDungeon.dynamicBanner.hide();
                        AbstractDungeon.dungeonMapScreen.closeInstantly();
                        AbstractDungeon.closeCurrentScreen();
                        AbstractDungeon.topPanel.unhoverHitboxes();
                        AbstractDungeon.fadeIn();
                        AbstractDungeon.effectList.clear();
                        AbstractDungeon.topLevelEffects.clear();
                        AbstractDungeon.topLevelEffectsQueue.clear();
                        AbstractDungeon.effectsQueue.clear();
                        AbstractDungeon.dungeonMapScreen.dismissable = true;
                        AbstractDungeon.nextRoom = mapRoomNode2;
                        AbstractDungeon.setCurrMapNode(mapRoomNode2);

                        try {
                            AbstractDungeon.overlayMenu.proceedButton.hide();
                            (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
//                            AbstractDungeon.eventList.add(0, eventID);
                            AbstractEvent event =  EventUtils.getEvent(eventID);
                            cer.event = event;
                            event.onEnterRoom();
                            return SpireReturn.Return(null);
                        } catch (Exception e) {
                        }
                        AbstractDungeon.scene.nextRoom(mapRoomNode2.room);
                        if (mapRoomNode2.room instanceof com.megacrit.cardcrawl.rooms.EventRoom) {
                            AbstractDungeon.rs = (mapRoomNode2.room.event instanceof com.megacrit.cardcrawl.events.AbstractImageEvent) ? AbstractDungeon.RenderScene.EVENT : AbstractDungeon.RenderScene.NORMAL;
                        } else if (mapRoomNode2.room instanceof com.megacrit.cardcrawl.rooms.RestRoom) {
                            AbstractDungeon.rs = AbstractDungeon.RenderScene.CAMPFIRE;
                        } else {
                            AbstractDungeon.rs = AbstractDungeon.RenderScene.NORMAL;
                        }
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }

}
