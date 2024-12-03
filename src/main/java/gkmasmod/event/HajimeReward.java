package gkmasmod.event;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.potion.*;
import gkmasmod.relics.*;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.CommonEnum;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.SoundHelper;

public class HajimeReward extends AbstractImageEvent {
    public static final String ID = HajimeReward.class.getSimpleName();
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private int screenNum = 0;
    public static CommonEnum.IdolType type;

    public HajimeReward() {
        super(NAME, DESCRIPTIONS[0], "gkmasModResource/img/event/teacher.png");
        type  = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getType(SkinSelectScreen.Inst.skinIndex);
        if(type==CommonEnum.IdolType.SENSE){
            this.imageEventText.setDialogOption(OPTIONS[0],new FirstStarBracelet());
            this.imageEventText.setDialogOption(OPTIONS[1],new FirstStarNotebook());
            this.imageEventText.setDialogOption(OPTIONS[2],new FirstStarTshirt());
        }
        else if (type==CommonEnum.IdolType.LOGIC){
            this.imageEventText.setDialogOption(OPTIONS[0],new FirstStarBracelet());
            this.imageEventText.setDialogOption(OPTIONS[1],new UnofficialMascot());
            this.imageEventText.setDialogOption(OPTIONS[2],new FirstStarKeychain());
        }
        else if(type == CommonEnum.IdolType.ANOMALY){
            this.imageEventText.setDialogOption(OPTIONS[0],new FirstStarBracelet());
            this.imageEventText.setDialogOption(OPTIONS[1],new FirstStarClock());
            this.imageEventText.setDialogOption(OPTIONS[2],new FirstStarModel());
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
                        screenNum++;
                        if (!AbstractDungeon.player.hasRelic(FirstStarBracelet.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new FirstStarBracelet());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        return;
                    case 1:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        if(type==CommonEnum.IdolType.SENSE){
                            if (!AbstractDungeon.player.hasRelic(FirstStarNotebook.ID)) {
                                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new FirstStarNotebook());
                            } else {
                                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                            }
                        }
                        else if (type==CommonEnum.IdolType.LOGIC){
                            if (!AbstractDungeon.player.hasRelic(UnofficialMascot.ID)) {
                                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new UnofficialMascot());
                            } else {
                                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                            }
                        }
                        else if(type == CommonEnum.IdolType.ANOMALY){
                            if (!AbstractDungeon.player.hasRelic(FirstStarClock.ID)) {
                                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new FirstStarClock());
                            } else {
                                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                            }
                        }
                        screenNum++;
                        return;
                    case 2:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        if(type==CommonEnum.IdolType.SENSE){
                            if (!AbstractDungeon.player.hasRelic(FirstStarTshirt.ID)) {
                                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new FirstStarTshirt());
                            } else {
                                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                            }
                        }
                        else if (type==CommonEnum.IdolType.LOGIC){
                            if (!AbstractDungeon.player.hasRelic(FirstStarKeychain.ID)) {
                                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new FirstStarKeychain());
                            } else {
                                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                            }
                        }
                        else if(type == CommonEnum.IdolType.ANOMALY){
                            if (!AbstractDungeon.player.hasRelic(FirstStarModel.ID)) {
                                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new FirstStarModel());
                            } else {
                                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                            }
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

        SoundHelper.playSound("gkmasModResource/audio/voice/plan/presult_001_final_true_nasr_001.ogg");

    }
}