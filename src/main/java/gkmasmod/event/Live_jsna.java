package gkmasmod.event;


import com.badlogic.gdx.Gdx;
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
import gkmasmod.cards.free.HighSpirits;
import gkmasmod.relics.IdolNoSoul;
import gkmasmod.relics.StruggleRecord;
import gkmasmod.room.shop.AnotherShopPotions;
import gkmasmod.utils.ThreeSizeHelper;
import gkmasmod.vfx.SimplePlayVideoEffect2;

public class Live_jsna extends AbstractImageEvent {
    public static final String ID = Live_jsna.class.getSimpleName();
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private static final int HP_LOST = 6;
    private static final int MONEY = 90;
    private int screenNum = 0;

    public Live_jsna() {
        super(NAME, DESCRIPTIONS[0], String.format("gkmasModResource/img/event/%s.png",ID));
        this.imageEventText.setDialogOption(String.format(OPTIONS[0],HP_LOST),new IdolNoSoul());
        this.imageEventText.setDialogOption(String.format(OPTIONS[1],MONEY),new BrightFuture());
        this.imageEventText.setDialogOption(OPTIONS[2],new HighSpirits());
        this.imageEventText.setDialogOption(OPTIONS[3]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum){
            case 0 :
                switch (i) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        AbstractDungeon.player.damage(new DamageInfo(null, HP_LOST));
                        if (!AbstractDungeon.player.hasRelic(IdolNoSoul.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new IdolNoSoul());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        playVideo();

                        screenNum++;
                        return;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(CardLibrary.getCopy(BrightFuture.ID), (Settings.WIDTH / 2), (Settings.HEIGHT / 2)));
                        AbstractDungeon.effectList.add(new RainingGoldEffect(MONEY));
                        AbstractDungeon.player.gainGold(MONEY);
                        screenNum++;
                        return;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(CardLibrary.getCopy(HighSpirits.ID), (Settings.WIDTH / 2), (Settings.HEIGHT / 2)));
                        screenNum++;
                        return;
                    case 3:
                        screenNum++;
                        openMap();
                        return;
                }
            case 1:
                switch (i) {
                    case 0:
                        openMap();
                        break;
                }
        }
        openMap();
    }

    private void playVideo(){
        String videoPath = "gkmasModResource/video/other/jsna_special.webm";

        CardCrawlGame.fadeIn(1.0F);
        CardCrawlGame.music.dispose();
        if(Gdx.files.local(videoPath).exists()){
            AbstractDungeon.topLevelEffectsQueue.add(new SimplePlayVideoEffect2(videoPath,true));
        }
    }

}