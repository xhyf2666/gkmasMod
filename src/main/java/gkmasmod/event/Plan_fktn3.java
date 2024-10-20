package gkmasmod.event;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.utils.SoundHelper;

public class Plan_fktn3 extends AbstractImageEvent {
    public static final String ID = Plan_fktn3.class.getSimpleName();
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private int screenNum = 0;
    private int choice = -1;
    private boolean pickCard = false;

    public Plan_fktn3() {
        super(NAME, DESCRIPTIONS[0], "gkmasModResource/img/event/teacherDa.png");
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
        this.imageEventText.setDialogOption(OPTIONS[2]);
    }

    public void update() {
        super.update();
        if (this.pickCard &&
                !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c, transCard, upgCard;
            switch (this.choice) {
                case 0:
                    AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    ((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0)).upgrade();
                    upgCard = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                    AbstractEvent.logMetricCardUpgrade("Living Wall", "Grow", upgCard);
                    AbstractDungeon.player.bottledCardUpgradeCheck(upgCard);
                    break;
                case 1:
                    CardCrawlGame.sound.play("CARD_EXHAUST");
                    AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(AbstractDungeon.gridSelectScreen.selectedCards

                            .get(0), (Settings.WIDTH / 2), (Settings.HEIGHT / 2)));
                    AbstractEvent.logMetricCardRemoval("Living Wall", "Forget", AbstractDungeon.gridSelectScreen.selectedCards

                            .get(0));
                    AbstractDungeon.player.masterDeck.removeCard(AbstractDungeon.gridSelectScreen.selectedCards
                            .get(0));
                    break;
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.pickCard = false;
        }
    }


    @Override
    protected void buttonEffect(int i) {
        switch (screenNum){
            case 0 :
                switch (i) {
                    case 0:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[2]);
                        choice = 0;
                        if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards())
                                .size() > 0)
                            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck
                                    .getUpgradableCards(), 1, OPTIONS[0], true, false, false, false);
                        screenNum++;
                        this.pickCard = true;
                        return;
                    case 1:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[2]);
                        choice = 1;
                        if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards())
                                .size() > 0)
                            AbstractDungeon.gridSelectScreen.open(
                                    CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck
                                            .getPurgeableCards()), 1, OPTIONS[1], false, false, false, true);
                        screenNum++;
                        this.pickCard = true;
                        return;
                    case 2:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[2]);
                        screenNum++;
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
        SoundHelper.playSound(String.format("gkmasModResource/audio/voice/plan/%s.ogg",ID));
    }

}