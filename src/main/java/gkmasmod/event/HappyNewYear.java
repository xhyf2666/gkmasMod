package gkmasmod.event;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.SoundHelper;

import java.util.Random;

public class HappyNewYear extends AbstractImageEvent {
    public static final String ID = HappyNewYear.class.getSimpleName();
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;

    public HappyNewYear() {
        super(getName(), getBody(), getImg());
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
    }

    public void update() {
        super.update();
        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.transformCard(c, false, AbstractDungeon.miscRng);
            AbstractCard transCard = AbstractDungeon.getTransformedCard();
            logMetricTransformCard("Transmorgrifier", "Transformed", c, transCard);
            AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(transCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    @Override
    public void onEnterRoom() {
        CardCrawlGame.music.playTempBgmInstantly("gkmasModResource/audio/bgm/bgm_adv_end_001.ogg",true);
        switch (SkinSelectScreen.Inst.idolName) {
            case IdolData.hski:
                SoundHelper.playSound("gkmasModResource/audio/voice/newYear/system_hski_home_transition_13.ogg");
                break;
            case IdolData.ttmr:
                SoundHelper.playSound("gkmasModResource/audio/voice/newYear/system_ttmr_home_transition_13.ogg");
                break;
            case IdolData.fktn:
                SoundHelper.playSound("gkmasModResource/audio/voice/newYear/system_fktn_home_transition_13.ogg");
                break;
            case IdolData.amao:
                SoundHelper.playSound("gkmasModResource/audio/voice/newYear/system_amao_home_transition_13.ogg");
                break;
            case IdolData.kllj:
                SoundHelper.playSound("gkmasModResource/audio/voice/newYear/system_kllj_home_transition_13.ogg");
                break;
            case IdolData.ssmk:
                SoundHelper.playSound("gkmasModResource/audio/voice/newYear/system_ssmk_home_transition_13.ogg");
                break;
            case IdolData.hume:
                SoundHelper.playSound("gkmasModResource/audio/voice/newYear/system_hume_home_transition_13.ogg");
                break;
            case IdolData.shro:
                SoundHelper.playSound("gkmasModResource/audio/voice/newYear/system_shro_home_transition_13.ogg");
                break;
            case IdolData.kcna:
                SoundHelper.playSound("gkmasModResource/audio/voice/newYear/system_kcna_home_transition_13.ogg");
                break;
            case IdolData.jsna:
                SoundHelper.playSound("gkmasModResource/audio/voice/newYear/system_jsna_home_transition_13.ogg");
                break;
            case IdolData.hrnm:
                SoundHelper.playSound("gkmasModResource/audio/voice/newYear/system_hrnm_home_transition_13.ogg");
                break;
        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch (screenNum){
            case 0:
                switch (buttonPressed) {
                    case 0:
                        Random rng = new Random(Settings.seed);
                        int gold = rng.nextInt(100)+1;
                        AbstractDungeon.player.gainGold(gold);
                        AbstractDungeon.effectList.add(new RainingGoldEffect(gold));
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[1]);
                        screenNum++;
                        break;
                    case 1:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[1]);
                        screenNum++;
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

    private static String getName(){
        return NAME;
    }

    private static String getBody(){
        switch (SkinSelectScreen.Inst.idolName){
            case IdolData.hski:
                return DESCRIPTIONS[0];
            case IdolData.ttmr:
                return DESCRIPTIONS[1];
            case IdolData.fktn:
                return DESCRIPTIONS[2];
            case IdolData.amao:
                return DESCRIPTIONS[3];
            case IdolData.kllj:
                return DESCRIPTIONS[4];
            case IdolData.ssmk:
                return DESCRIPTIONS[5];
            case IdolData.hume:
                return DESCRIPTIONS[6];
            case IdolData.shro:
                return DESCRIPTIONS[7];
            case IdolData.kcna:
                return DESCRIPTIONS[8];
            case IdolData.jsna:
                return DESCRIPTIONS[9];
            case IdolData.hrnm:
                return DESCRIPTIONS[10];
            default:
                return DESCRIPTIONS[10];
        }
    }

    private static String getImg(){
        return String.format("gkmasModResource/img/event/HappyNewYear_%s.png", SkinSelectScreen.Inst.idolName);
    }

}