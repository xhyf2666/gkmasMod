package gkmasmod.event;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.utils.ThreeSizeHelper;

public class KakaSong extends AbstractImageEvent {
    public static final String ID = KakaSong.class.getSimpleName();
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private static final int[] vo_require = {100, 200, 400};
    private int screenNum = 0;
    private int vo=0;

    public KakaSong() {
        super(NAME, DESCRIPTIONS[0], String.format("gkmasModResource/img/event/%s.png",ID));
        this.imageEventText.setDialogOption(OPTIONS[0]);
        vo = ThreeSizeHelper.getVo();
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum){
            case 0 :
                switch (i) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        if(vo>=vo_require[screenNum]) {
                            screenNum++;
                            return;
                        }
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(String.format(OPTIONS[1],vo_require[screenNum]),true);
                        this.imageEventText.setDialogOption(OPTIONS[2]);
                        return;
                }
            case 1:
                switch (i) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        if(vo>=vo_require[screenNum]) {
                            screenNum++;
                            return;
                        }
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(String.format(OPTIONS[1],vo_require[screenNum]),true);
                        this.imageEventText.setDialogOption(OPTIONS[2]);
                        return;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.COMMON);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), r);
                        screenNum=9;
                        openMap();
                        return;
                }
            case 2:
                switch (i) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        if(vo>=vo_require[screenNum]) {
                            screenNum++;
                            return;
                        }
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(String.format(OPTIONS[1],vo_require[screenNum]),true);
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        return;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.COMMON);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), r);
                        screenNum=9;
                        openMap();
                        return;
                }
            case 3:
                switch (i) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        screenNum=4;
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        return;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.UNCOMMON);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), r);
                        screenNum=9;
                        openMap();
                        return;
                }
            case 4:
                AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.UNCOMMON);
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), r);
                screenNum=9;
                openMap();
                return;
        }
        openMap();
    }

}