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
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.ending.MisuzuBoss;
import gkmasmod.relics.PocketBook;
import gkmasmod.relics.StruggleRecord;
import gkmasmod.room.shop.AnotherShopScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;

import java.util.ArrayList;

public class AbstractEventPatch {


    @SpirePatch(clz = AbstractEvent.class,method = "openMap")
    public static class PrePatchAbstractEvent_openMap {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractEvent __instance) {
            if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
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

                    AbstractEvent event =  EventUtils.getEvent(eventID);
                    if(event!=null){
                        AbstractDungeon.eventList.add(0, eventID);
                        RoomEventDialog.optionList.clear();
                        MapRoomNode cur = AbstractDungeon.currMapNode;
                        MapRoomNode mapRoomNode2 = new MapRoomNode(cur.x, cur.y);
                        CustomEventRoom cer = new CustomEventRoom();
                        mapRoomNode2.room = cer;
                        cer.event = event;
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
                            GkmasMod.node = mapRoomNode2;
                            event.onEnterRoom();
                            return SpireReturn.Return(null);
                        } catch (Exception e) {
                        }
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }



}
