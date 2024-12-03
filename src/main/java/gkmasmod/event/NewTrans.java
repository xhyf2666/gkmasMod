package gkmasmod.event;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import gkmasmod.cards.free.BrightFuture;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.relics.IdolNoSoul;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.SoundHelper;
import gkmasmod.vfx.SimplePlayVideoEffect2;

public class NewTrans extends AbstractImageEvent {
    public static final String ID = NewTrans.class.getSimpleName();
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private static int screenNum = 0;
    private static int type = 0;

    public NewTrans() {
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
        if(type==1){
            CardCrawlGame.music.playTempBgmInstantly("gkmasModResource/audio/bgm/bgm_adv_daily_002.ogg",true);
            SoundHelper.playSound("gkmasModResource/audio/adv/adv_csprt_3_0000_03.ogg");
        }
        else if(type==2){
            CardCrawlGame.music.playTempBgmInstantly("gkmasModResource/audio/bgm/bgm_adv_daily_002.ogg",true);
            SoundHelper.playSound("gkmasModResource/audio/adv/adv_csprt_3_0017_03.ogg");
        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch (screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        screenNum++;
                        this.imageEventText.updateBodyText(getBody());
                        AbstractDungeon.gridSelectScreen.open(
                                CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck
                                        .getPurgeableCards()), 1, OPTIONS[2], false, true, false, false);
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        if(type==1){
                            CardCrawlGame.music.dispose();
                            CardCrawlGame.music.playTempBgmInstantly("gkmasModResource/audio/bgm/bgm_adv_comical_002.ogg",true);
                            SoundHelper.clearSound();
                            SoundHelper.playSound("gkmasModResource/audio/adv/adv_csprt_3_0000_03_2.ogg");
                        }
                        else if(type==2){
                            CardCrawlGame.music.dispose();
                            CardCrawlGame.music.playTempBgmInstantly("gkmasModResource/audio/bgm/bgm_adv_happy_001.ogg",true);
                            SoundHelper.clearSound();
                            SoundHelper.playSound("gkmasModResource/audio/adv/adv_csprt_3_0017_03_2.ogg");
                        }
                        break;
                    case 1:
                        screenNum++;
                        logMetricIgnored("Transmorgrifier");
                        this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        break;
                }
                break;
            case 1:
                openMap();
                break;
        }
    }

    private static String getName(){
        screenNum = 0;
        type =0;
        if(AbstractDungeon.player instanceof IdolCharacter){
            IdolCharacter idol = (IdolCharacter) AbstractDungeon.player;
            if(idol.idolData.idolName.equals(IdolData.fktn)||idol.idolData.idolName.equals(IdolData.ttmr)||idol.idolData.idolName.equals(IdolData.hski)){
                type = 1;
                return DESCRIPTIONS[0];
            }
            else if(idol.idolData.idolName.equals(IdolData.kllj)||idol.idolData.idolName.equals(IdolData.ssmk)){
                type = 2;
                return DESCRIPTIONS[3];
            }
            else return NAME;
        }
        return NAME;
    }

    private static String getBody(){
        if(type==1)
            return DESCRIPTIONS[1+screenNum];
        else if(type==2){
            return DESCRIPTIONS[4+screenNum];
        }
        else
            return DESCRIPTIONS[6+screenNum];
    }

    private static String getImg(){
        if(type==1)
            return String.format("gkmasModResource/img/event/%s_%d.png",ID,type);
        else if(type==2){
            return String.format("gkmasModResource/img/event/%s_%d.png",ID,type);
        }
        else
            return String.format("gkmasModResource/img/event/%s_%d.png",ID,1);
    }

}