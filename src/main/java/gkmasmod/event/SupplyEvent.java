package gkmasmod.event;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.characters.OtherIdolCharacter;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.relics.*;
import gkmasmod.screen.OtherSkinSelectScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.CommonEnum;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.SoundHelper;

import java.util.*;

public class SupplyEvent extends AbstractImageEvent {
    public static final String ID = SupplyEvent.class.getSimpleName();
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private int screenNum = 0;
    public static CommonEnum.IdolType type;
    public static int gold = 60;

    public ArrayList<AbstractCard> cards;

    public SupplyEvent() {
        super(NAME, DESCRIPTIONS[0], "gkmasModResource/img/event/teacher.png");
        type  = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getType(SkinSelectScreen.Inst.skinIndex);
        this.cards = new ArrayList<>();
        if(AbstractDungeon.player instanceof MisuzuCharacter){
            getRandomCard(PlayerColorEnum.gkmasModColorSense,cards);
        }
        if(AbstractDungeon.player instanceof OtherIdolCharacter){
            type  = IdolData.getOtherIdol(OtherSkinSelectScreen.Inst.idolIndex).getType(OtherSkinSelectScreen.Inst.skinIndex);
        }
        else{
            if(type==CommonEnum.IdolType.SENSE){
                getRandomCard(PlayerColorEnum.gkmasModColorSense,cards);
            }
            else if(type==CommonEnum.IdolType.LOGIC){
                getRandomCard(PlayerColorEnum.gkmasModColorLogic,cards);
            }
            else if(type==CommonEnum.IdolType.ANOMALY){
                getRandomCard(PlayerColorEnum.gkmasModColorAnomaly,cards);
            }
            else{
                getRandomCard(PlayerColorEnum.gkmasModColorSense,cards);
                getRandomCard(PlayerColorEnum.gkmasModColorLogic,cards);
                getRandomCard(PlayerColorEnum.gkmasModColorAnomaly,cards);
            }
        }
        Collections.shuffle(this.cards,new Random(Settings.seed+AbstractDungeon.floorNum));
        this.imageEventText.setDialogOption(String.format(OPTIONS[0],gold+AbstractDungeon.actNum*20, cards.get(0).name),cards.get(0));
        this.imageEventText.setDialogOption(String.format(OPTIONS[1],gold+AbstractDungeon.actNum*20, cards.get(1).name),cards.get(1));
        this.imageEventText.setDialogOption(String.format(OPTIONS[2],gold+AbstractDungeon.actNum*20, cards.get(2).name),cards.get(2));
        this.imageEventText.setDialogOption(OPTIONS[3]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum){
            case 0 :
                switch (i) {
                    case 0:
                    case 1:
                    case 2:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(cards.get(i), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        AbstractDungeon.effectList.add(new RainingGoldEffect(this.gold+AbstractDungeon.actNum*20));
                        AbstractDungeon.player.gainGold(this.gold+AbstractDungeon.actNum*20);
                        screenNum++;
                        return;
                }
            case 1:
                switch (i) {
                    case 0:
                        break;
                }
        }
        openMap();
    }

    @Override
    public void onEnterRoom() {
        super.onEnterRoom();
        SoundHelper.playSound("gkmasModResource/audio/voice/plan/presult_001_final_true_nasr_001.ogg");
    }

    public ArrayList<AbstractCard> getRandomCard(AbstractCard.CardColor color,ArrayList<AbstractCard> tmpPool) {
        Iterator<Map.Entry<String, AbstractCard>> cardLib = CardLibrary.cards.entrySet().iterator();
        while (true) {
            if (!cardLib.hasNext())
                return tmpPool;
            Map.Entry c = cardLib.next();
            AbstractCard card = (AbstractCard)c.getValue();
            if (card.color.equals(color)&& card.tags.contains(GkmasCardTag.IDOL_CARD_TAG)){
                GkmasCard gkmasCard = (GkmasCard)card;
                if(gkmasCard.bannerColor.equals("color")&&!card.cardID.equals(IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getCard(SkinSelectScreen.Inst.skinIndex)))
                    tmpPool.add(card);
            }

        }
    }
}