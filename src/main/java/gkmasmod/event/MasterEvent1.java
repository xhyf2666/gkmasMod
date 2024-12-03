package gkmasmod.event;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.relics.*;
import gkmasmod.utils.SoundHelper;

public class MasterEvent1 extends AbstractImageEvent {
    public static final String ID = MasterEvent1.class.getSimpleName();
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private int screenNum = 0;

    public MasterEvent1() {
        super(NAME, DESCRIPTIONS[0], "gkmasModResource/img/event/teacherVo.png");
        this.imageEventText.setDialogOption(OPTIONS[0],new StruggleRecord());
        this.imageEventText.setDialogOption(OPTIONS[1],new MasterGirding());
        this.imageEventText.setDialogOption(OPTIONS[2],new MasterHighHeels());
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
                        if (!AbstractDungeon.player.hasRelic(StruggleRecord.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new StruggleRecord());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        screenNum++;
                        return;
                    case 1:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        if (!AbstractDungeon.player.hasRelic(MasterGirding.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new MasterGirding());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        screenNum++;
                        return;
                    case 2:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        if (!AbstractDungeon.player.hasRelic(MasterHighHeels.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new MasterHighHeels());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        screenNum++;
                        return;
                    case 3:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        screenNum++;
                        openMap();
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
            openMap();
        }
        else{
            openMap();
        }
    }

}