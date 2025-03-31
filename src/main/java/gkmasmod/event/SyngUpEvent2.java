package gkmasmod.event;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.beyond.MonsterSena2;
import gkmasmod.relics.*;

public class SyngUpEvent2 extends AbstractImageEvent {
    public static final String ID = SyngUpEvent2.class.getSimpleName();
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private int screenNum = 0;
    private int gold = 150;

    public SyngUpEvent2() {
        super(NAME, DESCRIPTIONS[0], "gkmasModResource/img/event/SyngUpEvent2.png");
        this.imageEventText.setDialogOption(OPTIONS[0],new FriendChinaRelic());
        this.imageEventText.setDialogOption(OPTIONS[1],new FriendHiroRelic());
        this.imageEventText.setDialogOption(OPTIONS[2],new FriendUmeRelic());
        this.imageEventText.setDialogOption(String.format(OPTIONS[3],gold),AbstractDungeon.player.gold < gold,new FriendKotoneRelic());
        this.imageEventText.setDialogOption(OPTIONS[4],new FriendSenaRelic());

    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum){
            case 0 :
                switch (i) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        if (!AbstractDungeon.player.hasRelic(FriendChinaRelic.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new FriendChinaRelic());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        screenNum=1;
                        return;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        if (!AbstractDungeon.player.hasRelic(FriendHiroRelic.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new FriendHiroRelic());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        screenNum=1;
                        return;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        if (!AbstractDungeon.player.hasRelic(FriendUmeRelic.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new FriendUmeRelic());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        screenNum=1;
                        return;
                    case 3:
                        AbstractDungeon.player.loseGold(gold);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        if (!AbstractDungeon.player.hasRelic(FriendKotoneRelic.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new FriendKotoneRelic());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        screenNum=2;
                        return;
                    case 4:
//                        AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(25, 35));
                        (AbstractDungeon.getCurrRoom()).monsters = MonsterHelper.getEncounter(MonsterSena2.ID);
                        (AbstractDungeon.getCurrRoom()).rewards.clear();
                        (AbstractDungeon.getCurrRoom()).rewardAllowed = false;
                        enterCombatFromImage();
//                        if (AbstractDungeon.player.hasRelic(FriendSenaRelic.ID)) {
//                            AbstractDungeon.getCurrRoom().addRelicToRewards(new Circlet());
//                        } else {
//                            AbstractDungeon.getCurrRoom().addRelicToRewards(new FriendSenaRelic());
//                        }
                        this.imageEventText.clearRemainingOptions();
                        AbstractDungeon.lastCombatMetricKey = MonsterSena2.ID;
                        screenNum=2;
                        return;
                }
            case 1:
                switch (i) {
                    case 0:
                        break;
                }
                break;
            case 2:
                switch (i) {
                    case 0:
                        if (!AbstractDungeon.player.hasRelic(FriendSenaRelic.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new FriendSenaRelic());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
//                        openMap();
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

    public void reopen() {
        if (screenNum==2) {
            AbstractDungeon.resetPlayer();
            AbstractDungeon.player.drawX = Settings.WIDTH * 0.25F;
            AbstractDungeon.player.preBattlePrep();
            enterImageFromCombat();
            this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
            this.imageEventText.clearAllDialogs();
            this.imageEventText.setDialogOption(OPTIONS[5],new FriendSenaRelic());
        }
    }

}