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
import gkmasmod.cards.GkmasCard;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.relics.*;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.CommonEnum;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.SoundHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class NIAReward extends AbstractImageEvent {
    public static final String ID = NIAReward.class.getSimpleName();
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private int screenNum = 0;
    public static CommonEnum.IdolType type;

    private ArrayList<AbstractRelic> relics;

    public NIAReward() {
        super(NAME, DESCRIPTIONS[0], "gkmasModResource/img/event/teacher.png");
        type  = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getType(SkinSelectScreen.Inst.skinIndex);
        if(AbstractDungeon.player!=null&&AbstractDungeon.player instanceof MisuzuCharacter){
            type = CommonEnum.IdolType.SENSE;
        }
        generateRelic();
        updateRelicOption();
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum){
            case 0:
                switch (i) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, relics.get(i));
                        screenNum++;
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[1]);
                        return;
                    case 4:
                        screenNum++;
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[1]);
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

    @Override
    public void onEnterRoom() {
        super.onEnterRoom();
        SoundHelper.playSound("gkmasModResource/audio/voice/plan/presult_001_final_true_nasr_001.ogg");
    }

    private void updateRelicOption(){
        this.imageEventText.clearAllDialogs();
        this.imageEventText.updateBodyText(DESCRIPTIONS[0]);
        this.imageEventText.setDialogOption(String.format(OPTIONS[0],relics.get(0).name),relics.get(0));
        this.imageEventText.setDialogOption(String.format(OPTIONS[0],relics.get(1).name),relics.get(1));
        this.imageEventText.setDialogOption(String.format(OPTIONS[0],relics.get(2).name),relics.get(2));
        this.imageEventText.setDialogOption(String.format(OPTIONS[0],relics.get(3).name),relics.get(3));
        this.imageEventText.setDialogOption(OPTIONS[1]);
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