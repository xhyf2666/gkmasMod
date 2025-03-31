package gkmasmod.event;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.rooms.RestRoom;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.relics.FriendChinaRelic;
import gkmasmod.relics.NIABadge;
import gkmasmod.relics.SyngUpRelic;
import gkmasmod.relics.SyngUpRelicBroken;
import gkmasmod.room.shop.AnotherShopEffect;

public class SelectBossEvent extends AbstractImageEvent {
    public static final String ID = SelectBossEvent.class.getSimpleName();
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private int screenNum = 0;

    public SelectBossEvent() {
        super(NAME, DESCRIPTIONS[0], "gkmasModResource/img/event/SyngUpEvent1.png");
        this.imageEventText.setDialogOption(OPTIONS[0],new NIABadge());
        this.imageEventText.setDialogOption(OPTIONS[1]);
        this.imageEventText.setDialogOption(OPTIONS[2]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum){
            case 0 :
                switch (i) {
                    case 0:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[1]);
                        if (!AbstractDungeon.player.hasRelic(NIABadge.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new NIABadge());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        screenNum++;
                        break;
                    case 1:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[1]);
                        screenNum++;
                        break;
                    case 2:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[1]);
                        screenNum++;
                        break;
                }
            case 1:
                switch (i) {
                    case 0:
                        break;
                }
        }
        if(GkmasMod.node!=null){
            AbstractDungeon.setCurrMapNode(GkmasMod.node);
//            GkmasMod.node.room.onPlayerEntry();
            GkmasMod.node = null;
            RestRoom r = (RestRoom)AbstractDungeon.getCurrRoom();
            AbstractDungeon.isScreenUp = false;
            GkmasMod.screenIndex = 0;
            AbstractDungeon.overlayMenu.hideBlackScreen();
            r.campfireUI.reopen();
        }
        else{
            openMap();
        }
    }

}