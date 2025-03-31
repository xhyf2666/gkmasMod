package gkmasmod.room.supply;

import basemod.BaseMod;
import basemod.CustomEventRoom;
import basemod.eventUtil.EventUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import gkmasmod.cards.GkmasCard;
import gkmasmod.event.SupplyEvent;
import gkmasmod.room.specialTeach.SpecialTeachScreen;

import java.util.ArrayList;
import java.util.Iterator;

public class SupplyEffect extends AbstractGameEffect {


    static public boolean isFinished= false;

    private static final float DUR = 1.5F;

    private boolean openedScreen = false;

    private Color screenColor = AbstractDungeon.fadeColor.cpy();

    public SupplyEffect() {
        this.duration = 0.5F;
        this.screenColor.a = 0.0F;
        this.isFinished = false;
    }

    public void update() {
        supply();
        this.isDone = true;
    }


    private void updateBlackScreenColor() {
        if (this.duration > 1.0F) {
            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.0F) * 2.0F);
        } else {
            this.screenColor.a = Interpolation.fade.apply(0.0F, 0.5F, this.duration / 1.5F);
        }
    }



    @Override
    public void render(SpriteBatch sb) {
    }

    @Override
    public void dispose() {

    }

    public void supply(){
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
//        AbstractDungeon.effectList.clear();
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
            AbstractEvent event =  EventUtils.getEvent(SupplyEvent.ID);
            cer.event = event;
        } catch (Exception e) {
        }

        AbstractDungeon.scene.nextRoom(mapRoomNode2.room);
    }
}
