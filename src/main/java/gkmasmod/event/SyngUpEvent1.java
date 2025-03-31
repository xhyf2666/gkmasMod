package gkmasmod.event;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.relics.*;
import gkmasmod.utils.SoundHelper;

public class SyngUpEvent1 extends AbstractImageEvent {
    public static final String ID = SyngUpEvent1.class.getSimpleName();
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private int screenNum = 0;

    public SyngUpEvent1() {
        super(NAME, DESCRIPTIONS[0], "gkmasModResource/img/event/SyngUpEvent1.png");
        this.imageEventText.setDialogOption(OPTIONS[0],new SyngUpRelicBroken());
    }

    @Override
    public void onEnterRoom() {
        CardCrawlGame.music.playTempBgmInstantly("gkmasModResource/audio/bgm/bgm_adv_end_001.ogg",true);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum){
            case 0 :
                switch (i) {
                    case 0:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[1]);
                        int idx=0;
                        for(AbstractRelic r:AbstractDungeon.player.relics){
                            if(r instanceof SyngUpRelic){
                                new SyngUpRelicBroken().instantObtain(AbstractDungeon.player, idx, true);
                                break;
                            }
                            idx++;
                        }
                        screenNum++;
                        return;
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