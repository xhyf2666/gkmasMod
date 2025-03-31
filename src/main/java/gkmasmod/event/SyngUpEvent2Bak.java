package gkmasmod.event;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.Circlet;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.monster.beyond.MonsterSena2;
import gkmasmod.relics.*;

public class SyngUpEvent2Bak extends AbstractEvent {
    public static final String ID = SyngUpEvent2Bak.class.getSimpleName();
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private int screenNum = 0;
    private int gold = 100;

    public SyngUpEvent2Bak() {
        this.body = DESCRIPTIONS[0];
        this.hasDialog = true;
        this.hasFocus = true;
        this.roomEventText.addDialogOption(OPTIONS[0],new FriendChinaRelic());
        this.roomEventText.addDialogOption(OPTIONS[1],new FriendHiroRelic());
        this.roomEventText.addDialogOption(OPTIONS[2],new FriendUmeRelic());
        this.roomEventText.addDialogOption(String.format(OPTIONS[3],gold),AbstractDungeon.player.gold < gold,new FriendKotoneRelic());
        this.roomEventText.addDialogOption(OPTIONS[4],new FriendSenaRelic());
        (AbstractDungeon.getCurrRoom()).monsters = MonsterHelper.getEncounter(MonsterSena2.ID);
    }

    public void update() {
        super.update();
        if (!RoomEventDialog.waitForInput)
            buttonEffect(this.roomEventText.getSelectedOption());
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum){
            case 0 :
                switch (i) {
                    case 0:
                        this.roomEventText.clear();
                        this.body = DESCRIPTIONS[1];
                        this.roomEventText.show(this.body);
                        this.roomEventText.addDialogOption(OPTIONS[5]);
                        if (!AbstractDungeon.player.hasRelic(FriendChinaRelic.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new FriendChinaRelic());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        screenNum++;
                        return;
                    case 1:
                        this.roomEventText.clear();
                        this.body = DESCRIPTIONS[2];
                        this.roomEventText.show(this.body);
                        this.roomEventText.addDialogOption(OPTIONS[5]);
                        if (!AbstractDungeon.player.hasRelic(FriendHiroRelic.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new FriendHiroRelic());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        screenNum++;
                        return;
                    case 2:
                        this.roomEventText.clear();
                        this.body = DESCRIPTIONS[3];
                        this.roomEventText.show(this.body);
                        this.roomEventText.addDialogOption(OPTIONS[5]);
                        if (!AbstractDungeon.player.hasRelic(FriendUmeRelic.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new FriendUmeRelic());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        screenNum++;
                        return;
                    case 3:
                        AbstractDungeon.player.loseGold(gold);
                        this.roomEventText.clear();
                        this.body = DESCRIPTIONS[4];
                        this.roomEventText.show(this.body);
                        this.roomEventText.addDialogOption(OPTIONS[5]);
                        if (!AbstractDungeon.player.hasRelic(FriendKotoneRelic.ID)) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new FriendKotoneRelic());
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                        }
                        screenNum++;
                        return;
                    case 4:
                        AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(25, 35));
                        if (AbstractDungeon.player.hasRelic(FriendSenaRelic.ID)) {
                            AbstractDungeon.getCurrRoom().addRelicToRewards(new Circlet());
                        } else {
                            AbstractDungeon.getCurrRoom().addRelicToRewards(new FriendSenaRelic());
                        }
                        enterCombat();
                        AbstractDungeon.lastCombatMetricKey = MonsterSena2.ID;
                        screenNum=2;
                        return;
                }
            case 1:
                switch (i) {
                    case 0:
                        break;
                }
            case 2:
                switch (i) {
                    case 0:
                        openMap();
                        break;
                }
        }
        if(GkmasMod.node!=null){
            (AbstractDungeon.getCurrRoom()).monsters = null;
            AbstractDungeon.setCurrMapNode(GkmasMod.node);
            GkmasMod.node.room.onPlayerEntry();
            GkmasMod.node = null;
            AbstractDungeon.rs = AbstractDungeon.RenderScene.NORMAL;
        }
        else{
            openMap();
        }
    }

}