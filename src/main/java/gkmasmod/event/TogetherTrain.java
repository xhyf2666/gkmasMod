package gkmasmod.event;


import basemod.abstracts.events.phases.CombatPhase;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import gkmasmod.potion.HotCoffee;
import gkmasmod.relics.PocketBook;
import gkmasmod.room.shop.AnotherShopPotions;
import gkmasmod.utils.ThreeSizeHelper;

public class TogetherTrain extends AbstractImageEvent {
    public static final String ID = TogetherTrain.class.getSimpleName();
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private static final int vi_add = 60;
    private int screenNum = 0;

    public TogetherTrain() {
        super(NAME, DESCRIPTIONS[0], String.format("gkmasModResource/img/event/%s.png",ID));
        this.imageEventText.setDialogOption(String.format(OPTIONS[0],vi_add));
        this.imageEventText.setDialogOption(String.format(OPTIONS[1],1));
        this.imageEventText.setDialogOption(OPTIONS[2]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum){
            case 0 :
                switch (i) {
                    case 0:
                        addVi();
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        screenNum++;
                        return;
                    case 1:
                        AbstractDungeon.player.obtainPotion(AnotherShopPotions.returnRandomPotion());
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        screenNum++;
                        return;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[2]);
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

    private void addVi() {
        ThreeSizeHelper.changeVi(vi_add);
    }
}