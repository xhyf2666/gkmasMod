package gkmasmod.event;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.relics.EveryoneTextbook;
import gkmasmod.relics.MysteriousObject;
import gkmasmod.relics.OverpoweredBall;
import gkmasmod.relics.PledgePetal;
import gkmasmod.utils.SoundHelper;

public class Plan_shro1 extends AbstractImageEvent {
    public static final String ID = Plan_shro1.class.getSimpleName();
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private int screenNum = 0;
    private int gold = 50;

    public Plan_shro1() {
        super(NAME, DESCRIPTIONS[0], "gkmasModResource/img/event/teacherVo.png");
        this.imageEventText.setDialogOption(OPTIONS[0],new MysteriousObject());
        this.imageEventText.setDialogOption(OPTIONS[1],new OverpoweredBall());
        this.imageEventText.setDialogOption(OPTIONS[2],new PledgePetal());
        this.imageEventText.setDialogOption(OPTIONS[3],new EveryoneTextbook());

    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum){
            case 0 :
                switch (i) {
                    case 0:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        AbstractDungeon.effectList.add(new RainingGoldEffect(this.gold));
                        AbstractDungeon.player.gainGold(this.gold);
                        if (!AbstractDungeon.player.hasRelic(MysteriousObject.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new MysteriousObject());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        screenNum++;
                        return;
                    case 1:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        AbstractDungeon.effectList.add(new RainingGoldEffect(this.gold));
                        AbstractDungeon.player.gainGold(this.gold);
                        if (!AbstractDungeon.player.hasRelic(OverpoweredBall.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new OverpoweredBall());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        screenNum++;
                        return;
                    case 2:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        AbstractDungeon.effectList.add(new RainingGoldEffect(this.gold));
                        AbstractDungeon.player.gainGold(this.gold);
                        if (!AbstractDungeon.player.hasRelic(PledgePetal.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new PledgePetal());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        screenNum++;
                        return;
                    case 3:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        AbstractDungeon.effectList.add(new RainingGoldEffect(this.gold));
                        AbstractDungeon.player.gainGold(this.gold);
                        if (!AbstractDungeon.player.hasRelic(EveryoneTextbook.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new EveryoneTextbook());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
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

    @Override
    public void onEnterRoom() {
        super.onEnterRoom();
        SoundHelper.playSound(String.format("gkmasModResource/audio/voice/plan/%s.ogg",ID));
    }

}