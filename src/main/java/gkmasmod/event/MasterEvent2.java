package gkmasmod.event;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.Circlet;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.relics.*;

public class MasterEvent2 extends AbstractImageEvent {
    public static final String ID = MasterEvent2.class.getSimpleName();
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private int screenNum = 0;

    public MasterEvent2() {
        super(NAME, DESCRIPTIONS[0], "gkmasModResource/img/event/teacherDa.png");
        this.imageEventText.setDialogOption(OPTIONS[0],new MasterVest());
        this.imageEventText.setDialogOption(OPTIONS[1],new MasterSupportFrame());
        this.imageEventText.setDialogOption(OPTIONS[2],new MasterRoller());
        this.imageEventText.setDialogOption(OPTIONS[3]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum){
            case 0 :
                switch (i) {
                    case 0:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        if (!AbstractDungeon.player.hasRelic(MasterVest.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new MasterVest());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        screenNum++;
                        return;
                    case 1:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        if (!AbstractDungeon.player.hasRelic(MasterSupportFrame.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new MasterSupportFrame());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        screenNum++;
                        return;
                    case 2:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        if (!AbstractDungeon.player.hasRelic(MasterRoller.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new MasterRoller());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        screenNum++;
                        return;
                    case 3:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
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
            GkmasMod.node.room.onPlayerEntry();
            GkmasMod.node = null;
            AbstractDungeon.rs = AbstractDungeon.RenderScene.NORMAL;
        }
        else{
            openMap();
        }
    }

}