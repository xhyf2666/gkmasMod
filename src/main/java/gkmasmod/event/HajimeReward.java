package gkmasmod.event;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.characters.OtherIdolCharacter;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.potion.*;
import gkmasmod.relics.*;
import gkmasmod.screen.OtherSkinSelectScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.CommonEnum;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.SoundHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class HajimeReward extends AbstractImageEvent {
    public static final String ID = HajimeReward.class.getSimpleName();
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private int screenNum = 0;
    public static CommonEnum.IdolType type;

    private ArrayList<AbstractRelic> relics;

    public HajimeReward() {
        super(NAME, DESCRIPTIONS[0], "gkmasModResource/img/event/teacher.png");
        type  = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getType(SkinSelectScreen.Inst.skinIndex);
        if(AbstractDungeon.player instanceof MisuzuCharacter){
            type = CommonEnum.IdolType.SENSE;
        }
        if(AbstractDungeon.player instanceof OtherIdolCharacter){
            type  = IdolData.getOtherIdol(OtherSkinSelectScreen.Inst.idolIndex).getType(OtherSkinSelectScreen.Inst.skinIndex);
            if(OtherSkinSelectScreen.Inst.idolName.equals(IdolData.prod)){
                type = CommonEnum.IdolType.SENSE;
            }
        }
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

        generateRelic();
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum){
            case 0 :
                switch (i) {
                    case 0:
                        screenNum++;
                        updateRelicOption();
                        if (!AbstractDungeon.player.hasRelic(FirstStarBracelet.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new FirstStarBracelet());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        return;
                    case 1:
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
                        updateRelicOption();
                        return;
                    case 2:
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
                        updateRelicOption();
                        return;
                }
            case 1:
                switch (i) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, relics.get(i));
                        screenNum++;
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        return;
                    case 4:
                        screenNum++;
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        break;
                }
            case 2:
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

    private void updateRelicOption(){
        this.imageEventText.clearAllDialogs();
        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
        this.imageEventText.setDialogOption(String.format(OPTIONS[4],relics.get(0).name),relics.get(0));
        this.imageEventText.setDialogOption(String.format(OPTIONS[4],relics.get(1).name),relics.get(1));
        this.imageEventText.setDialogOption(String.format(OPTIONS[4],relics.get(2).name),relics.get(2));
        this.imageEventText.setDialogOption(String.format(OPTIONS[4],relics.get(3).name),relics.get(3));
        this.imageEventText.setDialogOption(OPTIONS[5]);
    }

    private void generateRelic(){
        AbstractCard.CardColor targetColor = null;
        if(type==CommonEnum.IdolType.SENSE){
            targetColor = PlayerColorEnum.gkmasModColorSense;
        }
        else if (type==CommonEnum.IdolType.LOGIC){
            targetColor = PlayerColorEnum.gkmasModColorLogic;
        }
        else if(type == CommonEnum.IdolType.ANOMALY){
            targetColor = PlayerColorEnum.gkmasModColorAnomaly;
        }
        relics = new ArrayList<>();
        ArrayList<String> cardNameList;
        ArrayList<String> relicNameList;
        for(String idolname :IdolData.idolNames){
            cardNameList = IdolData.getIdol(idolname).getCardList();
            relicNameList = IdolData.getIdol(idolname).getRelicList();
            for (int i = 0; i < cardNameList.size(); i++) {
                AbstractCard card = CardLibrary.getCard(cardNameList.get(i));
                GkmasCard gkmasCard = (GkmasCard)card;
                if(gkmasCard.bannerColor.equals("color")){
                    if(targetColor==null)
                        relics.add(RelicLibrary.getRelic(relicNameList.get(i)).makeCopy());
                    else{
                        if(targetColor==card.color)
                            relics.add(RelicLibrary.getRelic(relicNameList.get(i)).makeCopy());
                    }
                }
            }
        }
        Iterator<AbstractRelic> iterator = relics.iterator();
        while (iterator.hasNext()) {
            AbstractRelic relic = iterator.next();
            if (AbstractDungeon.player.hasRelic(relic.relicId))
                iterator.remove();
        }
        int diff = 4-relics.size();
        for (int i = 0; i < diff; i++) {
            relics.add(new Circlet());
        }

        Collections.shuffle(relics, new Random(Settings.seed+AbstractDungeon.floorNum));
    }
}