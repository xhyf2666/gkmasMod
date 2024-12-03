package gkmasmod.event;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.Circlet;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.potion.*;
import gkmasmod.relics.AmakawaRamenTour;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.CommonEnum;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.SoundHelper;

public class Plan_hski2 extends AbstractImageEvent {
    public static final String ID = Plan_hski2.class.getSimpleName();
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private int screenNum = 0;
    public static CommonEnum.IdolType type;


    public Plan_hski2() {
        super(NAME, DESCRIPTIONS[0], "gkmasModResource/img/event/teacherVo.png");
        type  = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getType(SkinSelectScreen.Inst.skinIndex);
        if(type==CommonEnum.IdolType.SENSE){
            this.imageEventText.setDialogOption(String.format(OPTIONS[0],new FirstStarWater().name));
            this.imageEventText.setDialogOption(String.format(OPTIONS[1],new IcedCoffee().name));
            this.imageEventText.setDialogOption(String.format(OPTIONS[2],new VitaminDrink().name));
        }
        else if (type==CommonEnum.IdolType.LOGIC){
            this.imageEventText.setDialogOption(String.format(OPTIONS[0],new OolongTea().name));
            this.imageEventText.setDialogOption(String.format(OPTIONS[1],new HotCoffee().name));
            this.imageEventText.setDialogOption(String.format(OPTIONS[2],new RooibosTea().name));
        }
        else if (type==CommonEnum.IdolType.ANOMALY){
            this.imageEventText.setDialogOption(String.format(OPTIONS[0],new OolongTea().name));
            this.imageEventText.setDialogOption(String.format(OPTIONS[1],new GingerAle().name));
            this.imageEventText.setDialogOption(String.format(OPTIONS[2],new Hojicha().name));
        }
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum){
            case 0 :
                switch (i) {
                    case 0:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        if(type==CommonEnum.IdolType.SENSE){
                            AbstractDungeon.player.obtainPotion(new FirstStarWater());
                        }
                        else if (type==CommonEnum.IdolType.LOGIC){
                            AbstractDungeon.player.obtainPotion(new OolongTea());
                        }
                        else if (type==CommonEnum.IdolType.ANOMALY){
                            AbstractDungeon.player.obtainPotion(new OolongTea());
                        }
                        if (!AbstractDungeon.player.hasRelic(AmakawaRamenTour.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new AmakawaRamenTour());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        screenNum++;
                        return;
                    case 1:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        if(type==CommonEnum.IdolType.SENSE){
                            AbstractDungeon.player.obtainPotion(new IcedCoffee());
                        }
                        else if (type==CommonEnum.IdolType.LOGIC){
                            AbstractDungeon.player.obtainPotion(new HotCoffee());
                        }
                        else if (type==CommonEnum.IdolType.ANOMALY){
                            AbstractDungeon.player.obtainPotion(new GingerAle());
                        }
                        if (!AbstractDungeon.player.hasRelic(AmakawaRamenTour.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new AmakawaRamenTour());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        screenNum++;
                        return;
                    case 2:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        if(type==CommonEnum.IdolType.SENSE){
                            AbstractDungeon.player.obtainPotion(new VitaminDrink());
                        }
                        else if (type==CommonEnum.IdolType.LOGIC){
                            AbstractDungeon.player.obtainPotion(new RooibosTea());
                        }
                        else if (type==CommonEnum.IdolType.ANOMALY){
                            AbstractDungeon.player.obtainPotion(new Hojicha());
                        }
                        if (!AbstractDungeon.player.hasRelic(AmakawaRamenTour.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new AmakawaRamenTour());
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